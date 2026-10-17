package com.mentalhealthapi.service;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.server.ResponseStatusException;

import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberDataDownloadDTO;
import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.MemberWithdrawExportDTO;

import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.dto.MailMagazineDTO;
import com.mentalhealthapi.model.Job;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.MemberDeleted;
import com.mentalhealthapi.model.Prefecture;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.model.RemarksInitial;
import com.mentalhealthapi.repository.JobRepository;
import com.mentalhealthapi.repository.MailmagazineRepository;
import com.mentalhealthapi.repository.MailmagazineUserRepository;
import com.mentalhealthapi.repository.MemberDeletedRepository;
import com.mentalhealthapi.repository.MemberRepository;
import com.mentalhealthapi.repository.PrefectureRepository;
import com.mentalhealthapi.repository.RemarksInitialRepository;
import com.mentalhealthapi.repository.RemarksRepository;
import com.opencsv.CSVWriter;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.IOException;
import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.JoinType;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.transaction.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;

@Service
public class MemberService {
	@Autowired
	private JavaMailSender mailSender;
	private final EmailService emailService;
	@PersistenceContext
	private EntityManager entityManager;
	private final MemberRepository memberRepository;
	private final MailmagazineRepository mailmagazineRepository;
	private final MailmagazineUserRepository mailmagazineUserRepository;
	private final MailMagazineService mailMagazineService;
	private final RemarksRepository remarksRepository;
	private final RemarksInitialRepository remarksInitialRepository;
	private final MemberDeletedRepository memberDeletedRepository;
	private final PrefectureRepository prefectureRepository;
	private final JobRepository jobRepository;
	@Autowired
	private ModelMapper modelMapper;

	@Bean
	public ModelMapper modelMapper() {
		return new ModelMapper();
	}

	public MemberService(MemberRepository memberRepository, MailmagazineRepository mailmagazineRepository,
			MailmagazineUserRepository mailmagazineUserRepository, MailMagazineService mailMagazineService,
			RemarksRepository remarksRepository, RemarksInitialRepository remarksInitialRepository,
			MemberDeletedRepository memberDeletedRepository, EmailService emailservice,
			PrefectureRepository prefectureRepository, JobRepository jobRepository) {
		this.memberRepository = memberRepository;
		this.mailmagazineRepository = mailmagazineRepository;
		this.mailmagazineUserRepository = mailmagazineUserRepository;
		this.mailMagazineService = mailMagazineService;
		this.remarksRepository = remarksRepository;
		this.remarksInitialRepository = remarksInitialRepository;
		this.memberDeletedRepository = memberDeletedRepository;
		this.emailService = emailservice;
		this.prefectureRepository = prefectureRepository;
		this.jobRepository = jobRepository;
	}

	public List<Member> searchMember(MemberDTO request) {
		// Define criteria builder and query

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		Root<Member> root = cq.from(Member.class);

		// Create a list to hold the search predicates
		List<Predicate> predicates = new ArrayList<>();

		// Add search conditions based on the request parameters
		if (request.getName() != null) {

			predicates.add(cb.like(root.get("name"), "%" + request.getName() + "%"));
		}
		if (request.getMemberid() != null) {

			predicates.add(cb.equal(root.get("memberid"), request.getMemberid()));
		}
		if (request.getMemberno() != null) {

			predicates.add(cb.equal(root.get("memberno"), request.getMemberno()));
		}
		if (request.getSex() != null) {

			predicates.add(cb.equal(root.get("sex"), request.getSex()));
		}
		if (request.getJob() != null) {

			predicates.add(cb.equal(root.get("job"), request.getJob()));
		}
		if (request.getEmail_pc() != null) {

			predicates.add(cb.equal(root.get("email_pc"), request.getEmail_pc()));
		}
		if (request.getStatus() != null) {

			predicates.add(cb.equal(root.get("status"), request.getStatus()));
		}

		// Apply the predicates to the query
		cq.where(predicates.toArray(new Predicate[0]));

		// Add sorting
		if ((request.getSortField() != null && !request.getSortField().isEmpty())) {
			if (request.getSortDirection() == SortDirection.ASC) {
				cq.orderBy(cb.asc(root.get(request.getSortField())));
			} else {
				cq.orderBy(cb.desc(root.get(request.getSortField())));
			}
		}
		// Create a typed query
		TypedQuery<Member> query = entityManager.createQuery(cq);

		// Apply pagination
		query.setFirstResult(request.getPageNumber() * request.getPageSize());
		query.setMaxResults(request.getPageSize());

		// Execute the query and return the result
		return query.getResultList();
	}

	/**
	 * serarch with total count
	 * 
	 * @return
	 */

	public ApiListResponse<List<Member>> searchMember2(MemberDTO request) {
		// Define criteria builder and query

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> cq = cb.createQuery(Member.class);
		CriteriaQuery<MemberDeleted> cd1 = cb.createQuery(MemberDeleted.class);
		Root<Member> root = cq.from(Member.class);

		// Create a list to hold the search predicates
		List<Predicate> predicates = new ArrayList<>();

		// Add search conditions based on the request parameters
		if (request.getName() != null && request.getName() != "") {

			predicates.add(cb.like(root.get("name"), "%" + request.getName() + "%"));
		}
		if (request.getMemberid() != null && request.getMemberid() != "") {

			predicates.add(cb.equal(root.get("memberid"), request.getMemberid()));
		}
		if (request.getMemberno() != null && request.getMemberno() != "") {

			predicates.add(cb.equal(root.get("memberno"), request.getMemberno()));
		}
		if (request.getSex() != null) {

			predicates.add(cb.equal(root.get("sex"), request.getSex()));
		}
		if (request.getJob() != null) {

			predicates.add(cb.equal(root.get("job"), request.getJob()));
		}
		if (request.getEmail_pc() != null && request.getEmail_pc() != "") {

			Predicate emailPcPredicate = cb.equal(root.get("email_pc"), request.getEmail_pc());
			Predicate emailMobPredicate = cb.equal(root.get("email_mob"), request.getEmail_pc());
			predicates.add(cb.or(emailPcPredicate, emailMobPredicate));
		}

		if (request.getStatus() != null) {

			predicates.add(cb.equal(root.get("status"), request.getStatus()));
		}
		if (request.getNgMember() != null) {

			predicates.add(cb.equal(root.get("ngMember"), request.getNgMember()));
		}
		if (request.getWithdrawList() == false) {
			Predicate statusNotEqual3 = cb.notEqual(root.get("status"), 3);
			Predicate statusNotEqual5 = cb.notEqual(root.get("status"), 5);
			predicates.add(cb.and(statusNotEqual3, statusNotEqual5));
		} else {
			Predicate statusEqual3 = cb.equal(root.get("status"), 3);
			Predicate statusEqual5 = cb.equal(root.get("status"), 5);
			predicates.add(cb.and(statusEqual3, statusEqual5));
		}
		predicates.add(cb.equal(root.get("role"), "USER"));
		// Apply the predicates to the query
		cq.where(predicates.toArray(new Predicate[0]));

		// Add sorting
		if ((request.getSortField() != null && !request.getSortField().isEmpty())) {
			if (request.getSortDirection() == SortDirection.ASC) {
				cq.orderBy(cb.asc(root.get(request.getSortField())));
			} else {
				cq.orderBy(cb.desc(root.get(request.getSortField())));
			}
		}

		// Create a typed query
		TypedQuery<Member> query = entityManager.createQuery(cq);
		// Apply pagination
		query.setFirstResult(request.getPageNumber() * request.getPageSize());
		query.setMaxResults(request.getPageSize());

		// Create the response object and set the count
		ApiListResponse<List<Member>> response = new ApiListResponse<>(1, null, query.getResultList(),
				this.getCountOfFilteredMembers(request));

		return response;

	}

