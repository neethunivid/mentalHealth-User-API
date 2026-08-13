package com.mentalhealthapi.service;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.*;
import java.util.Optional;
import java.util.stream.Collectors;

import org.jsoup.Jsoup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.mentalhealthapi.dto.ArchieveRecordResponse;
import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.CheckSheetTemplateDTO;
import com.mentalhealthapi.dto.CreateReplyDTO;
import com.mentalhealthapi.dto.DiagnoseCheckDTO;
import com.mentalhealthapi.dto.DiagnoseEditUserDTO;
import com.mentalhealthapi.dto.DiagnoseMailMagazineDTO;
import com.mentalhealthapi.dto.DiagnosisMailingResponse;
import com.mentalhealthapi.model.DiagnoseMailMagazine;
import com.mentalhealthapi.model.DiagnoseMailUsers;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.DiagnoseMailMagazineRepository;
import com.mentalhealthapi.repository.DiagnoseMailUserRepository;
import com.mentalhealthapi.dto.ReservationDTO;
import com.mentalhealthapi.model.ChecksheetTemplate;
import com.mentalhealthapi.model.ChecksheetType;
import com.mentalhealthapi.model.DiagnoseCheck;
import com.mentalhealthapi.model.ReservationSlot;
import com.mentalhealthapi.repository.CheckSheetTemplateRepository;
import com.mentalhealthapi.repository.CheckSheetTypeRepository;
import com.mentalhealthapi.repository.DiagnoseCheckRepository;
import com.mentalhealthapi.repository.RegionRepository;

import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.transaction.Transactional;

@Service
public class CheckSheetMailService {
	@PersistenceContext
	private EntityManager entityManager;
	private final DiagnoseCheckRepository diagonsecheckRepository;
	private final CheckSheetTemplateRepository checksheetTemplateRepository;
	private final CheckSheetTypeRepository checkSheetTypeRepository;
	private final DiagnoseMailMagazineRepository diagnoseMailMagazineRepository;
	private final EmailService emailService;
	@Autowired
	private final DiagnoseMailUserRepository diagnoseMailUsersRepository;

	public CheckSheetMailService(DiagnoseCheckRepository diagonsecheckRepository,
			CheckSheetTemplateRepository checksheetTemplateRepository, EmailService emailService,
			DiagnoseMailUserRepository diagnoseMailUsersRepository, CheckSheetTypeRepository checkSheetTypeRepository,
			DiagnoseMailMagazineRepository diagnoseMailMagazineRepository) {
		this.diagonsecheckRepository = diagonsecheckRepository;
		this.checkSheetTypeRepository = checkSheetTypeRepository;
		this.diagnoseMailUsersRepository = diagnoseMailUsersRepository;
		this.checksheetTemplateRepository = checksheetTemplateRepository;
		this.diagnoseMailMagazineRepository = diagnoseMailMagazineRepository;
		this.emailService = emailService;
	}

	public List<Object> listCheckSheet(CheckSheetListDTO request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);
		Root<DiagnoseCheck> root = criteriaQuery.from(DiagnoseCheck.class);
		Predicate predicate = buildPredicate(request, criteriaBuilder, root);
		criteriaQuery.where(predicate);

		// order by
		criteriaQuery.orderBy(criteriaBuilder.desc(root.get("id"))); // Ordering by id in descending order

		List<Object> result = entityManager.createQuery(criteriaQuery)
				.setFirstResult(Math.max((request.getPageNumber() - 1) * request.getPageSize(), 0))
				.setMaxResults(request.getPageSize()).getResultList();

