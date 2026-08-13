package com.mentalhealthapi.service;

import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
//import java.util.Date;
import java.util.List;

import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Join;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.MailMagDTO;
import com.mentalhealthapi.dto.MailMagUserList;
import com.mentalhealthapi.dto.MailMaganizeDTO;
import com.mentalhealthapi.dto.MailMagazineDTO;
import com.mentalhealthapi.dto.ResponseList;
import com.mentalhealthapi.dto.SendMagDTO;
import com.mentalhealthapi.exceptions.ResourceNotFoundException;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.model.MagUser;
import com.mentalhealthapi.model.MailMagJob;
import com.mentalhealthapi.model.MailMagazineBody;
import com.mentalhealthapi.model.MailMagazineEmail;

import com.mentalhealthapi.model.Mailmagazine;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.MagUserRepository;
import com.mentalhealthapi.repository.MailMagJobRepository;
import com.mentalhealthapi.repository.MailmagazineBodyRepository;
import com.mentalhealthapi.repository.MailmagazineEmailRepository;
import com.mentalhealthapi.repository.MailmagazineRepository;
import com.mentalhealthapi.repository.MailmagazineUserRepository;
import com.mentalhealthapi.repository.MemberRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
public class MailMagazineService {

	private final EntityManager entityManager;
	private final MailmagazineUserRepository mailmagazineUserRepository;
	private MailmagazineRepository mailmagazineRepository;
	private MailmagazineBodyRepository mailmagazineBodyRepository;
	private MailmagazineEmailRepository mailMagazineEmailRepository;
	private MemberRepository memberRepository;
	private MagUserRepository magUserRepository;
	private final EmailService emailService;
	private final MailMagJobRepository mailMagJobRepository;

	@Autowired
	public MailMagazineService(EntityManager entityManager, MailmagazineUserRepository mailmagazineUserRepository,
			MailmagazineRepository mailmagazineRepository, MemberRepository memberRepository, EmailService emailService,
			MailmagazineBodyRepository mailmagazineBodyRepository, MagUserRepository magUserRepository,
			MailmagazineEmailRepository mailMagazineEmailRepository,MailMagJobRepository mailMagJobRepository) {
		this.entityManager = entityManager;
		this.emailService = emailService;
		this.magUserRepository = magUserRepository;
		this.mailmagazineBodyRepository = mailmagazineBodyRepository;
		this.mailmagazineUserRepository = mailmagazineUserRepository;
		this.mailMagazineEmailRepository = mailMagazineEmailRepository;
		this.mailmagazineRepository = mailmagazineRepository;
		this.memberRepository = memberRepository;
		this.mailMagJobRepository =mailMagJobRepository;
	}

	public void processMailMagazine(MailMagazineDTO dto) {
		if (dto.getMailMagazine() == 1) {
			int idVal = checkMailMagUser(dto.getEmailPc(), dto.getEmailMob());

			if (idVal == 0) {
				MailmagazineUser user = new MailmagazineUser();
				user.setEmail(dto.getEmailPc());
				user.setEmailMob(dto.getEmailMob());
				user.setEmailSmart(dto.getEmail_smart());
				user.setDate(LocalDate.now());
				user.setStatus(1);
				user.setMember(dto.getId());
				user.setName(dto.getName());
				mailmagazineUserRepository.save(user);
			} else {
				MailmagazineUser user = mailmagazineUserRepository.findById(idVal)
						.orElseThrow(() -> new RuntimeException("User not found")); 
				user.setMember(dto.getId());
				mailmagazineUserRepository.save(user);
			}
		}
	}
	
	public int checkMailMagUser(String emlPc, String emlMob) {
	    boolean hasEmailPc = emlPc != null && !emlPc.isEmpty();
	    boolean hasEmailMob = emlMob != null && !emlMob.isEmpty();

	    if (hasEmailPc && hasEmailMob) {
	        // Both emailPc and emailMob have values
	    	MailmagazineUser user = mailmagazineUserRepository.findFirstByEmailOrEmailMob(emlPc, emlMob);
	        return 0;  
	    } else if (hasEmailPc) {
	        // Only emailPc has a value
	        MailmagazineUser user = mailmagazineUserRepository.findFirstByEmail(emlPc);
	        if (user != null) {
	            return user.getId();
	        }
	    } else if (hasEmailMob) {
	        // Only emailMob has a value
	        MailmagazineUser user = mailmagazineUserRepository.findFirstByEmailMob(emlMob);
	        if (user != null) {
	            return user.getId();
	        }
	    }

	    return 0;  // Default case: Neither emailPc nor emailMob has a value
	}




	public ApiListResponse<List<MailmagazineUser>> mailMagList(MailMagDTO request) {
		try {
			List<MailmagazineUser> mailmagazineUsers = findMailmagazineUsers(request);
			int totalRecords = mailmagazineUsers.size();

			// Apply pagination to the data
			int fromIndex = Math.max(0, request.getPageNumber() * request.getPageSize());
			int toIndex = Math.min(mailmagazineUsers.size(), fromIndex + request.getPageSize());
			mailmagazineUsers = mailmagazineUsers.subList(fromIndex, toIndex);

			ApiListResponse<List<MailmagazineUser>> response = new ApiListResponse<>(totalRecords, "Success",
					mailmagazineUsers, mailmagazineUsers.size());

			return response;
		} catch (Exception e) {
			ApiListResponse<List<MailmagazineUser>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
			return response;
		}
	}