	public Long getCountOfFilteredMembers(MemberDTO request) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Member> root = countQuery.from(Member.class);
		List<Predicate> predicates = new ArrayList<>();

		// Add search conditions based on the request parameters
		if (request.getName() != null) {

			predicates.add(cb.like(root.get("name"), "%" + request.getName() + "%"));
		}
		if (request.getMemberid() != null) {

			predicates.add(cb.equal(root.get("memberid"), request.getMemberid()));
		}
		if (request.getMemberno() != null) {

			predicates.add(cb.equal(root.get("memberno"), request.getMemberno()));
		}
		if (request.getSex() != null) {

			predicates.add(cb.equal(root.get("sex"), request.getSex()));
		}
		if (request.getJob() != null) {

			predicates.add(cb.equal(root.get("job"), request.getJob()));
		}
		if (request.getEmail_pc() != null) {

			predicates.add(cb.equal(root.get("email_pc"), request.getEmail_pc()));
		}
		if (request.getStatus() != null) {

			predicates.add(cb.equal(root.get("status"), request.getStatus()));
		}
		if (request.getNgMember() != null) {

			predicates.add(cb.equal(root.get("ngMember"), request.getNgMember()));
		}
		if (request.getStatus() != null) {

			predicates.add(cb.equal(root.get("status"), request.getStatus()));
		} else {
			if (request.getWithdrawList() == false) {
				Predicate statusNotEqual3 = cb.notEqual(root.get("status"), 3);
				Predicate statusNotEqual5 = cb.notEqual(root.get("status"), 5);
				predicates.add(cb.and(statusNotEqual3, statusNotEqual5));
			} else {
				Predicate statusEqual3 = cb.equal(root.get("status"), 3);
				Predicate statusEqual5 = cb.equal(root.get("status"), 5);
				predicates.add(cb.and(statusEqual3, statusEqual5));
			}
		}
		predicates.add(cb.equal(root.get("role"), "USER"));

		countQuery.where(predicates.toArray(new Predicate[0]));
		countQuery.select(cb.count(root));