		return result;

	}

	public Long countCheckSheet(CheckSheetListDTO request) {
		CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
		CriteriaQuery<Object> criteriaQuery = criteriaBuilder.createQuery(Object.class);
		CriteriaQuery<Long> countQuery = criteriaBuilder.createQuery(Long.class);
		Root<DiagnoseCheck> countRoot = countQuery.from(DiagnoseCheck.class);
		Predicate countPredicate = buildPredicate(request, criteriaBuilder, countRoot);
		countQuery.select(criteriaBuilder.count(countRoot)).where(countPredicate);

		return entityManager.createQuery(countQuery).getSingleResult();

	}

	public DiagnoseMailMagazine createReply(CreateReplyDTO request) {
		DiagnoseMailMagazine dmag = new DiagnoseMailMagazine();
		ChecksheetType ck = checkSheetTypeRepository.findById(request.getCheckSheetType()).orElse(null);
		dmag.setCheckSheetType(ck);
		dmag.setMail_type(request.getMail_type());
		dmag.setContent(request.getContent());
		dmag.setTitle(request.getTitle());
		dmag.setMagMailSubject(request.getMagMailSubject());
		dmag.setCreatedOn(LocalDateTime.now());
		dmag.setModifiedOn(LocalDateTime.now());
		dmag.setSendStatus((byte) 0);
		dmag.setJobRemark("PENDING");
		dmag.setJobStatus("PENDING");
		// request.setSentTime(null);
		return diagnoseMailMagazineRepository.save(dmag);

	}

	public DiagnoseMailMagazine saveEmail(DiagnoseMailMagazineDTO request) {
        List<Integer> checkId = request.getDiagonseCheckID();
		ChecksheetType cst = checkSheetTypeRepository.findById(request.getCheckSheetTypeID()).orElse(null);
		DiagnoseMailMagazine entity = diagnoseMailMagazineRepository.findById(request.getDiagonseMagID()).orElse(null);
		//DiagnoseMailMagazine entity = new DiagnoseMailMagazine();
		entity.setCreatedOn(LocalDateTime.now());
		entity.setModifiedOn(LocalDateTime.now());
		entity.setSendStatus((byte) 0);
		entity.setMagMailSubject(request.getMagMailSubject());
		entity.setTitle(request.getTitle());
		entity.setSentTime(request.getSentTime());
		entity.setMail_type((byte) request.getMailType());
		entity.setContent(request.getContent());
		entity.setJobRemark("PENDING");
		entity.setJobStatus("PENDING");
		entity.setCheckSheetType(cst);
		entity.setId(request.getDiagonseMagID());
		// Save the entity
		DiagnoseMailMagazine result = diagnoseMailMagazineRepository.save(entity);
		
		
        for(int i=0;i<checkId.size();i++) {
        DiagnoseMailUsers usersEntity = new DiagnoseMailUsers();	
		DiagnoseCheck dc = diagonsecheckRepository.findById(checkId.get(i)).orElse(null);
		usersEntity.setDiagnoseCheck(dc);
		usersEntity.setSendStatus((byte) 0);
		usersEntity.setSendTime(request.getSentTime());
		usersEntity.setDiagnoseMailMagazine(entity);
		diagnoseMailUsersRepository.save(usersEntity);
        }

		return result;

	}

	private Predicate buildPredicate(CheckSheetListDTO request, CriteriaBuilder criteriaBuilder,
			Root<DiagnoseCheck> root) {
		List<Predicate> predicates = new ArrayList<>();

		if (request.getCheckSheetType() != 0 && request.getCheckSheetType() != null) {
			predicates.add(criteriaBuilder.equal(root.get("checkSheetType"), request.getCheckSheetType()));
		}

		predicates.add(criteriaBuilder.equal(root.get("isArchived"), (byte) 0));

		predicates.add(criteriaBuilder.equal(root.get("isDeleted"), (byte) 0));

		return criteriaBuilder.and(predicates.toArray(new Predicate[0]));
	}

	public ChecksheetTemplate showTemplateText(Integer id) {
		ChecksheetTemplate chksheet = checksheetTemplateRepository.findById(id)
				.orElseThrow(() -> new RuntimeException("id not found"));

		return chksheet;
	}

	public List<ChecksheetTemplate> listTemplateText(CheckSheetListDTO request) {
		try {
			CriteriaBuilder cb = entityManager.getCriteriaBuilder();
			CriteriaQuery<ChecksheetTemplate> cq = cb.createQuery(ChecksheetTemplate.class);
			Root<ChecksheetTemplate> root = cq.from(ChecksheetTemplate.class);
			List<Predicate> predicates = new ArrayList<>();
			if (request.getCheckSheetType() != 0) {

				predicates.add(cb.equal(root.get("checksheetType"), request.getCheckSheetType()));
			}
			if (request.getTemplate_type() != 0) {

				predicates.add(cb.equal(root.get("templateType"), request.getTemplate_type()));
			}
			predicates.add(cb.equal(root.get("isDeleted"), 0));
			cq.where(predicates.toArray(new Predicate[0]));
			TypedQuery<ChecksheetTemplate> query = entityManager.createQuery(cq);
			return query.getResultList();
		} catch (Exception e) {

			e.printStackTrace();

			return Collections.emptyList();
		}
	}

	public List<DiagnosisMailingResponse> diagnosisMailingList(CheckSheetListDTO request) {
		try {
			PageRequest pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize());
			List<Object[]> tupleList = diagnoseMailMagazineRepository.findDiagnoseMailMagazineList(pageable)
					.getContent();

			List<DiagnosisMailingResponse> result = new ArrayList<>();
		
			Map<Integer, DiagnosisMailingResponse> resultMap = new HashMap<>();

			for (Object[] tuple : tupleList) {
			
			    Integer id = (Integer) tuple[0];
			    String checkSheetName = (String) tuple[1];
			    String device = (String) tuple[2];
			    Timestamp timestamp = (Timestamp) tuple[3];
			    LocalDateTime sentTime = timestamp!=null?timestamp.toLocalDateTime():null;

			    Byte sendStatus = (Byte) tuple[4];
			    String userEmail = (String) tuple[5];
			    String checkSheetTypeId = (String) tuple[6];
			    Integer typeId = (Integer) tuple[7];
			    String magMailSubject = (String) tuple[8];
			    String content = (String) tuple[9];
			    String title = (String) tuple[10];
			    
			    // Check if the result map already contains an entry for this id
//			    if (resultMap.containsKey(id)) {
//			        // If it does, concatenate the userEmail with a comma separator
//			        DiagnosisMailingResponse existingDto = resultMap.get(id);
//			        existingDto.setUserEmail(existingDto.getUserEmail() + ", " + userEmail);
//			    } else {
//			  
			        DiagnosisMailingResponse dto = new DiagnosisMailingResponse(id, checkSheetName, device, sentTime,
			            sendStatus, userEmail, typeId, checkSheetTypeId, magMailSubject, content, title);
			        resultMap.put(id, dto);
//			    }
			}

			result.addAll(resultMap.values());
			Collections.sort(result, (dto1, dto2) -> Integer.compare(dto2.getId(), dto1.getId()));

			return result;
		} catch (Exception e) {

			e.printStackTrace();
			return Collections.emptyList();
		}
	}

	public int diagnosisMailingListCount() {
		return diagnoseMailMagazineRepository.findCountDiagnoseMailMagazineList();

	}
	public int diagnosisMailingDistinctCount() {
		return diagnoseMailMagazineRepository.countDistinctIds();

	}
	/** After sending email and reservation list delete email */
	@Transactional
	public String deleteEmail(int id) {
		try {

			DiagnoseMailMagazine magazine = diagnoseMailMagazineRepository.findById(id).orElse(null);
			if (magazine != null) {
				// Delete associated DiagnoseMailUsers
				diagnoseMailUsersRepository.deleteByDiagnoseMailMagazine(magazine);

				// Delete DiagnoseMailMagazine
				diagnoseMailMagazineRepository.deleteById(id);

				return "Deleted successfully";
			} else {
				return "Magazine not found";
			}
		} catch (Exception e) {
			return e.getMessage();
		}
	}

	public String getEmailAddresses(List<Integer> ids) {
		List<String> emailList = diagonsecheckRepository.findDistinctUserEmailsByIdIn(ids);
		return String.join(",", emailList);

	}
	/**deleting  data from 'diagnose_mail_users' tables
	 *updating modified_on column of diagnosis_mail_magzine
	 *inserting new data to users and diagnose check
	 * @param magid
	 * @param request
	 * @return
	 */
	public String editUsersSenddate(int magid, DiagnoseEditUserDTO request) {
		try {

			DiagnoseMailMagazine magazine = diagnoseMailMagazineRepository.findById(magid).orElse(null);
			if (magazine != null) {
				//diagnoseMailUsersRepository.deleteByDiagnoseMailMagazine(magazine);
				List<DiagnoseMailUsers> mailUsersToDelete = diagnoseMailUsersRepository
						.findByDiagnoseMailMagazine(magazine);

				for (DiagnoseMailUsers mailUser : mailUsersToDelete) {
					diagnoseMailUsersRepository.delete(mailUser);
				}
				magazine.setModifiedOn(LocalDateTime.now());
				magazine.setSendStatus((byte) 0);
				diagnoseMailMagazineRepository.save(magazine);
				for (int mailListId : request.getCheckIds()) {
					DiagnoseCheck dc = diagonsecheckRepository.findById(mailListId).orElse(null);
					DiagnoseMailUsers mailUser = new DiagnoseMailUsers();
					mailUser.setDiagnoseMailMagazine(magazine);
					mailUser.setDiagnoseCheck(dc);
					mailUser.setSendStatus((byte) 0);
					diagnoseMailUsersRepository.save(mailUser);

				}

				return "Updated Successfully";
			} else {
				return "Diagnose Magazine not found";
			}

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Transactional
	public String editUsers(int magid, DiagnoseMailMagazineDTO request) {
		try {

			DiagnoseMailMagazine dmEntity = diagnoseMailMagazineRepository.findById(magid).orElse(null);
			if(request.getMailType()!=0)
			{
			dmEntity.setMail_type((byte) request.getMailType());
			}
			if (request.getMagMailSubject() != null) {
				dmEntity.setMagMailSubject(request.getMagMailSubject());
			}

			dmEntity.setSentTime(request.getSentTime());
			dmEntity.setTitle(request.getTitle());
			dmEntity.setContent(request.getContent());
			dmEntity.setSendStatus((byte) 0);
			dmEntity.setModifiedOn(LocalDateTime.now());

			diagnoseMailMagazineRepository.save(dmEntity);

			diagnoseMailUsersRepository.updateSendStatusByMagazineId(0, magid);
			return "Updated Successfully";

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Transactional
	public String editEmails(int checkid, CheckSheetListDTO request) {
		try {

			DiagnoseCheck dcEntity = diagonsecheckRepository.findById(checkid).orElse(null);
			if (dcEntity != null) {
				dcEntity.setUserEmail(request.getUserEmail());
			}

			diagonsecheckRepository.save(dcEntity);

			return "Updated Successfully";

		} catch (Exception e) {
			return e.getMessage();
		}
	}

	@Transactional
	public String scheduledEmailSend() {
		try {
			List<Object[]> dmEntities = diagnoseMailMagazineRepository.findUnsentMagazines();

			for (Object[] objArray : dmEntities) {
				int dmId = (int) objArray[0];

				System.out.println(dmId);

				List<Object[]> usrEmails = diagnoseMailMagazineRepository.getUsersWithLimitAndOffset((long) dmId);
				List<Integer> checkIds = usrEmails.stream().map(row -> (int) row[1]) // sheet_id is in the second column
						.collect(Collectors.toList());

				// send email

				sendEmail(usrEmails, (String) objArray[3], (String) objArray[4], dmId);
				diagnoseMailMagazineRepository.updateDiagnoseMailMagUserStatus(dmId, checkIds);
				diagnoseMailMagazineRepository.updateDiagnoseCheckStatus(checkIds);
				updateDiagnoseCronJobRemarkStatus(dmId, "COMPLETED", "COMPLETED");
			}
			return "Email Send";
		} catch (Exception er) {
			return er.getMessage();
		}
	}

	@Transactional
	private void sendEmail(List<Object[]> usrEmails, String title, String content, int magId) {
		try {
			for (Object[] email : usrEmails) {
				this.emailService.sendDiagnoseEmail((String) email[3], title, content, true);

			}

			updateDiagnoseCronJobStatus(magId);
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Transactional
	private void updateDiagnoseCronJobStatus(int magId) {
		try {
			diagnoseMailMagazineRepository.updateDiagnoseMailMagStatus(magId);

		} catch (Exception er) {
			System.out.println(er.getMessage());
		}
	}

	@Transactional
	private void updateDiagnoseCronJobRemarkStatus(int magId, String remarks, String status) {
		try {
			diagnoseMailMagazineRepository.updateDiagnoseMailMagJobStatus(magId, remarks, status);
		} catch (Exception er) {
			System.out.println(er.getMessage());
		}
	}

	public ArchieveRecordResponse getArchiveRecordList(CheckSheetListDTO request) {
		PageRequest pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize());

		int financialYear;
		if (request.getFinYear() == 0) {
			LocalDate today = LocalDate.now();
			int currentYear = today.getYear();

			financialYear = today.getMonthValue() < 4 ? currentYear - 1 : currentYear;
		} else {
			financialYear = request.getFinYear();
		}

		List<String> years = diagonsecheckRepository.findDistinctFinYearOrderByYear();
		List<DiagnoseCheck> dc_result = diagonsecheckRepository.findDiagnoseCheckByFinYear(financialYear, pageable)
				.getContent();

		ArchieveRecordResponse acr = new ArchieveRecordResponse();
		acr.setFinYear(years);
		acr.setDiagnoseCheck(dc_result);
		return acr;
	}

	public int getCountArchiveRecordList(CheckSheetListDTO request) {
		int financialYear;
		if (request.getFinYear() == 0) {
			LocalDate today = LocalDate.now();
			int currentYear = today.getYear();

			financialYear = today.getMonthValue() < 4 ? currentYear - 1 : currentYear;
		} else {
			financialYear = request.getFinYear();
		}

		return diagonsecheckRepository.countDiagnoseCheckByFinYear(financialYear);

	}

	/** Api to update is_archived to 1 */
	public String setarchived(DiagnoseEditUserDTO request) {
		try {
			LocalDateTime today = LocalDateTime.now();
			List idArray = request.getCheckIds();

			List<DiagnoseCheck> checksToArchive = diagonsecheckRepository.findAllById(idArray);
			for (DiagnoseCheck check : checksToArchive) {
				check.setIsArchived((byte) 1);
				check.setArchiveDate(today);
			}

			diagonsecheckRepository.saveAll(checksToArchive);
			return "Saved Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong" + e;
		}
	}
	//update is deleted to 1
	public String isDeleted(DiagnoseEditUserDTO request) {
		try {
			LocalDateTime today = LocalDateTime.now();
			List idArray = request.getCheckIds();

			List<DiagnoseCheck> checksToArchive = diagonsecheckRepository.findAllById(idArray);
			for (DiagnoseCheck check : checksToArchive) {
				check.setIsDeleted((byte) 1);
				check.setDeletedDate(today);
			}

			diagonsecheckRepository.saveAll(checksToArchive);
			return "Deleted Successfully";
		} catch (Exception e) {
			e.printStackTrace();
			return "Something went wrong" + e;
		}
	}

	public List<DiagnoseCheckDTO> reservationsYearlylist(CheckSheetListDTO request) {
		List<DiagnoseCheck> diagnoseChecks = getDiagnoseChecksForOneYear(request.getFinYear());

		List<DiagnoseCheckDTO> result = new ArrayList<>();

		for (DiagnoseCheck diagnoseCheck : diagnoseChecks) {
			DiagnoseCheckDTO dto = new DiagnoseCheckDTO();
			dto.setId(diagnoseCheck.getId());
			dto.setDate(formatDate(diagnoseCheck.getReceivedDate()));
			dto.setUsername(diagnoseCheck.getUserName());
			dto.setUserEmail(diagnoseCheck.getUserEmail());
			dto.setMailSendDate(
					formatMailSendDate(diagnoseCheck.getMailSendDate(), (byte) diagnoseCheck.getMailSendStatus()));
			dto.setTitle(getChecksheetTitle(diagnoseCheck.getCheckSheetType().getId()));
			dto.setDeviceinfo(stripTags(diagnoseCheck.getDeviceInfo()));
			dto.setDiagnosisResult(stripTags(diagnoseCheck.getDiagonoseResult()));
			dto.setChecksheetContent(stripTags(diagnoseCheck.getCheckSheetContent()));

			result.add(dto);
		}

		return result;
	}

	private List<DiagnoseCheck> getDiagnoseChecksForOneYear(int finYear) {
		int financialYear;
		if (finYear == 0) {
			LocalDate today = LocalDate.now();
			int currentYear = today.getYear();

			financialYear = today.getMonthValue() < 4 ? currentYear - 1 : currentYear;
		} else {
			financialYear = finYear;
		}

		List<DiagnoseCheck> result = diagonsecheckRepository.findByFinYear(financialYear);

		return result;
	}

	private String formatDate(LocalDateTime dateTime) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy.MM.dd");
		return dateTime.format(formatter);
	}

	private String formatMailSendDate(LocalDateTime mailSendDate, byte mailSendStatus) {
		if (mailSendStatus == 1) {
			return formatDate(mailSendDate);
		} else {
			return " ";
		}
	}

	private String getChecksheetTitle(Integer checksheetTypeId) {
		ChecksheetType checksheetType = checkSheetTypeRepository.findById(checksheetTypeId)
				.orElse(new ChecksheetType());
		return checksheetType.getTitle();
	}

	private String stripTags(String text) {
		return Jsoup.parse(text).text();

	}

	
	public ChecksheetTemplate createTemplate(ChecksheetTemplate request) {
		System.out.print(request.getChecksheetId());
		ChecksheetType ctEntity=checkSheetTypeRepository.findById(request.getChecksheetId()).orElse(null);
		if(ctEntity!=null) {
			request.setChecksheetType(ctEntity);
		}
		request.setCreatedOn(LocalDateTime.now());
		request.setModifiedOn(LocalDateTime.now());
		request.setTemplateContent(stripTags(request.getTemplateContent()));
		return checksheetTemplateRepository.save(request);
	}

	public List<DiagnoseCheck> showDeletedMail(CheckSheetListDTO request) {
		try {
			PageRequest pageable = PageRequest.of(request.getPageNumber() - 1, request.getPageSize());

			List<DiagnoseCheck> dc=diagonsecheckRepository.findDeletedMail(pageable);
			
			return dc;
		} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong", e);
        }
	}
	public int countOfDeletedMail() {
		try {
		return	diagonsecheckRepository.findCountDeletedMail();	
		} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong", e);
        }
	}
	
    public String editCheckSheetTemplate(int editId,CheckSheetTemplateDTO request) {
    	
    	try {
    		ChecksheetTemplate ctEntity=checksheetTemplateRepository.findById(editId).orElse(null);
    		if(ctEntity!=null)
    		{
    			ctEntity.setTemplateContent(stripTags(request.getTemplateContent()));
    			ctEntity.setTemplateTitle(request.getTemplateTitle());
    			ctEntity.setTemplateType((byte)request.getTemplateType());
    			ctEntity.setModifiedOn(LocalDateTime.now());
    			checksheetTemplateRepository.save(ctEntity);
    			return "Updated";
    		}
    		else
    		{
    			return "Template ID not found";
    		}
    	
    	
    	}catch(Exception exp)
    	{
    		return exp.getMessage();
    	}
    }
   public String isdefaultCheckSheetTemplate(int editId) {
    	
    	try {
    		ChecksheetTemplate ctEntity=checksheetTemplateRepository.findById(editId).orElse(null);
    		if(ctEntity!=null)
    		{
    			ctEntity.setIsDefault((byte)1);
    			ctEntity.setModifiedOn(LocalDateTime.now());
    			checksheetTemplateRepository.save(ctEntity);
    			return "Updated";
    		}
    		else
    		{
    			return "Template ID not found";
    		}
    	
    	
    	}catch(Exception exp)
    	{
    		return exp.getMessage();
    	}
    }
   public String deleteCheckSheetTemplate(int sheetId) {
   	
   	try {
   		ChecksheetTemplate ctEntity=checksheetTemplateRepository.findById(sheetId).orElse(null);
   		if(ctEntity!=null)
   		{
   			ctEntity.setIsDeleted((byte)1);
   			ctEntity.setModifiedOn(LocalDateTime.now());
   			checksheetTemplateRepository.save(ctEntity);
   			return "Deleted";
   		}
   		else
   		{
   			return "Template ID not found";
   		}
   	
   	
   	}catch(Exception exp)
   	{
   		return exp.getMessage();
   	}
   }



public Integer checkExistingTemplateOrNot(ChecksheetTemplate request) {	
	ChecksheetType ctEntity=checkSheetTypeRepository.findById(request.getChecksheetId()).orElse(null);
	if(ctEntity!=null) {
		request.setChecksheetType(ctEntity);
	}
	Integer cnt = performTemplateCheck(request.getChecksheetId(),String.valueOf(request.getTemplateType()), request.getTemplateTitle());

	return cnt;
}

public int performTemplateCheck(Integer checksheetType, String pcSpType, String title) {
	ChecksheetType ctEntity=checkSheetTypeRepository.findById(checksheetType).orElse(null);
	
    List<ChecksheetTemplate> templates = checksheetTemplateRepository.findTemplatesByType(ctEntity.getId(), pcSpType);
    
    int cnt = 0;
    for (ChecksheetTemplate template : templates) {
    	System.out.print(templates);
        if (title.trim().equals(template.getTemplateTitle().trim())) {
            cnt++;
        }
    }
    
    return cnt;
}

	@Transactional
	public String setRestoreEmail(DiagnoseEditUserDTO request) {
		try {
			int updatedRows = diagonsecheckRepository.updateIsDeletedForIds(request.getCheckIds());
			if(updatedRows>0)
			return "Updated";
			else
			return "Error";
		} catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Something went wrong", e);
        }
	}
}