	public List<MailmagazineUser> findMailmagazineUsers(MailMagDTO request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
		Root<MailmagazineUser> root = criteriaQuery.from(MailmagazineUser.class);

		// Select the required fields using a multiselect
		criteriaQuery.multiselect(root.get("id"), root.get("email"), root.get("emailMob"), root.get("status"),
				root.get("date"), root.get("name"), root.get("emailSmart"), root.get("member"));

		List<Predicate> predicates = new ArrayList<>();

		// Add conditions for email_pc and member status
		if("email_pc".equals(request.getType())) {
		predicates.add(criteriaBuilder.isNotNull(root.get("email")));
		predicates.add(criteriaBuilder.equal(root.get("status"), 1));
		predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
		predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));
		}
		if("email_mob".equals(request.getType())) {
			predicates.add(criteriaBuilder.isNotNull(root.get("emailMob")));
			predicates.add(criteriaBuilder.equal(root.get("status"), 1));
			predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
			predicates.add(criteriaBuilder.notEqual(root.get("emailMob"), ""));
		}

		if (request.getName() != null && !request.getName().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
		}

		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if ("id".equals(request.getSortField()) || "email".equals(request.getSortField())
				|| "email_mob".equals(request.getSortField())) {
			if ("asc".equalsIgnoreCase(request.getSortOrder())) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(request.getSortField())));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(request.getSortField())));
			}
		} else if ("date".equals(request.getSortField())) {
			if ("asc".equalsIgnoreCase(request.getSortOrder())) {
				criteriaQuery.orderBy(
						criteriaBuilder.asc(criteriaBuilder.function("YEAR", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.asc(criteriaBuilder.function("MONTH", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.asc(criteriaBuilder.function("DAY", Integer.class,
								root.get("date").as(java.sql.Date.class))));
			} else {
				criteriaQuery.orderBy(
						criteriaBuilder.desc(criteriaBuilder.function("YEAR", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.desc(criteriaBuilder.function("MONTH", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.desc(criteriaBuilder.function("DAY", Integer.class,
								root.get("date").as(java.sql.Date.class))));
			}
		}

		TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

		// Apply pagination
		typedQuery.setFirstResult(request.getPageNumber() * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());

		List<Object[]> results = typedQuery.getResultList();
		List<MailmagazineUser> mailmagazineUsers = new ArrayList<>();

		for (Object[] result : results) {
			MailmagazineUser mailmagazineUser = new MailmagazineUser();
			mailmagazineUser.setId((Integer) result[0]);
			mailmagazineUser.setEmail((String) result[1]);
			mailmagazineUser.setEmailMob((String) result[2]);
			mailmagazineUser.setStatus((Integer) result[3]);
			mailmagazineUser.setDate((LocalDate) result[4]);
			mailmagazineUser.setName((String) result[5]);
			mailmagazineUser.setEmailSmart((String) result[6]);

			// Fetch the memberno and set it in MailmagazineUser
			Integer memberno = (Integer) result[7];
			mailmagazineUser.setMember(memberno);

			// Fetch the Member entity based on the memberno
			Member member = entityManager.find(Member.class, memberno);

			// If Member is found, set the memberName and memberId
			if (member != null) {
				mailmagazineUser.setMemberName(member.getName());
				mailmagazineUser.setMemberId(member.getMemberid());
				mailmagazineUser.setMemberId(member.getName2());
			}
          
			mailmagazineUsers.add(mailmagazineUser);
            
		}

		return mailmagazineUsers;
	}

	
	
	public List<MailmagazineUser> findMailmagazineUsers2(MailMagDTO request) {
	    CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	    CriteriaQuery<Object[]> criteriaQuery = criteriaBuilder.createQuery(Object[].class);
	    Root<MailmagazineUser> root = criteriaQuery.from(MailmagazineUser.class);

		Join<MailmagazineUser, Member> memberJoin = root.join("memberDetails");
	    // Select the required fields using a multiselect
	    criteriaQuery.multiselect(root.get("id"), root.get("email"), root.get("emailMob"), root.get("status"),
	            root.get("date"), root.get("name"), root.get("emailSmart"),memberJoin.get("id"),memberJoin.get("name"),memberJoin.get("status"));

	    List<Predicate> predicates = new ArrayList<>();

	    // Add conditions for email_pc and member status
	    if ("email_pc".equals(request.getType())) {
	        predicates.add(criteriaBuilder.isNotNull(root.get("email")));
	        predicates.add(criteriaBuilder.equal(root.get("status"), 1));
	        predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
	        predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));
	    }
	    if ("email_mob".equals(request.getType())) {
	        predicates.add(criteriaBuilder.isNotNull(root.get("emailMob")));
	        predicates.add(criteriaBuilder.equal(root.get("status"), 1));
	        predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
	        predicates.add(criteriaBuilder.notEqual(root.get("emailMob"), ""));
	    }

	    // Add a condition for name search in both mailmagazine_user and member
	    if (request.getName() != null && !request.getName().isEmpty()) {
	        Expression<String> nameExpression = criteriaBuilder.literal("%" + request.getName() + "%");
	        predicates.add(criteriaBuilder.or(
	                criteriaBuilder.like(root.get("name"), nameExpression),
	                criteriaBuilder.like(root.join("memberDetails").get("name"), nameExpression)
	        ));
	    }

		predicates.add(criteriaBuilder.equal(memberJoin.get("status"), 1));

	    criteriaQuery.where(predicates.toArray(new Predicate[0]));

	    if ("id".equals(request.getSortField()) || "email".equals(request.getSortField())
	            || "email_mob".equals(request.getSortField())) {
	        if ("asc".equalsIgnoreCase(request.getSortOrder())) {
	            criteriaQuery.orderBy(criteriaBuilder.asc(root.get(request.getSortField())));
	        } else {
	            criteriaQuery.orderBy(criteriaBuilder.desc(root.get(request.getSortField())));
	        }
	    } else if ("date".equals(request.getSortField())) {
	        if ("asc".equalsIgnoreCase(request.getSortOrder())) {
	            criteriaQuery.orderBy(
	                    criteriaBuilder.asc(criteriaBuilder.function("YEAR", Integer.class,
	                            root.get("date").as(java.sql.Date.class))),
	                    criteriaBuilder.asc(criteriaBuilder.function("MONTH", Integer.class,
	                            root.get("date").as(java.sql.Date.class))),
	                    criteriaBuilder.asc(criteriaBuilder.function("DAY", Integer.class,
	                            root.get("date").as(java.sql.Date.class)))
	            );
	        } else {
	            criteriaQuery.orderBy(
	                    criteriaBuilder.desc(criteriaBuilder.function("YEAR", Integer.class,
	                            root.get("date").as(java.sql.Date.class))),
	                    criteriaBuilder.desc(criteriaBuilder.function("MONTH", Integer.class,
	                            root.get("date").as(java.sql.Date.class))),
	                    criteriaBuilder.desc(criteriaBuilder.function("DAY", Integer.class,
	                            root.get("date").as(java.sql.Date.class)))
	            );
	        }
	    }

	    TypedQuery<Object[]> typedQuery = entityManager.createQuery(criteriaQuery);

	    // Apply pagination
	    typedQuery.setFirstResult(request.getPageNumber() * request.getPageSize());
	    typedQuery.setMaxResults(request.getPageSize());

	    List<Object[]> results = typedQuery.getResultList();
	    List<MailmagazineUser> mailmagazineUsers = new ArrayList<>();

	    for (Object[] result : results) {
			MailmagazineUser mailmagazineUser = new MailmagazineUser();
			mailmagazineUser.setId((Integer) result[0]);
			mailmagazineUser.setEmail((String) result[1]);
			mailmagazineUser.setEmailMob((String) result[2]);
			mailmagazineUser.setStatus((Integer) result[3]);
			mailmagazineUser.setDate((LocalDate) result[4]);
			mailmagazineUser.setName((String) result[5]);
			mailmagazineUser.setEmailSmart((String) result[6]);

			// Fetch the memberno and set it in MailmagazineUser
			Integer memberno = (Integer) result[7];
			mailmagazineUser.setMember(memberno);

			// Fetch the Member entity based on the memberno
			Member member = entityManager.find(Member.class, memberno);

			// If Member is found, set the memberName and memberId
			if (member != null) {
				mailmagazineUser.setMemberName(member.getName());
				mailmagazineUser.setMemberId(member.getMemberid());
				mailmagazineUser.setMemberId(member.getName2());
			}
          
			mailmagazineUsers.add(mailmagazineUser);
            
		}


	    return mailmagazineUsers;
	}

	
	
	public List<MailmagazineUser> findMailmagazineUsers_working(MailMagDTO request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MailmagazineUser> criteriaQuery = criteriaBuilder.createQuery(MailmagazineUser.class);
		Root<MailmagazineUser> root = criteriaQuery.from(MailmagazineUser.class);

		List<Predicate> predicates = new ArrayList<>();

		// Add conditions for email_pc and member status
		predicates.add(criteriaBuilder.isNotNull(root.get("email")));
		predicates.add(criteriaBuilder.equal(root.get("status"), 1));
		predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0)); // Assuming "member" is a numeric field
		predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));

		criteriaQuery.where(predicates.toArray(new Predicate[0]));

//        // Add sorting based on the provided field and direction
//        if ("id".equals(request.getSortField()) || "email".equals(request.getSortField()) || "email_mob".equals(request.getSortField())) {
//            if ("asc".equalsIgnoreCase(request.getSortOrder())) {
//                criteriaQuery.orderBy(criteriaBuilder.asc(root.get(request.getSortField())));
//            } else {
//                criteriaQuery.orderBy(criteriaBuilder.desc(root.get(request.getSortField())));
//            }
//        }

		if ("id".equals(request.getSortField()) || "email".equals(request.getSortField())
				|| "email_mob".equals(request.getSortField())) {
			if ("asc".equalsIgnoreCase(request.getSortOrder())) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(request.getSortField())));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(request.getSortField())));
			}
		} else if ("date".equals(request.getSortField())) {
			if ("asc".equalsIgnoreCase(request.getSortOrder())) {
				criteriaQuery.orderBy(
						criteriaBuilder.asc(criteriaBuilder.function("YEAR", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.asc(criteriaBuilder.function("MONTH", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.asc(criteriaBuilder.function("DAY", Integer.class,
								root.get("date").as(java.sql.Date.class))));
			} else {
				criteriaQuery.orderBy(
						criteriaBuilder.desc(criteriaBuilder.function("YEAR", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.desc(criteriaBuilder.function("MONTH", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.desc(criteriaBuilder.function("DAY", Integer.class,
								root.get("date").as(java.sql.Date.class))));
			}
		}

		TypedQuery<MailmagazineUser> typedQuery = entityManager.createQuery(criteriaQuery);

		// Apply pagination
		typedQuery.setFirstResult(request.getPageNumber() * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());

		List<MailmagazineUser> mailmagazineUsers = typedQuery.getResultList();

		// Fetch the Member entities based on the member field (memberno) in
		// MailmagazineUser
		List<Integer> memberIds = mailmagazineUsers.stream().map(MailmagazineUser::getMember)
				.collect(Collectors.toList());

		CriteriaQuery<Member> memberCriteriaQuery = criteriaBuilder.createQuery(Member.class);
		Root<Member> memberRoot = memberCriteriaQuery.from(Member.class);
		memberCriteriaQuery.where(memberRoot.get("memberno").in(memberIds));

		TypedQuery<Member> memberTypedQuery = entityManager.createQuery(memberCriteriaQuery);
		List<Member> members = memberTypedQuery.getResultList();

		// Map Members to MailmagazineUser based on the member field (memberno)
		Map<Integer, Member> memberMap = members.stream()
				.collect(Collectors.toMap(Member::getMemberno, Function.identity()));

		mailmagazineUsers.forEach(user -> {
			Member member = memberMap.get(user.getMember());
			if (member != null) {
				user.setMemberName(member.getName());
				user.setMemberId(member.getMemberid());
			}
		});

		return mailmagazineUsers;
	}

	public long getTotalCount(MailMagDTO request, String type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<MailmagazineUser> root = countQuery.from(MailmagazineUser.class);

		List<Predicate> predicates = new ArrayList<>();
		if (type == "BBS") {
			if ("email_pc".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("email")),
						criteriaBuilder.notEqual(root.get("email"), ""));
				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}

			} else if ("email_mob".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(
						criteriaBuilder.isNotNull(root.get("emailMob")),
						criteriaBuilder.notEqual(root.get("emailMob"), ""));

				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("emailMob"), ""));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}
			}
		} else {
			if ("email_pc".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("email")),
						criteriaBuilder.notEqual(root.get("email"), ""));
				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.equal(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}

			} else if ("email_mob".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(
						criteriaBuilder.isNotNull(root.get("emailMob")),
						criteriaBuilder.notEqual(root.get("emailMob"), ""));
				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.equal(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("emailMob"), ""));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}
			}

		}

		countQuery.select(criteriaBuilder.count(root));
		countQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery);
		return typedQuery.getSingleResult();
	}


	public long getTotalCountforActiveMailSubMember(MailMagDTO request, String type) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<MailmagazineUser> root = countQuery.from(MailmagazineUser.class);

		Join<MailmagazineUser, Member> memberJoin = root.join("memberDetails");

		List<Predicate> predicates = new ArrayList<>();
		if (type == "BBS") {
			if ("email_pc".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("email")),
						criteriaBuilder.notEqual(root.get("email"), ""));
				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));
				predicates.add(criteriaBuilder.equal(memberJoin.get("status"), 1));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}

			} else if ("email_mob".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(
						criteriaBuilder.isNotNull(root.get("emailMob")),
						criteriaBuilder.notEqual(root.get("emailMob"), ""));

				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.greaterThan(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("emailMob"), ""));
				predicates.add(criteriaBuilder.equal(memberJoin.get("status"), 1));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}
			}
		} else {
			if ("email_pc".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("email")),
						criteriaBuilder.notEqual(root.get("email"), ""));
				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.equal(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("email"), ""));
				predicates.add(criteriaBuilder.equal(memberJoin.get("status"), 1));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}

			} else if ("email_mob".equals(request.getType())) {
				Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(
						criteriaBuilder.isNotNull(root.get("emailMob")),
						criteriaBuilder.notEqual(root.get("emailMob"), ""));
				predicates.add(criteriaBuilder.equal(root.get("status"), 1));
				predicates.add(criteriaBuilder.equal(root.get("member"), 0));
				predicates.add(criteriaBuilder.notEqual(root.get("emailMob"), ""));
				predicates.add(criteriaBuilder.equal(memberJoin.get("status"), 1));
				if (request.getName() != null && !request.getName().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
				}

				if (request.getEmail() != null && !request.getEmail().isEmpty()) {
					predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
				}
			}

		}

		countQuery.select(criteriaBuilder.count(root));
		countQuery.where(predicates.toArray(new Predicate[0]));

		TypedQuery<Long> typedQuery = entityManager.createQuery(countQuery);
		return typedQuery.getSingleResult();
	}

	public List<MailmagazineUser> findMailmagazineUsersGeneral(MailMagDTO request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<MailmagazineUser> criteriaQuery = criteriaBuilder.createQuery(MailmagazineUser.class);
		Root<MailmagazineUser> root = criteriaQuery.from(MailmagazineUser.class);

		// Select the root entity directly instead of using multiselect
		criteriaQuery.select(root);

		List<Predicate> predicates = new ArrayList<>();
		if ("email_pc".equals(request.getType())) {
			// Add conditions for email_pc and member status
			Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("email")),
					criteriaBuilder.notEqual(root.get("email"), ""));

			predicates.add(criteriaBuilder.equal(root.get("status"), 1));
			predicates.add(criteriaBuilder.equal(root.get("member"), 0));
		}
		if ("email_mob".equals(request.getType())) {
			// Custom predicate to handle non-empty emailMob
			Predicate emailMobNotEmptyPredicate = criteriaBuilder.and(criteriaBuilder.isNotNull(root.get("emailMob")),
					criteriaBuilder.notEqual(root.get("emailMob"), ""));

			predicates.add(emailMobNotEmptyPredicate);
			predicates.add(criteriaBuilder.equal(root.get("status"), 1));
			predicates.add(criteriaBuilder.equal(root.get("member"), 0));
		}

		if (request.getName() != null && !request.getName().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("name"), "%" + request.getName() + "%"));
		}

		if (request.getEmail() != null && !request.getEmail().isEmpty()) {
			predicates.add(criteriaBuilder.like(root.get("email"), "%" + request.getEmail() + "%"));
		}
		criteriaQuery.where(predicates.toArray(new Predicate[0]));

		if ("id".equals(request.getSortField()) || "email".equals(request.getSortField())
				|| "email_mob".equals(request.getSortField())) {
			if ("asc".equalsIgnoreCase(request.getSortOrder())) {
				criteriaQuery.orderBy(criteriaBuilder.asc(root.get(request.getSortField())));
			} else {
				criteriaQuery.orderBy(criteriaBuilder.desc(root.get(request.getSortField())));
			}
		} else if ("date".equals(request.getSortField())) {
			if ("asc".equalsIgnoreCase(request.getSortOrder())) {
				criteriaQuery.orderBy(
						criteriaBuilder.asc(criteriaBuilder.function("YEAR", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.asc(criteriaBuilder.function("MONTH", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.asc(criteriaBuilder.function("DAY", Integer.class,
								root.get("date").as(java.sql.Date.class))));
			} else {
				criteriaQuery.orderBy(
						criteriaBuilder.desc(criteriaBuilder.function("YEAR", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.desc(criteriaBuilder.function("MONTH", Integer.class,
								root.get("date").as(java.sql.Date.class))),
						criteriaBuilder.desc(criteriaBuilder.function("DAY", Integer.class,
								root.get("date").as(java.sql.Date.class))));
			}
		}

		TypedQuery<MailmagazineUser> typedQuery = entityManager.createQuery(criteriaQuery);

		// Apply pagination
		typedQuery.setFirstResult(request.getPageNumber() * request.getPageSize());
		typedQuery.setMaxResults(request.getPageSize());

		return typedQuery.getResultList();
	}

	public MailmagazineUser updateGeneral(Integer id, MailmagazineUser muser) {
		MailmagazineUser existingMailUser = mailmagazineUserRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MailmagazineUser not found with id: " + id));
		if (muser.getEmail() != null) {
			existingMailUser.setEmail(muser.getEmail());
		}
		if (muser.getEmailMob() != null) {
			existingMailUser.setEmailMob(muser.getEmailMob());
		}
		if (muser.getName() != null) {
			existingMailUser.setName(muser.getName());
		}

		return mailmagazineUserRepository.save(existingMailUser);

	}

	public String deleteGeneral(Integer id) {
		try {
			MailmagazineUser existingMailUser = mailmagazineUserRepository.findById(id)
					.orElseThrow(() -> new ResourceNotFoundException("MailmagazineUser not found with id: " + id));
			Integer mem = existingMailUser.getMember();
			mailmagazineUserRepository.delete(existingMailUser);

			if (mem > 0) {
				Optional<Member> memberOptional = memberRepository.findById(mem);
				if (memberOptional.isPresent()) {
					Member member = memberOptional.get();
					member.setMailmagazine(2);
					memberRepository.save(member);
				}
			}

			return "MailmagazineUser deleted successfully";
		} catch (ResourceNotFoundException e) {
			return "MailmagazineUser not found with id: " + id;
		} catch (Exception e) {
			return "Failed to delete MailmagazineUser";
		}
	}

	public Mailmagazine registerMailmagazine(Mailmagazine request) {
		// request.setDate(new Date(System.currentTimeMillis()));
		request.setStatus(1);
		request.setDate(LocalDate.now());

		return mailmagazineRepository.save(request);
	}
	public MailMagazineBody registerMailmagazineBody(MailMagazineBody request) {
		// request.setDate(new Date(System.currentTimeMillis()));
		  request.setStatus(1);
		  request.setJobStatus("PENDING");
		  request.setJobRemark("");
		return mailmagazineBodyRepository.save(request);
	}

	public List<Mailmagazine> findMailMagazine(MailMaganizeDTO request) {
		PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
		byte nonValue = request.getNon();
		return mailmagazineRepository.findByNonAndSendtimeOrderByidDesc(nonValue,pageRequest).getContent();
	}
	/**
	 * find total count of mailmagazine by non value
	 * @return
	 */
	public Long countMailMagazine(MailMaganizeDTO request) {
		return mailmagazineRepository.countByNon(request.getNon());
	}
	public List<MailMagazineBody> findMailmagazineByTheme() {
		return mailmagazineBodyRepository.findAllToSend();
	}
	public Mailmagazine updateMail(Integer id, Mailmagazine mailmag) {
		Mailmagazine existingMail = mailmagazineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mailmagazine not found with id: " + id));
		if (mailmag.getTitle() != null) {
			existingMail.setTitle(mailmag.getTitle());
		}
		if (mailmag.getText() != null) {
			existingMail.setText(mailmag.getText());
		}
		if (mailmag.getTextMob() != null) {
			existingMail.setTextMob(mailmag.getTextMob());
		}

		return mailmagazineRepository.save(existingMail);
	}

	public Mailmagazine getMailMag(Integer id) {
		Mailmagazine existingMail = mailmagazineRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("Mailmagazine not found with id: " + id));
		return existingMail;
	}

	//Cron job to send mailmagazine for non BBS users
	public String sendMailMagSchedulerService() {
		try {
			int mailMagJobId = 0;
			String title = "", body = "";
			LocalDateTime currentDateTime = LocalDateTime.now();
			System.out.println("currentDateTime: " + currentDateTime);

			List<MailMagazineBody> newslettersToSend = mailmagazineBodyRepository
					.findPendingOrProcessingNewslettersToSend(currentDateTime);
			//System.out.println("result s" + newslettersToSend);

			for (MailMagazineBody mailMagazineBody : newslettersToSend) {
				try {
					mailMagazineBody.setSendStatus(0);
					mailMagazineBody.setJobStatus("PROCESSING");
					mailMagazineBody.setJobRemark("PROCESSING");
					mailMagazineBody.setSendOutTime(currentDateTime);
					mailmagazineBodyRepository.save(mailMagazineBody);
					mailMagJobId = mailMagazineBody.getMailMagJobId();
					title = mailMagazineBody.getSubject();
					body = mailMagazineBody.getText();
					
					MailMagJob mail_job = mailMagJobRepository.findById(mailMagJobId).orElse(null);
					List<MailMagazineEmail> emailList = mailMagazineEmailRepository
							.findPendingAndNotCompletedEmails(mailMagJobId);

					for (MailMagazineEmail email : emailList) {
						try {
							emailService.sendNewsletterEmail(email.getEmail(), title, body, true);
							System.out.println("Sent email to: " + email.getEmail());
							email.setStatus(1);
							email.setSendStatus("COMPLETED");
							LocalDate currentDate = LocalDate.now();
							Date sendDate = Date.valueOf(currentDate);
							email.setSendDate(sendDate);
							mailMagazineEmailRepository.save(email);
						} catch (Exception e) {
							mailMagazineBody.setSendStatus(0);
							mailMagazineBody.setJobStatus("STOPED");
							mailMagazineBody.setJobRemark(e.getMessage());
							mailMagazineBody.setSendOutTime(currentDateTime);
							mailmagazineBodyRepository.save(mailMagazineBody);
							
							System.err.println("Failed to send email to: " + email.getEmail());
							// Set a custom status code to indicate failure
							e.printStackTrace();
						}

					}
					//updating mail mag job to 1
					mail_job.setStatus(1);
					mail_job.setSendDate(LocalDate.now());
					mailMagJobRepository.save(mail_job);
				} catch (Exception e) {
					
					mailMagazineBody.setSendStatus(0);
					mailMagazineBody.setJobStatus("STOPED");
					mailMagazineBody.setJobRemark(e.getMessage());
					mailMagazineBody.setSendOutTime(currentDateTime);
					mailmagazineBodyRepository.save(mailMagazineBody);

					e.printStackTrace();
					return "Failed to send newsletters: sendMailMagSchedulerService";
				}

			
				// Update the status of the mail magazine body
				mailMagazineBody.setSendStatus(1);
				mailMagazineBody.setJobStatus("COMPLETED");
				mailMagazineBody.setJobRemark("COMPLETED");
				mailMagazineBody.setSendOutTime(currentDateTime);
				mailmagazineBodyRepository.save(mailMagazineBody);
			}
			return "Send newsletters successfully";
		} catch (Exception e) {

			e.printStackTrace();
			return "Failed to send newsletters: sendMailMagSchedulerService";
		}
	}
	//Cron job to send mailmagazine for BBS users
	public String sendMailMagSchedulerServiceForBBS() {
		try {
			System.out.println("sendMailMagSchedulerServiceForBBS");
			int mailMagJobId = 0;
			LocalDate currentDate = LocalDate.now();
			Date sendDate = Date.valueOf(currentDate);
			String title = "", body = "";
			LocalDateTime currentDateTime = LocalDateTime.now();
			//System.out.println("currentDateTime: " + currentDateTime);
			List<Mailmagazine> mailMagToSend = mailmagazineRepository
					.findByStatusAndTextNotNullAndSendStatusAndSendTimeLessThanEqualOrderByDateAsc(1, 0,
							currentDateTime);
			//System.out.println("mail mag" + mailMagToSend);

			for (Mailmagazine mailMagazine : mailMagToSend) {
				try {
					if (mailMagazine.getJobStatus() != "COMPLETED") {
						title = mailMagazine.getTitle();
						body = mailMagazine.getText();
						System.out.println(mailMagazine.getId());
						List<MagUser> userList = magUserRepository.findByStatusAndMailmagazine(0, mailMagazine.getId());

						for (MagUser usr : userList) {
							MailmagazineUser mailMagazineUser = mailmagazineUserRepository
									.findById(usr.getMailmagazineUser()).orElse(null);
							System.out.println(mailMagazineUser);
							if (mailMagazineUser != null) {
								if (mailMagazine.getType() == 2) {
									emailService.sendNewsletterEmail(mailMagazineUser.getEmailMob(),
											mailMagazine.getTextMob(), body, true);

								} else {
									emailService.sendNewsletterEmail(mailMagazineUser.getEmail(), title, body, true);
								}
								usr.setStatus(1);
								usr.setSendtime(currentDateTime);
								usr.setSent(currentDateTime);
								magUserRepository.save(usr);
								System.out.println("Sent email to: " + mailMagazineUser.getEmail());
							}
						}

			
						// Update the status of the mail magazine body
						mailMagazine.setSendstatus(1);
						mailMagazine.setJobRemark("COMPLETED");
						mailMagazine.setJobStatus("COMPLETED");
						mailMagazine.setSendtime(currentDateTime);
						mailmagazineRepository.save(mailMagazine);
					}
				} catch (Exception e) {
					mailMagazine.setSendstatus(0);
					mailMagazine.setJobRemark(e.getMessage());
					mailMagazine.setJobStatus("STOPED");
					mailMagazine.setSendtime(currentDateTime);
					mailmagazineRepository.save(mailMagazine);
					e.printStackTrace();
					return "Failed to send newsletters: sendMailMagSchedulerService";
				}
			}

			return "Send newsletters successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Failed to send newsletters: sendMailMagSchedulerService";
		}
	}

	public Map<String, Object> nonuserlist(MailMaganizeDTO request) {
		PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
		Map<String, Object> response=new HashMap<>();
		Optional<Mailmagazine> mailmagazineOptional = mailmagazineRepository.findById(request.getMailmagId());
		Page<Object[]> maillist = null;
		
		//Page<Object[]> maillist2 = null;
		if (mailmagazineOptional.isPresent()) {
		    Mailmagazine mailmagazine = mailmagazineOptional.get();
		    List<MailMagUserList> mailmagUserList = new ArrayList<>();
		    
		    if (mailmagazine.getNon()==0) {
		    	if(mailmagazine.getType()==1) {		    	 
		    	 maillist = mailmagazineUserRepository.getAllBbsListWithCount(pageRequest);
		    	
		    	}
		    	if(mailmagazine.getType()==2) {		    	 
			    	 maillist = mailmagazineUserRepository.getAllBbsMobListWithCount(pageRequest);
			   
		    	}
				for (Object[] data : maillist.getContent()) {
							    		MailMagUserList dto = new MailMagUserList();
							            dto.setId((int) data[0]);
							            dto.setDate((Date) data[1]);
							            dto.setEmail((String) data[2]);
							            dto.setName((String) data[3]);
							            dto.setMemberid((String) data[4]);
							            mailmagUserList.add(dto);
		    	}
		    	 response.put("data", mailmagUserList);
		    	 response.put("count", (long) mailmagUserList.size());
		    	return response;
		    }
		    if (mailmagazine.getNon()==1) {
		    	 if (mailmagazine.getType()==1) {
		    	 maillist = mailmagazineUserRepository.getAllGeneralListWithCount(pageRequest);	
		    	 }
		    	 if (mailmagazine.getType()==2) {
			    	 maillist = mailmagazineUserRepository.getAllGeneralMobListWithCount(pageRequest);	
			    	 }

					 for (Object[] data : maillist.getContent()) {
							MailMagUserList dto = new MailMagUserList();
				            dto.setId((int) data[0]);
				            dto.setDate((Date) data[1]);
				            dto.setEmail((String) data[2]);
				            dto.setName((String) data[3]);
				            dto.setMemberid((String) data[4]);
							mailmagUserList.add(dto);
		        }
		   
		    	 response.put("data", mailmagUserList);
				 response.put("count", (long) mailmagUserList.size());
		    	 return response;
		    }
		} else {
			throw new ResourceNotFoundException("Mailmagazine not found with id: " + request.getMailmagId());
		}
		
		return null;
	}

	public String deletemailmaguser(Integer id) {
	    try {
	        Mailmagazine existingMailUser = mailmagazineRepository.findById(id)
	                .orElseThrow(() -> new ResourceNotFoundException("Mailmagazine not found with id: " + id));

	        mailmagazineRepository.deleteById(id);
	        magUserRepository.deleteByMailmagazine(id);
	        return "Deleted successfully";
	    } catch (ResourceNotFoundException e) {
	        return "Mailmagazine not found with id: " + id;
	    } catch (Exception e) {
	        return "An error occurred while deleting the Mailmagazine.";
	    }
	}
	public String deletemailmagBody(Integer id) {
	    try {
	        MailMagazineBody existingMailUser = mailmagazineBodyRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Mailmagazinebody not found with id: " + id));

	        mailmagazineBodyRepository.deleteById(id);
	       
	        return "Deleted successfully";
	    } catch (ResourceNotFoundException e) {
	        return "Mailmagazinebody not found with id: " + id;
	    } catch (Exception e) {
	        return "An error occurred while deleting the Mailmagazinebody.";
	    }
	}
	public MailMagazineBody updateMailMagBody(Integer id, MailMagazineBody magBody) {
		MailMagazineBody existingMailBody = mailmagazineBodyRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MailmagazineBody not found with id: " + id));
		  Integer updatedMailMagJobId = magBody.getMailMagJobId();
		  Integer updatedMailMagStatus = magBody.getSendStatus();
		if (magBody.getSubject() != null) {
			existingMailBody.setSubject(magBody.getSubject());
		}
		if (magBody.getText() != null) {
			existingMailBody.setText(magBody.getText());
		}
		if (magBody.getType() != null) {
			existingMailBody.setType(magBody.getType());
		}
		if (magBody.getDate() != null) {
			existingMailBody.setDate(magBody.getDate());
		}
		if (updatedMailMagStatus != null) {
			existingMailBody.setStatus(magBody.getSendStatus());
		}
		if (magBody.getSendOutTime() != null) {
			existingMailBody.setSendOutTime(magBody.getSendOutTime());
		}
		
		if (updatedMailMagJobId!=null) {
			existingMailBody.setMailMagJobId(magBody.getMailMagJobId());
		}

		return mailmagazineBodyRepository.save(existingMailBody);

	}
	public List<ResponseList> getTopic(String type) {
        List<Object[]> mailMagJobs = mailMagJobRepository.getTopic(type);

        if (mailMagJobs == null || mailMagJobs.isEmpty()) {
            // Handle the situation when the list is null or empty
            return Collections.emptyList(); 
        }

        // Perform the mapping from Object[] to ResponseList
        List<ResponseList> responseList = mailMagJobs.stream()
                .map(mailMagJob -> {
                    ResponseList response = new ResponseList();
                    response.setId((Integer) mailMagJob[0]); 
                    response.setName((String) mailMagJob[1]); 
                    return response;
                })
                .collect(Collectors.toList());

        return responseList;
    }

	@Transactional
	public String sendMagazine(SendMagDTO request) {
		Mailmagazine mailMagazine = mailmagazineRepository.findById(request.getMagId()).orElse(null);
		if (mailMagazine == null) {
            return "Mail magazine id not found";
        }
		//this.deleteUsersByMailMagazineId(request.getMagId());
		magUserRepository.deleteByMailmagazine(request.getMagId());
		
		for (Integer userId : request.getUserIds()) {
            if (!isPushed(userId, request.getMagId())) {
                if (request.isSendNow()) {
                    sendMagNow(userId, request.getMagId());
                } else {
                	MagUser user = new MagUser();
                    user.setMailmagazine(mailMagazine.getId());
                    user.setMailmagazineUser(userId);
                    user.setSendtime(request.getSendTime());
                   
                    magUserRepository.save(user);
                }
            }
        }
		 //UPDATING MAILMAGAZINE TABLE
		if (request.isSendNow()) {
            mailMagazine.setSendStatus(1);
            mailMagazine.setSendTime(LocalDateTime.now());
        } else {
            mailMagazine.setSendStatus(0);
            mailMagazine.setSendTime(request.getSendTime());
        }
        mailmagazineRepository.save(mailMagazine);
		
		 return "Sucessfully Created";
	}

	

	private boolean isPushed(Integer userId, Integer magId) {
		MagUser user = magUserRepository.findByMailmagazineAndMailmagazineUser(magId, userId);
	        return user != null;
	}

	private void sendMagNow(Integer userId, Integer magId) {
		Mailmagazine mailMagazine = mailmagazineRepository.findById(magId).orElse(null);
		MailmagazineUser user = mailmagazineUserRepository.findById(userId).orElse(null);
		if(mailMagazine==null || user==null) {
			return ;
		}
		String email =user.getEmail();
		if(mailMagazine.getType()==2) {
			email = user.getEmailMob();
		}
		if(email==null) return;
		String subject = mailMagazine.getTitle();
		String body="";
		if (mailMagazine.getType()==1) {
			body = mailMagazine.getText();
		}else if (mailMagazine.getType()==2) {
			body = mailMagazine.getTextMob();
		}
		
		emailService.sendMaganizeMail(email, subject, body);
		MagUser maguser = new MagUser();
		maguser.setMailmagazine(mailMagazine.getId());
		maguser.setMailmagazineUser(user.getId());
		maguser.setStatus(1);
		maguser.setSendtime(LocalDateTime.now());
		maguser.setSent(LocalDateTime.now());
		magUserRepository.save(maguser);
		
		
	}


	public String editTopic(Integer id, String topic) {
		MailMagJob existingTopic = mailMagJobRepository.findById(id).orElse(null);
		if (existingTopic == null) {
			return "Mail Magazine id not found";
		}
		existingTopic.setTopic(topic);
		mailMagJobRepository.save(existingTopic);				
		return "Updated successfully";
	}

	public MailMagJob getMailMagJob(Integer id) {
		MailMagJob existingMail = mailMagJobRepository.findById(id)
				.orElseThrow(() -> new ResourceNotFoundException("MailMagJob not found with id: " + id));
		return existingMail;
	}

	public List<MagUser> getMagUsersByMailMagazine(Integer mailmagazine) {
        return magUserRepository.findByMailmagazine(mailmagazine);
    }
	public Long countMailMagazine(Integer mailmagazine)
	{
		return magUserRepository.countByMailmagazine(mailmagazine);
	}

}