		return entityManager.createQuery(countQuery).getSingleResult();
	}

	public ApiListResponse<List<Member>> searchMember3(MemberDTO request) {
		// Define criteria builder and query

		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		CriteriaQuery<Member> memberListQuery = cb.createQuery(Member.class);
		Root<Member> root2 = memberListQuery.from(Member.class);

		CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
		Root<Member> root = countQuery.from(Member.class);

		// Create a list to hold the search predicates
		List<Predicate> predicates = new ArrayList<>();
		List<Predicate> countPredicates = new ArrayList<>();

		// Add search conditions based on the request parameters
		if (request.getName() != null) {

			predicates.add(cb.like(root.get("name"), "%" + request.getName() + "%"));
		}
		if (request.getMemberid() != null) {

			predicates.add(cb.equal(root.get("memberid"), request.getMemberid()));
		}
		if (request.getMemberno() != null) {

			predicates.add(cb.equal(root.get("memberno"), request.getMemberno()));
		}
		if (request.getSex() != null) {

			predicates.add(cb.equal(root.get("sex"), request.getSex()));
		}
		if (request.getJob() != null) {

			predicates.add(cb.equal(root.get("job"), request.getJob().toString()));
		}
		if (request.getEmail_pc() != null) {

			predicates.add(cb.equal(root.get("email_pc"), request.getEmail_pc()));
		}
		if (request.getStatus() != null) {

			predicates.add(cb.equal(root.get("status"), request.getStatus()));
		}

		// Apply the predicates to the query

		// Create a count query

		countQuery.where(predicates.toArray(new Predicate[0]));

		countQuery.select(cb.count(root));

		// Execute the count query
		Long totalCount = entityManager.createQuery(countQuery).getSingleResult();

		memberListQuery.where(predicates.toArray(new Predicate[0]));
		// Add sorting
		if ((request.getSortField() != null && !request.getSortField().isEmpty())) {
			if (request.getSortDirection() == SortDirection.ASC) {
				memberListQuery.orderBy(cb.asc(root.get(request.getSortField())));
			} else {
				memberListQuery.orderBy(cb.desc(root.get(request.getSortField())));
			}
		}
		// Create a typed query

		TypedQuery<Member> query = entityManager.createQuery(memberListQuery);

		// Apply pagination
		query.setFirstResult(request.getPageNumber() * request.getPageSize());
		query.setMaxResults(request.getPageSize());

		// Execute the query and return the result
		System.out.println(query.unwrap(org.hibernate.query.Query.class).getQueryString());
		List<Member> members = query.getResultList();

		// Create the response object and set the count
		ApiListResponse<List<Member>> response = new ApiListResponse<>(1, null, members, totalCount);

		return response;

	}

	public MemberRepository getMemberRepository() {
		return memberRepository;
	}

	public Member saveMember(MemberRequest request) {

		if (memberRepository.findByMemberid(request.getMemberid()).isPresent() == true) {
			throw new IllegalArgumentException("MemberId already exists");
		}
		if (StringUtils.hasText(request.getEmail_pc())) {
			Boolean email = this.checkEmail(request.getEmail_pc(), request.getEmail_pc());
			if (email == true) {
				throw new IllegalArgumentException("Email already exists");
			}
		}
		if (StringUtils.hasText(request.getEmail_mob())) {
			Boolean email = this.checkEmail(request.getEmail_mob(), request.getEmail_pc());
			if (email == true) {
				throw new IllegalArgumentException("Email already exists");
			}
		}

		Member member = new Member();

		member.setRole("USER");
		member.setMemberno(0);
		member.setStatus(0);
		member.setName(request.getName());
		member.setName2(request.getName2());
		member.setNamek(request.getNamek());
		member.setNamek2(request.getNamek2());
		member.setMemberid(request.getMemberid());
		member.setPassword(request.getPassword());
		member.setSex(request.getSex());
		member.setJoinDate(request.getJoinDate());
		member.setChangeDate(request.getChangeDate());
		member.setChangeCause(request.getChangeCause());
		member.setAddress1(request.getAddress1());
		member.setAddress2(request.getAddress2());
		member.setAddress3(request.getAddress3());
		member.setDob(request.getDob());
		member.setJob(request.getJob());
		member.setJob_additional(request.getJob_additional());
		member.setZipcode(request.getZipcode());
		member.setPref(request.getPref());
		member.setMob(request.getMob());
		member.setTel(request.getTel());
		member.setWorkplace(request.getWorkplace());
		member.setHowfound(request.getHowfound());
		member.setEmail_pc(request.getEmail_pc());
		member.setEmail_mob(request.getEmail_mob());
		member.setMailmagazine(request.getMailmagazine());
		member.setRemarks_initial(request.getRemarks_initial());
		member.setNgMember(0);
		member.setDeleteRequest(0);
		member.setDate(request.getDate());
		member.setEmail_smart(request.getEmail_smart());
		member.setRole("USER");
		member.setMemberno(1);
		Member memberdata = memberRepository.save(member);
		int mid = memberdata.getId();
		member.setMemberno(mid);
		member.setId(mid);
		memberRepository.save(member);

		// save to mailmagazine if selected
		MailMagazineDTO mailMagazineDTO = new MailMagazineDTO();
		mailMagazineDTO.setEmailPc(member.getEmail_pc());
		mailMagazineDTO.setEmailMob(member.getEmail_mob());
		mailMagazineDTO.setEmail_smart(member.getEmail_smart());
		mailMagazineDTO.setMailMagazine(member.getMailmagazine());
		mailMagazineDTO.setId(member.getId());
		mailMagazineDTO.setName(member.getName());
		mailMagazineService.processMailMagazine(mailMagazineDTO);
		// SAVING THE REMARKS TO REMARKS TABLE ...
		RemarksInitial remarksInitial = new RemarksInitial();
		remarksInitial.setMember(mid);
		remarksInitial.setTitle(request.getStatement_title());
		remarksInitial.setText(request.getStatement_text());
		remarksInitial.setNormal((byte) 0);
		remarksInitial.setAnxiety((byte) 0);
		remarksInitial.setBlackmail((byte) 0);
		remarksInitial.setGrowingup((byte) 0);
		remarksInitial.setOther((byte) 0);
		remarksInitial.setDate(LocalDateTime.now());
		remarksInitial.setStatus(1);
		RemarksInitial rmk = remarksInitialRepository.save(remarksInitial);
		member.setRemarks_initial(rmk.getId());
		memberRepository.save(member);

		return memberdata;

	}

	public Member saveMemberAdmin(MemberRequest request) {

		if (memberRepository.findByMemberid(request.getMemberid()).isPresent() == true) {
			throw new IllegalArgumentException("MemberId already exists");
		}
		if (StringUtils.hasText(request.getEmail_pc())) {
			Boolean email = this.checkEmail(request.getEmail_pc(), request.getEmail_pc());
			if (email == true) {
				throw new IllegalArgumentException("Email already exists");
			}
		}
		if (StringUtils.hasText(request.getEmail_mob())) {
			Boolean email = this.checkEmail(request.getEmail_mob(), request.getEmail_pc());
			if (email == true) {
				throw new IllegalArgumentException("Email already exists");
			}
		}

		Member member = new Member();
		member.setName(request.getName());
		member.setName2(request.getName2());
		member.setNamek(request.getNamek());
		member.setNamek2(request.getNamek2());
		member.setMemberid(request.getMemberid());
		member.setPassword(request.getPassword());
		member.setSex(request.getSex());
		member.setJoinDate(request.getJoinDate());
		member.setChangeDate(request.getChangeDate());
		member.setChangeCause(request.getChangeCause());
		member.setAddress1(request.getAddress1());
		member.setAddress2(request.getAddress2());
		member.setAddress3(request.getAddress3());
		member.setDob(request.getDob());
		member.setJob(request.getJob());
		member.setJob_additional(request.getJob_additional());
		member.setZipcode(request.getZipcode());
		member.setPref(request.getPref());
		member.setMob(request.getMob());
		member.setTel(request.getTel());
		member.setWorkplace(request.getWorkplace());
		member.setHowfound(request.getHowfound());
		member.setEmail_pc(request.getEmail_pc());
		member.setEmail_mob(request.getEmail_mob());
		member.setMailmagazine(request.getMailmagazine());
		member.setRemarks_initial(request.getRemarks_initial());
		if (request.getUser_type1() != null) {
			member.setStatus(1);
		}
		if (request.getUser_type2() != null) {
			member.setStatus(4);
		}
		if (request.getUser_type3() != null) {
			member.setStatus(3);
		}

		member.setNgMember(request.getNgMember());
		member.setDeleteRequest(request.getDeleteRequest());
		member.setDate(request.getDate());
		member.setEmail_smart(request.getEmail_smart());
		member.setRole("USER");
		member.setMemberno(1);
		Member memberdata = memberRepository.save(member);
		int mid = memberdata.getId();
		member.setMemberno(mid);
		member.setId(mid);
		memberRepository.save(member);

		MailMagazineDTO mailMagazineDTO = new MailMagazineDTO();
		mailMagazineDTO.setEmailPc(request.getEmail_pc());
		mailMagazineDTO.setEmailMob(request.getEmail_mob());
		mailMagazineDTO.setMailMagazine(request.getMailmagazine());
		mailMagazineDTO.setId(mid);
		mailMagazineService.processMailMagazine(mailMagazineDTO);

		// SAVING THE REMARKS TO REMARKS TABLE ...
		RemarksInitial remarksInitial = new RemarksInitial();
		remarksInitial.setMember(mid);
		remarksInitial.setTitle(request.getStatement_title());
		remarksInitial.setText(request.getStatement_text());
		remarksInitial.setNormal(request.getNormal());
		remarksInitial.setAnxiety(request.getAnxiety());
		remarksInitial.setBlackmail(request.getBlackmail());
		remarksInitial.setGrowingup(request.getGrowingup());
		remarksInitial.setOther(request.getOther());
		if (request.isChk_add1() && request.getAddition1() != null) {
			remarksInitial.setAddition1(request.getAddition1());
		}
		if (request.isChk_add2() && request.getAddition2() != null) {
			remarksInitial.setAddition2(request.getAddition2());
		}
		remarksInitial.setDate(LocalDateTime.now());
		remarksInitial.setStatus(1);
		RemarksInitial rmk = remarksInitialRepository.save(remarksInitial);
		Integer rmkid = rmk.getId();
		Member existingMember = memberRepository.findById(mid).orElse(null);
		int memberStatus = existingMember.getStatus();
		existingMember.setRemarks_initial(rmkid);
		memberRepository.save(existingMember);

		if (memberStatus == 1) {
			Remarks remarks = new Remarks();
			remarks.setRemarkNo("RMK");
			remarks.setMember(rmk.getMember());
			remarks.setNormal(rmk.getNormal());
			remarks.setAnxiety(rmk.getAnxiety());
			remarks.setBlackmail(rmk.getBlackmail());
			remarks.setOther(rmk.getOther());
			remarks.setGrowingup(rmk.getGrowingup());
			remarks.setAddition1(rmk.getAddition1());
			remarks.setAddition2(rmk.getAddition2());
			remarks.setTitle(rmk.getTitle());
			remarks.setText(rmk.getText());
			remarks.setStatus(1);
			remarks.setRemarks(0);
			remarks.setDate(rmk.getDate());
			remarksRepository.save(remarks);
			Integer rmid = remarks.getId();
			remarks.setId(rmid);
			remarks.setRemarkNo(generateRMKNo(rmid));
			remarksRepository.save(remarks);
			rmk.setRemarkNo(generateRMKNo(rmid));
			remarksInitialRepository.save(rmk);
		}
		try {
			// get pref name
			Prefecture preftur = prefectureRepository.findById(existingMember.getPref()).orElse(null);
			String pref = preftur.getName();
			Job jobs = jobRepository.findById(Integer.parseInt(existingMember.getJob())).orElse(null);
			String job = jobs.getName();

			// sendMemberRegistrationMail need room type
			emailService.sendMemberEmail(member.getEmail_pc(), member.getName(), memberdata, pref, job);
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return memberdata;

	}

	public Member updateMember(Member member) {
		Member existingMember = memberRepository.findById(member.getId()).orElse(null);
		if (member.getName() != null) {
			existingMember.setName(member.getName());
		}
		// if (member.getMemberno()!=null ) {
		// existingMember.setMemberno(member.getMemberno());
		// }
		if (member.getNamek() != null) {
			existingMember.setNamek(member.getNamek());
		}
		if (member.getName2() != null) {
			existingMember.setNamek(member.getName2());
		}
		if (member.getNamek2() != null) {
			existingMember.setNamek(member.getNamek2());
		}
		if (member.getMemberid() != null) {
			existingMember.setMemberid(member.getMemberid());
		}
		if (member.getStatus() != null) {
			existingMember.setStatus(member.getStatus());
		}
		if (member.getPassword() != null) {
			existingMember.setPassword(member.getPassword());
		}
		if (member.getSex() != null) {
			existingMember.setSex(member.getSex());
		}
		if (member.getDob() != null) {
			existingMember.setDob(member.getDob());
		}
		if (member.getJob() != null) {
			existingMember.setJob(member.getJob());
		}
		if (member.getJob_additional() != null) {
			existingMember.setJob_additional(member.getJob_additional());
		}
		if (member.getZipcode() != null) {
			existingMember.setZipcode(member.getZipcode());
		}
		if (member.getPref() != null) {
			existingMember.setPref(member.getPref());
		}
		if (member.getAddress1() != null) {
			existingMember.setAddress1(member.getAddress1());
		}
		if (member.getAddress2() != null) {
			existingMember.setAddress2(member.getAddress2());
		}
		if (member.getAddress3() != null) {
			existingMember.setAddress3(member.getAddress3());
		}
		if (member.getTel() != null) {
			existingMember.setTel(member.getTel());
		}
		if (member.getWorkplace() != null) {
			existingMember.setWorkplace(member.getWorkplace());
		}
		if (member.getMob() != null) {
			existingMember.setMob(member.getMob());
		}

		if (member.getHowfound() != null) {
			existingMember.setHowfound(member.getHowfound());
		}
		if (member.getEmail_pc() != null) {
			existingMember.setEmail_pc(member.getEmail_pc());
		}
		if (member.getMailmagazine() != null) {
			existingMember.setMailmagazine(member.getMailmagazine());
		}

		if (member.getJoinDate() != null) {
			existingMember.setJoinDate(member.getJoinDate());
		}
		if (member.getChangeDate() != null) {
			existingMember.setChangeDate(member.getChangeDate());
		}
		if (member.getChangeCause() != null) {
			existingMember.setChangeCause(member.getChangeCause());
		}
		if (member.getNgMember() != null) {
			existingMember.setNgMember(member.getNgMember());
		}
		if (member.getDeleteRequest() != null) {
			existingMember.setDeleteRequest(member.getDeleteRequest());
		}
		if (member.getEmail_pc() != null) {
			existingMember.setEmail_pc(member.getEmail_pc());
		}
		if (member.getEmail_smart() != null) {
			existingMember.setEmail_smart(member.getEmail_smart());
		}
		if(member.getIcon() != null) {
			existingMember.setIcon(member.getIcon());
		}
		if(member.getSpeech_title() != null) {
			existingMember.setSpeech_title(member.getSpeech_title());
		}
		if(member.getSpeech_title() != null) {
			existingMember.setSpeech_title(member.getSpeech_title());
		}
		if(member.getMessage() != null) {
			existingMember.setMessage(member.getMessage());
		}
		existingMember.setChangeDate(LocalDate.now());

		System.out.println("smart email>>>" + existingMember.getEmail_smart().toString());

		if (member.getMailmagazine() == 2) {
			System.out.println(member.getId());
			MailmagazineUser mid = mailmagazineUserRepository.findByMember(member.getId());
			if (mid != null)
				mailmagazineUserRepository.deleteById(mid.getId());
		} else if (member.getMailmagazine() == 1) {
			MailmagazineUser mailUser = new MailmagazineUser();
			mailUser.setMember(existingMember.getId());
			mailUser.setEmail(existingMember.getEmail_pc());
			mailUser.setEmailMob(existingMember.getEmail_mob());
			mailUser.setDate(LocalDate.now());
			mailUser.setId(300);
			mailUser.setStatus(1);
			mailmagazineRepository.save(mailUser);
		}
		Member memberUpdated = memberRepository.save(existingMember);
		String[] cc = { "vanisree.co", "neethu@nivid.co" };

		// Sending mail to user
		if (memberUpdated.getEmail_pc() != null)
			emailService.sendUserEditMail(member, member.getEmail_pc(), cc);
		else
			emailService.sendUserEditMail(member, member.getEmail_mob(), cc);

		return memberUpdated;

	}

	/**
	 * update member for admin
	 * 
	 * @param id
	 * @return
	 */

	public Member updateMemberAdmin_old(MemberRequest request) {
		Member existingMember = memberRepository.findById(request.getId())
				.orElseThrow(() -> new RuntimeException("Member not found"));
		Integer MagSub = existingMember.getMailmagazine();
		// Update member properties
		existingMember.setName(request.getName());
		existingMember.setMemberno(request.getMemberno());
		existingMember.setNamek(request.getNamek());
		existingMember.setName2(request.getName2());
		existingMember.setNamek2(request.getNamek2());
		existingMember.setMemberid(request.getMemberid());
		existingMember.setStatus(request.getStatus());
		existingMember.setPassword(request.getPassword());
		existingMember.setSex(request.getSex());
		existingMember.setDob(request.getDob());
		existingMember.setJob(request.getJob());
		existingMember.setJob_additional(request.getJob_additional());
		existingMember.setZipcode(request.getZipcode());
		existingMember.setPref(request.getPref());
		existingMember.setAddress1(request.getAddress1());
		existingMember.setAddress2(request.getAddress2());
		existingMember.setAddress3(request.getAddress3());
		existingMember.setTel(request.getTel());
		existingMember.setWorkplace(request.getWorkplace());
		existingMember.setMob(request.getMob());
		existingMember.setHowfound(request.getHowfound());
		existingMember.setEmail_pc(request.getEmail_pc());
		existingMember.setEmail_mob(request.getEmail_mob());
		existingMember.setMailmagazine(request.getMailmagazine());
		existingMember.setRemarks_initial(request.getRemarks_initial());
		existingMember.setJoinDate(request.getJoinDate());
		existingMember.setChangeDate(request.getChangeDate());
		existingMember.setChangeCause(request.getChangeCause());
		existingMember.setNgMember(request.getNgMember());
		existingMember.setDeleteRequest(request.getDeleteRequest());
		existingMember.setDate(request.getDate());
		existingMember.setEmail_smart(request.getEmail_smart());

		// Save updated member
		Member member = memberRepository.save(existingMember);

		// Update mailmagazineUser if necessary
		if (request.getMailmagazine() != null && !MagSub.equals(request.getMailmagazine())) {
			if (request.getMailmagazine() == 2) {
				MailmagazineUser mid = mailmagazineUserRepository.findByMember(request.getId());
				mailmagazineUserRepository.deleteById(mid.getId());
			} else if (request.getMailmagazine() == 1) {
				MailmagazineUser mailUser = new MailmagazineUser();
				mailUser.setEmail(existingMember.getEmail_pc());
				mailUser.setEmailMob(existingMember.getEmail_mob());
				mailUser.setDate(LocalDate.now());
				mailUser.setMember(existingMember.getId());
				mailUser.setStatus(1);
				mailmagazineRepository.save(mailUser);
			}
		}

		if (request.getStatus() == 3 || request.getStatus() == 5) {
			MemberDeleted memDel = memberDeletedRepository.findByMember(existingMember.getId());

			if (memDel != null && memDel.getMailmagazine() == 1) {
				MailmagazineUser maguser = mailmagazineUserRepository.findByMember(existingMember.getId());
				maguser.setStatus(2);
				mailmagazineUserRepository.save(maguser);

			}
		}
		// Update RemarksInitial
		Optional<RemarksInitial> optionalRemarksInitial = remarksInitialRepository
				.findById(existingMember.getRemarks_initial());
		RemarksInitial rmkIni = optionalRemarksInitial.orElse(new RemarksInitial()); // Default RemarksInitial object if
																						// not found

		rmkIni.setMember(existingMember.getId());
		rmkIni.setTitle(request.getStatement_title());
		rmkIni.setText(request.getStatement_text());
		rmkIni.setNormal(request.getNormal());
		rmkIni.setAnxiety(request.getAnxiety());
		rmkIni.setBlackmail(request.getBlackmail());
		rmkIni.setGrowingup(request.getGrowingup());
		rmkIni.setOther(request.getOther());
		rmkIni.setAddition1(request.getAddition1());
		rmkIni.setAddition2(request.getAddition2());
		rmkIni.setRemarkNo("");

		if (request.isChk_add1() && request.getAddition1() != null) {
			rmkIni.setAddition1(request.getAddition1());
		}
		if (request.isChk_add2() && request.getAddition2() != null) {
			rmkIni.setAddition2(request.getAddition2());
		}
		rmkIni.setDate(LocalDateTime.now());
		rmkIni.setStatus(1);
		remarksInitialRepository.save(rmkIni);

		// Create Remarks if member status is 1
		if (existingMember.getStatus() == 1) {
			List<Remarks> remarksList = remarksRepository.findAllByMemberOrderByDateAsc(existingMember.getId());

			Remarks remarks = null;
			if (!remarksList.isEmpty()) {
				remarks = remarksList.get(0);
			} else {
				remarks = new Remarks();
			}
			remarks.setRemarkNo("RMK");
			remarks.setMember(rmkIni.getMember());
			remarks.setNormal(rmkIni.getNormal());
			remarks.setAnxiety(rmkIni.getAnxiety());
			remarks.setBlackmail(rmkIni.getBlackmail());
			remarks.setOther(rmkIni.getOther());
			remarks.setGrowingup(rmkIni.getGrowingup());
			remarks.setAddition1(rmkIni.getAddition1());
			remarks.setAddition2(rmkIni.getAddition2());
			remarks.setTitle(rmkIni.getTitle());
			remarks.setText(rmkIni.getText());
			remarks.setStatus(1);
			remarks.setRemarks(rmkIni.getRemarks());
			if (remarks.getId() == null) {
				remarks.setDate(rmkIni.getDate());
				remarks.setStatus(1);
			}
			remarksRepository.save(remarks);
			if (remarks.getRemarkNo() == "RMK") {
				remarks.setRemarkNo(generateRMKNo(remarks.getId()));
				remarksRepository.save(remarks);
			}
		}

		if (request.getStatus() == 3 || request.getStatus() == 5) {
			MemberDeleted memDel = memberDeletedRepository.findByMember(existingMember.getId());

			if (memDel == null) {
				// insert to member delete table
				// Map member fields to deleted member
				Optional<Member> memb = memberRepository.findById(existingMember.getId());
				if (memb.isPresent()) {
					Member member1 = memb.get();
					MemberDeleted deletedMember = new MemberDeleted();
					deletedMember.setMember(existingMember.getId());
					deletedMember.setName(member1.getName());
					deletedMember.setMemberno(member1.getMemberno());
					deletedMember.setNamek(member1.getNamek());
					deletedMember.setMemberid(member1.getMemberid());
					deletedMember.setStatus(member1.getStatus());
					deletedMember.setPassword(member1.getPassword());
					deletedMember.setSex(member1.getSex());
					deletedMember.setDob(member1.getDob());
					deletedMember.setJob(member1.getJob());
					// deletedMember.setJob_additional(member1.getJob_additional());
					// deletedMember.setZipcode(member1.getZipcode());
					// deletedMember.setEmail_mob(member1.getEmail_mob());
					deletedMember.setAddress1(member1.getAddress1());
					deletedMember.setAddress2(member1.getAddress2());
					deletedMember.setAddress3(member1.getAddress3());
					deletedMember.setTel(member1.getTel());
					deletedMember.setWorkplace(member1.getWorkplace());
					deletedMember.setMob(member1.getMob());
					deletedMember.setHowfound(member1.getHowfound());
					deletedMember.setEmailPc(member1.getEmail_pc());
					deletedMember.setMailmagazine(member1.getMailmagazine());
					deletedMember.setRemarks_initial(member1.getRemarks_initial());
					deletedMember.setJoinDate(member1.getJoinDate());
					deletedMember.setChangeDate(member1.getChangeDate());
					deletedMember.setChangeCause(member1.getChangeCause());
					// deletedMember.setNgMember(member1.getNgMember());
					// deletedMember.setDeleteRequest(member1.getDeleteRequest());
					deletedMember.setDate(member1.getDate());
					deletedMember.setEmailSmart(member1.getEmail_smart());
					deletedMember.setDateEffect(LocalDateTime.now());
					memberDeletedRepository.save(deletedMember);
				}

			}
		}

		String room = "";
		if (request.getNormal() != 0) {
			room = "普通神経症";
		} else if (request.getAnxiety() != 0) {
			room = "不安神経症";
		} else if (request.getBlackmail() != 0) {
			room = "強迫神経症";
		} else if (request.getOther() != 0) {
			room = "その他";
		} else if (request.getGrowingup() != 0) {
			room = "成長の部屋";
		} else if (request.getAddition1() != null) {
			room = "新規1";
		} else if (request.getAddition2() != null) {
			room = "新規２";
		}

		try {
			if (request.getStatus() == 1) {
				emailService.sendMemberRegistrationMail(member.getEmail_pc(), member.getName(), room, member);
			}
		} catch (MessagingException e) {
			e.printStackTrace();
		}

		return member;
	}

	@Transactional
	public Member updateMemberAdmin(MemberRequest request) {
		Member existingMember = memberRepository.findById(request.getId())
				.orElseThrow(() -> new RuntimeException("Member not found"));
		Integer MagSub = existingMember.getMailmagazine();

		// Update member properties
		updateMemberProperties(existingMember, request);

		// Save updated member
		Member member = memberRepository.save(existingMember);

		// Update mailmagazineUser if necessary
		updateMailmagazineUser(request, existingMember, MagSub);

		// Update RemarksInitial
		updateRemarksInitial(existingMember, request);

		// Create Remarks if member status is 1
		if (existingMember.getStatus() == 1) {
			createRemarks(existingMember, request);
		}

		// Delete member from member_deleted table if necessary
		if (request.getStatus() == 3 || request.getStatus() == 5) {
			MailmagazineUser maguser = mailmagazineUserRepository.findByMember(existingMember.getId());
			if (maguser != null && maguser.getStatus() == 1) {
				maguser.setStatus(2);
				mailmagazineUserRepository.save(maguser);
			}
		}
		if (request.getStatus() == 3 || request.getStatus() == 5) {
			deleteMemberFromDeletedTable(existingMember);
		}

		String room = "";
		if (request.getNormal() != 0) {
			room = "普通神経症";
		} else if (request.getAnxiety() != 0) {
			room = "不安神経症";
		} else if (request.getBlackmail() != 0) {
			room = "強迫神経症";
		} else if (request.getOther() != 0) {
			room = "その他";
		} else if (request.getGrowingup() != 0) {
			room = "成長の部屋";
		} else if (request.getAddition1() != null) {
			room = "新規1";
		} else if (request.getAddition2() != null) {
			room = "新規２";
		}
		System.out.println("<I am here >>>"+request.getStatus());
		try {
			if (request.getStatus() == 1) {
				emailService.sendMemberRegistrationMail(member.getEmail_pc(), member.getName(), room, member);
			}
		} catch (MessagingException e) {
			
			e.printStackTrace();
		}

		return member;

	}

	private void updateMemberProperties(Member existingMember, MemberRequest request) {
		existingMember.setName(request.getName());
		existingMember.setMemberno(request.getMemberno());
		existingMember.setNamek(request.getNamek());
		existingMember.setName2(request.getName2());
		existingMember.setNamek2(request.getNamek2());
		existingMember.setMemberid(request.getMemberid());
		existingMember.setStatus(request.getStatus());
		existingMember.setPassword(request.getPassword());
		existingMember.setSex(request.getSex());
		existingMember.setDob(request.getDob());
		existingMember.setJob(request.getJob());
		existingMember.setJob_additional(request.getJob_additional());
		existingMember.setZipcode(request.getZipcode());
		existingMember.setPref(request.getPref());
		existingMember.setAddress1(request.getAddress1());
		existingMember.setAddress2(request.getAddress2());
		existingMember.setAddress3(request.getAddress3());
		existingMember.setTel(request.getTel());
		existingMember.setWorkplace(request.getWorkplace());
		existingMember.setMob(request.getMob());
		existingMember.setHowfound(request.getHowfound());
		existingMember.setEmail_pc(request.getEmail_pc());
		existingMember.setEmail_mob(request.getEmail_mob());
		existingMember.setMailmagazine(request.getMailmagazine());
		existingMember.setRemarks_initial(request.getRemarks_initial());
		existingMember.setJoinDate(request.getJoinDate());
		existingMember.setChangeDate(request.getChangeDate());
		existingMember.setChangeCause(request.getChangeCause());
		existingMember.setNgMember(request.getNgMember());
		existingMember.setDeleteRequest(request.getDeleteRequest());
		existingMember.setDate(request.getDate());
		existingMember.setEmail_smart(request.getEmail_smart());
	}

	private void updateMailmagazineUser(MemberRequest request, Member existingMember, Integer magSub1) {

		Integer magSub = existingMember.getMailmagazine();
		Integer oldMagSub = magSub1;
		if (oldMagSub != null && magSub.equals(oldMagSub) || !magSub.equals(oldMagSub)) {
			if (magSub == 2) {
				MailmagazineUser mid = mailmagazineUserRepository.findByMember(existingMember.getId());

				if (mid != null) {
					mailmagazineUserRepository.deleteById(mid.getId());
				}
			} else if (magSub == 1) {
				// Retrieve existing MailmagazineUser for the member
				MailmagazineUser magUser = mailmagazineUserRepository.findByMember(existingMember.getMemberNo());

				// If no existing MailmagazineUser is found, create a new instance
				if (magUser == null) {
					magUser = new MailmagazineUser();
				}
				// Update the MailmagazineUser details
				magUser.setName(existingMember.getName());
				magUser.setEmail(existingMember.getEmail_pc());
				magUser.setEmailMob(existingMember.getEmail_mob());
				magUser.setDate(LocalDate.now());
				magUser.setMember(existingMember.getId());
				magUser.setStatus(1);

				// Save the updated or newly created MailmagazineUser
				mailmagazineUserRepository.save(magUser);

			}
		}
	}

	private void updateRemarksInitial(Member existingMember, MemberRequest request) {
		Optional<RemarksInitial> optionalRemarksInitial = remarksInitialRepository
				.findById(existingMember.getRemarks_initial());
		RemarksInitial rmkIni = optionalRemarksInitial.orElse(new RemarksInitial()); // Default RemarksInitial object if
																						// not found
		System.out.print("remark initial>>>>>r");
		rmkIni.setMember(existingMember.getId());
		rmkIni.setTitle(request.getStatement_title());
		rmkIni.setText(request.getStatement_text());
		rmkIni.setNormal(request.getNormal());
		rmkIni.setAnxiety(request.getAnxiety());
		rmkIni.setBlackmail(request.getBlackmail());
		rmkIni.setGrowingup(request.getGrowingup());
		rmkIni.setOther(request.getOther());
		rmkIni.setAddition1(request.getAddition1());
		rmkIni.setAddition2(request.getAddition2());
		rmkIni.setRemarkNo("");

		if (request.isChk_add1() && request.getAddition1() != null) {
			rmkIni.setAddition1(request.getAddition1());
		}
		if (request.isChk_add2() && request.getAddition2() != null) {
			rmkIni.setAddition2(request.getAddition2());
		}
		rmkIni.setDate(LocalDateTime.now());
		rmkIni.setStatus(1);
		remarksInitialRepository.save(rmkIni);

	}

	private void createRemarks(Member existingMember, MemberRequest request) {
		Optional<RemarksInitial> optionalRemarksInitial = remarksInitialRepository
				.findById(existingMember.getRemarks_initial());
		RemarksInitial rmkIni = optionalRemarksInitial.orElse(new RemarksInitial());
		if (existingMember.getStatus() == 1) {
			List<Remarks> remarksList = remarksRepository.findAllByMemberOrderByDateAsc(existingMember.getId());

			Remarks remarks = null;
			if (!remarksList.isEmpty()) {
				remarks = remarksList.get(0);
			} else {
				remarks = new Remarks();
			}
			remarks.setRemarkNo("RMK");
			remarks.setMember(rmkIni.getMember());
			remarks.setNormal(rmkIni.getNormal());
			remarks.setAnxiety(rmkIni.getAnxiety());
			remarks.setBlackmail(rmkIni.getBlackmail());
			remarks.setOther(rmkIni.getOther());
			remarks.setGrowingup(rmkIni.getGrowingup());
			remarks.setAddition1(rmkIni.getAddition1());
			remarks.setAddition2(rmkIni.getAddition2());
			remarks.setTitle(rmkIni.getTitle());
			remarks.setText(rmkIni.getText());
			remarks.setStatus(1);
			remarks.setRemarks(rmkIni.getRemarks());
			if (remarks.getId() == null) {
				remarks.setDate(rmkIni.getDate());
				remarks.setStatus(1);
			}
			remarksRepository.save(remarks);
			if (remarks.getRemarkNo() == "RMK") {
				remarks.setRemarkNo(generateRMKNo(remarks.getId()));
				remarksRepository.save(remarks);
			}
		}
	}

	private void deleteMemberFromDeletedTable(Member existingMember) {
		MemberDeleted memDel = memberDeletedRepository.findByMember(existingMember.getId());

		if (memDel == null) {
			// insert to member delete table
			// Map member fields to deleted member
			Optional<Member> memb = memberRepository.findById(existingMember.getId());
			if (memb.isPresent()) {
				Member member1 = memb.get();
				MemberDeleted deletedMember = new MemberDeleted();
				deletedMember.setMember(existingMember.getId());
				deletedMember.setName(member1.getName());
				deletedMember.setMemberno(member1.getMemberno());
				deletedMember.setNamek(member1.getNamek());
				deletedMember.setMemberid(member1.getMemberid());
				deletedMember.setStatus(member1.getStatus());
				deletedMember.setPassword(member1.getPassword());
				deletedMember.setSex(member1.getSex());
				deletedMember.setDob(member1.getDob());
				deletedMember.setJob(member1.getJob());
				deletedMember.setAddress1(member1.getAddress1());
				deletedMember.setAddress2(member1.getAddress2());
				deletedMember.setAddress3(member1.getAddress3());
				deletedMember.setTel(member1.getTel());
				deletedMember.setWorkplace(member1.getWorkplace());
				deletedMember.setMob(member1.getMob());
				deletedMember.setHowfound(member1.getHowfound());
				deletedMember.setEmailPc(member1.getEmail_pc());
				deletedMember.setMailmagazine(member1.getMailmagazine());
				deletedMember.setRemarks_initial(member1.getRemarks_initial());
				deletedMember.setJoinDate(member1.getJoinDate());
				deletedMember.setChangeDate(member1.getChangeDate());
				deletedMember.setChangeCause(member1.getChangeCause());
				deletedMember.setDate(member1.getDate());
				deletedMember.setEmailSmart(member1.getEmail_smart());
				deletedMember.setDateEffect(LocalDateTime.now());
				memberDeletedRepository.save(deletedMember);
			}

		}
	}

	/**
	 * GenarateRNKNo remark no
	 * 
	 * @param id
	 * @return
	 */

	private String generateRMKNo(Integer id) {
		return "RMK_" + id;
	}

	public Map<String, Object> getMemberById(int id) {
		Member member = memberRepository.findById(id).orElseThrow(() -> new RuntimeException("Member not found"));
		List<Object[]> memberDataWithRemarks = memberRepository.getMemberDataWithRemarks(id);

		// Construct the response map
		Map<String, Object> responseMap = new HashMap<>();

		// Map member data
		responseMap.put("member",
				memberDataWithRemarks != null && memberDataWithRemarks.size() > 0 ? memberDataWithRemarks.get(0)[0]
						: member);

		// Map remarks data
		List<Object> remarksList = new ArrayList<>();
		if (memberDataWithRemarks != null && memberDataWithRemarks.size() > 0) {
			if (memberDataWithRemarks.get(0).length > 1) {
				for (int i = 1; i < memberDataWithRemarks.get(0).length; i++) {
					remarksList.add(memberDataWithRemarks.get(0)[i]);
				}
			}
		}
		responseMap.put("remarks", remarksList);

		return responseMap;
	}

	public void sendMemberEmail(int id) throws MessagingException {
		Member member = memberRepository.findById(id)
				.orElseThrow(() -> new IllegalArgumentException("Member not found"));
		Prefecture prefecture = prefectureRepository.findById(member.getPref())
				.orElseThrow(() -> new IllegalArgumentException("Member prefecture not found"));
		Job job = jobRepository.findById(Integer.parseInt(member.getJob()))
				.orElseThrow(() -> new IllegalArgumentException("Member job not found"));

		emailService.sendMemberEmail(member.getEmail_pc(), member.getName(), member, prefecture.getName(),
				job.getName());
	}

	/**
	 * get all withdraw users list
	 */

	public List<Map<String, Object>> getAllWithdrawMembers(MemberDTO request) {

		PageRequest pageRequest = PageRequest.of(request.getPageNumber(), request.getPageSize());
		List<Map<String, Object>> resultList = new ArrayList<>();
		Page<Object[]> memberAndDeletedInfo = memberRepository.getMemberAndDeletedInfo(pageRequest);

		for (Object[] objects : memberAndDeletedInfo.getContent()) {
			Member member = (Member) objects[0];
			MemberDeleted memberDeleted = (MemberDeleted) objects[1];

			String deletedReason = memberDeleted.getReasonLeaving(); // Assuming 'reasonLeaving' is the property for the
																		// deleted reason

			Map<String, Object> jsonObject = new HashMap<>();
			jsonObject.put("member", member);
			jsonObject.put("deletedReason", deletedReason);

			resultList.add(jsonObject);
		}

		return resultList;

	}

	/**
	 * get count of withdraw members
	 */
	public Long getCountWithdrawMembers() {
		return memberRepository.getTotalMemberAndDeletedCount();
	}

	/**
	 * get all member data with remarks
	 */
	public List<Map<String, Object>> memberManagement(MemberDTO request) {

		List<Map<String, Object>> resultList = new ArrayList<>();

		List<Object[]> memberAndDeletedInfo = memberRepository.getMemberWithRemarks(request.getStatus());

		for (Object[] objects : memberAndDeletedInfo) {
			Member member = (Member) objects[0];
			RemarksInitial ri = (RemarksInitial) objects[1];
			String title = ri.getTitle(); // Assuming 'reasonLeaving' is the property for the deleted reason
			Map<String, Object> jsonObject = new HashMap<>();
			jsonObject.put("member", member);
			jsonObject.put("remark", ri);

			resultList.add(jsonObject);
		}

		return resultList;

	}

	public Member validateUser(String memberid, String password) {
		// return memberRepository.fin(id).orElse(null);
		Member m = memberRepository.findByMemberidAndPasswordAndStatus(memberid, password);

		if (m != null && m.getPassword().equals(password)) {
			return m;
		} else {
			return m;
		}
	}

	public String generateToken(String username) {
		Map<String, Object> claims = new HashMap<>();
		claims.put("username", username);

		Date now = new Date();
		Date expiration = new Date(now.getTime() + 96000000); // Token expires in 60 min 300000

		String token = Jwts.builder().setClaims(claims).setSubject(username).setIssuedAt(now).setExpiration(expiration)
				.signWith(SignatureAlgorithm.HS256, "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437")
				.compact();

		return token;
	}

	public List<Object[]> getAllMemberData() {
		return memberRepository.getMemberAndDeletedInfoByStatus();
	}

	public List<Member> getAllMembers() {
		return memberRepository.findAll();
	}

	public Member blockMember(int id) {
		Member existingMember = memberRepository.findById(id).orElse(null);
		existingMember.setNgMember(1);
		return memberRepository.save(existingMember);
	}

	public Member unBlockMember(int id) {
		Member existingMember = memberRepository.findById(id).orElse(null);
		existingMember.setNgMember(0);
		return memberRepository.save(existingMember);
	}

	public Member deleteMember(int memberId) {
		// Retrieve member
		Member member = memberRepository.findById(memberId).orElseThrow(() -> new RuntimeException("Member not found"));

		// Update member status
		member.setStatus(3);
		member.setChangeDate(LocalDate.now());
		Member membr = memberRepository.save(member);

		// Map member fields to deleted member
		MemberDeleted deletedMember = modelMapper.map(member, MemberDeleted.class);
		String[] cc = { "vanisree@nivid.co", "neethu@nivid.co" };

		deletedMember.setDateEffect(LocalDateTime.now());
		// memberDeletedRepository.save(deletedMember);
		if (member.getEmail_pc() != null)
			emailService.sendUserDeletedMail(member, member.getEmail_pc(), cc);
		else
			emailService.sendUserDeletedMail(member, member.getEmail_mob(), cc);
		return membr;
		// return ResponseEntity.ok("Member deleted successfully");
	}

	public String exportMemberDataCsv(MemberDataDownloadDTO request, HttpServletResponse response) {

		response.setContentType("text/csv");
		response.setCharacterEncoding("UTF-8"); // Set the character encoding
		response.setContentType("text/csv; charset=UTF-8");

		String headerKey = "Content-Disposition";
		String headerValue = "attachment; filename=csvmember_details.csv";
		response.setHeader(headerKey, headerValue);
		List<Object[]> memberInfoList = null;

		if (request.getFromDate() != null) {
			LocalDate date1 = LocalDate.parse(request.getFromDate());
			LocalDate date2 = LocalDate.parse(request.getToDate());

			memberInfoList = memberRepository.getMemberInfoByDateRange(date1, date2);
		}
		if (request.isBatch())
			memberInfoList = memberRepository.getMemberInfoByIdAndStatusNot3AndNot5(1);
		String[] csvHeader = null;

		csvHeader = new String[] { "会員番号", "	名前（漢字）", "	名前（漢字）2", "	ふりがな", "	ふりがな2", "	ID", "	パスワード", "	性別",
				"	生年月日", "	職業	", "職業の詳細", "県", "住所１ （都道府）", "	住所2 （市町村）", "	住所3 （丁目番地）", "	電話", "	携帯",
				"	勤め先・学校", "	何で知ったか", "	メールアドレス（PC）", "	メールアドレス（携帯）", "	メルマガ購読の有無", "	タイプ区分", "	入会日", "	状況	",
				" 変更日", "	理由", "	発言タイトル", "	発言内容" };
		try (CSVWriter csvWriter = new CSVWriter(response.getWriter())) {
			// try (CSVWriter csvWriter = new CSVWriter(new
			// FileWriter("csvmember_detail.csv"))) {
			// Write the CSV header
			csvWriter.writeNext(csvHeader);
			for (Object[] objects : memberInfoList) {
				String[] csvRow = new String[objects.length];
				for (int i = 0; i < objects.length; i++) {
					if (i == 22) { // Assuming 22 is the index of the remark ID
						Integer remarkId = (Integer) objects[i];
						// Fetch remarks data by ID from the repository
						RemarksInitial remarks = remarksInitialRepository.findById(remarkId).orElse(null);
						if (remarks != null) {
							// Set values based on remarks fields
							if (remarks.getNormal() == 1) {
								csvRow[i] = "普通神経症";
							} else if (remarks.getAnxiety() == 1) {
								csvRow[i] = "不安神経症";
							} else if (remarks.getGrowingup() == 1) {
								csvRow[i] = "成長の部屋";
							} else if (remarks.getBlackmail() == 1) {
								csvRow[i] = "強迫神経症";
							} else if (remarks.getOther() == 1) {
								csvRow[i] = "その他";
							} else if (remarks.getAddition1() != null) {
								csvRow[i] = remarks.getAddition1();
							} else if (remarks.getAddition2() != null) {
								csvRow[i] = remarks.getAddition2();
							}
						} else {
							csvRow[i] = ""; // Default value if remarks not found
						}
					} else {
						csvRow[i] = objects[i] != null ? objects[i].toString() : "";
					}
				}
				csvWriter.writeNext(csvRow);
				csvWriter.flush();
			}
			// csvWriter.close();
			return "CSV file created successfully.";
		} catch (IOException | java.io.IOException e) {
			System.out.println("Error while writing CSV file: " + e.getMessage());
			return "Failed to export file";
		}

	}

	public String checkMemberId(Member request) {
		if (memberRepository.findByMemberid(request.getMemberid()).isPresent() == true) {
			return "MemberId already exists";
		} else {
			return "Memberid doesnot exist";
		}
	}

	public Boolean checkEmail(String emailPc, String emailMob) {
		List<Member> emailpc = memberRepository.findByEmailPcOrEmailMob(emailPc);
		if (emailpc.size() == 0) {
			return false;
		} else
			return true;
	}

	public List<Prefecture> prefList() {

		return prefectureRepository.findAll();
	}

	public List<Job> jobList() {
		return jobRepository.findAll();
	}

}
