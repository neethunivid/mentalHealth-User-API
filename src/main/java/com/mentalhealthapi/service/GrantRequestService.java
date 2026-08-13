package com.mentalhealthapi.service;

import java.util.List;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.Date;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.GrantRequestDTO;
import com.mentalhealthapi.dto.GrantRequestEditDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.GrantRequest;
import com.mentalhealthapi.model.GrantRequest2;
import com.mentalhealthapi.model.Uploads;
import com.mentalhealthapi.repository.GrantRequest2Repository;
import com.mentalhealthapi.repository.GrantRequestRepository;

import jakarta.mail.MessagingException;
import jakarta.transaction.Transactional;

@Service
public class GrantRequestService {
	private final GrantRequestRepository grantRequestRepository;
	private final GrantRequest2Repository grantRequest2Repository;
	 private final ResourceLoader resourceLoader;
	@Autowired

	private JavaMailSender mailSender;
	private final EmailService emailService;
	private final FileUploadService uploadService;

	public GrantRequestService(GrantRequestRepository grantRequestRepository,
			GrantRequest2Repository grantRequest2Repository,ResourceLoader resourceLoader,
			EmailService emailservice,FileUploadService uploadService) {
		this.grantRequestRepository = grantRequestRepository;
		this.grantRequest2Repository = grantRequest2Repository;
		this.emailService = emailservice;
		this.uploadService=uploadService;
		this.resourceLoader=resourceLoader;
	}

	public List<GrantRequest> getAllGrantRequests() {
		System.out.println("list");
		return grantRequestRepository.findAll();
	}

	public List<GrantRequest> getYearWiseGrantRequests() {
		LocalDate currentDate = LocalDate.now();
		int currentYear = currentDate.getYear();
		LocalDate startOfYear = LocalDate.of(currentYear, 1, 1);

		List<GrantRequest> grantRequests = grantRequestRepository.findByDateCreatedGreaterThanEqual(startOfYear);
		return grantRequests;
	}

	public List<GrantRequest> getPreviousYearGrantRequests() {

	    LocalDate currentDate = LocalDate.now();
        int currentYear = currentDate.getYear();
        LocalDate startOfYear = LocalDate.of(currentYear, 4, 1);

        List<GrantRequest> grantRequests = grantRequestRepository.findByDateCreatedLessThanEqual(startOfYear);
        return grantRequests;
	}

	public List<GrantRequest> getYearBetweenGrantRequests(int year) {
		LocalDate startYear = LocalDate.of(year, 4, 1);
		LocalDate endYear = LocalDate.of(year + 1, 3, 31);
		return grantRequestRepository.findByDateCreatedBetween(startYear, endYear);

	}

	public GrantRequest updateGrantRequest(int id, GrantRequestEditDTO request) {

		GrantRequest grantRequest = grantRequestRepository.findById((long) id).orElse(null);
		if (grantRequest != null) {
			// Update fields if the corresponding value is not null in the request DTO
			grantRequest.setRemark(request.getRemark());
			grantRequest.setDateCreated(request.getDateCreated());
			grantRequest.setNewCont(request.getNewCont());
			grantRequest.setClassification(request.getClassification());
			grantRequest.setInstitution(request.getInstitution());
			grantRequest.setDept(request.getDept());
			grantRequest.setAge(request.getAge());
			grantRequest.setRepresentative(request.getRepresentative());
			grantRequest.setRecomdPernInst(request.getRecomdPernInst());
			grantRequest.setRecomdPernJob(request.getRecomdPernJob());
			grantRequest.setRecomdPernFullname(request.getRecomdPernFullname());
			grantRequest.setResearchName(request.getResearchName());

			// Save the updated grantRequest object
			return grantRequestRepository.save(grantRequest);
		}
		return null;
	}

	public GrantRequest getGrantRequestById(int id) {
		return grantRequestRepository.findById((long) id).orElse(null);

	}

	@Transactional
	public String deleteGrantRequest(List<Long> grantIds) {
		for (Long grantId : grantIds) {
            grantRequest2Repository.deleteByGrantId(grantId);
            grantRequestRepository.deleteById(grantId);
        }
	    
	     return "Grant Request deleted successfully";
	}

	public List<Object[]> getCombinedDataByGrantIds(List<Long> grantIds) {
		return grantRequestRepository.findCombinedDataByGrantIds(grantIds);
	}
	public List<GrantRequestDTO> combineGrandValues( List<Object[]> combinedData) {
	    List<GrantRequestDTO> grantRequestDTOList = new ArrayList<>();

	    for (Object[] data : combinedData) {
	    	GrantRequestDTO grantRequestDTO = new GrantRequestDTO();
	    	grantRequestDTO.setId((Integer) data[0]);
	    	grantRequestDTO.setLocation1((String) data[1]);
	    	grantRequestDTO.setPrefectures1((String) data[2]);
	    	grantRequestDTO.setMunicipality1((String) data[3]);
	    	grantRequestDTO.setAddress1((String) data[4]);
	    	grantRequestDTO.setBuilding1((String) data[5]);
	    	grantRequestDTO.setInstitution((String) data[6]);
	    	grantRequestDTO.setDept((String) data[7]);
	    	grantRequestDTO.setFullname((String) data[8]);
	    	grantRequestDTO.setFurigana((String) data[9]);
	    	grantRequestDTO.setDob((String) data[10]);
	    	grantRequestDTO.setAge((String) data[11]);
	    	grantRequestDTO.setHome((String) data[12]);
	    	grantRequestDTO.setPrefectures2((String) data[13]);
	    	grantRequestDTO.setMunicipality2((String) data[14]);
	    	grantRequestDTO.setAddress2((String) data[15]);
	    	grantRequestDTO.setBuilding2((String) data[16]);
	    	grantRequestDTO.setPhone((String) data[17]);
	    	grantRequestDTO.setEmail2((String) data[18]);
	    	grantRequestDTO.setRepresentative((String) data[19]);
	    	grantRequestDTO.setRepLocation((String) data[20]);
	    	grantRequestDTO.setRepPrefectures((String) data[21]);
	    	grantRequestDTO.setRepMunicipality((String) data[22]);
	    	grantRequestDTO.setRepAddress((String) data[23]);
	    	grantRequestDTO.setRepBuilding((String) data[24]);
	    	grantRequestDTO.setRepPhone((String) data[25]);
	    	grantRequestDTO.setRepEmail((String) data[26]);
	    	grantRequestDTO.setInCharge((String) data[27]);
	    	grantRequestDTO.setRecomdPernInst((String) data[28]);
	    	grantRequestDTO.setRecomdPernJob((String) data[29]);
	    	grantRequestDTO.setRecomdPernFullname((String) data[30]);
	    	grantRequestDTO.setRecomdPernLocation((String) data[31]);
	    	grantRequestDTO.setRecomdPernPrefectures4((String) data[32]);
	    	grantRequestDTO.setRecomdPernMunicipality((String) data[33]);
	    	grantRequestDTO.setRecomdPernAddress((String) data[34]);
	    	grantRequestDTO.setRecomdPernBuilding((String) data[35]);
	    	grantRequestDTO.setRecomdPernPhone((String) data[36]);
	    	grantRequestDTO.setFile1((String) data[37]);
	    	grantRequestDTO.setBiography((String) data[38]);
	    	grantRequestDTO.setApplication((String) data[39]);
	    	grantRequestDTO.setClassification((String) data[40]);
	    	grantRequestDTO.setNewCont((String) data[41]);
	    	grantRequestDTO.setResearchName((String) data[42]);
	    	grantRequestDTO.setExpEquipmentCosts((String) data[43]);
	    	grantRequestDTO.setExpConsumablesCosts((String) data[44]);
	    	grantRequestDTO.setExpTravelExpenses((String) data[45]);
	    	grantRequestDTO.setExpRewardMoney((String) data[46]);
	    	grantRequestDTO.setExpOther((String) data[47]);
	    	grantRequestDTO.setExpTotal((String) data[48]);
	    	grantRequestDTO.setSubEquipmentCosts((String) data[49]);
	    	grantRequestDTO.setSubConsumablesCosts((String) data[50]);
	    	grantRequestDTO.setSubTravelExpenses((String) data[51]);
	    	grantRequestDTO.setSubRewardMoney((String) data[52]);
	    	grantRequestDTO.setSubOther((String) data[53]);
	    	grantRequestDTO.setSubTotal((String) data[54]);
	    	grantRequestDTO.setFile2((String) data[55]);
	    	java.sql.Date sqlDate =(Date) data[56];
    	    LocalDate localDate = sqlDate.toLocalDate();
   	        grantRequestDTO.setDateCreated(localDate);
	    	//grantRequestDTO.setDateCreated((LocalDate) data[56]);
	    	grantRequestDTO.setCountry1((String) data[57]);
	    	grantRequestDTO.setCountry2((String) data[58]);
	    	grantRequestDTO.setCountry3((String) data[59]);
	    	grantRequestDTO.setCountry4((String) data[60]);
	    	
	    	grantRequestDTO.setDetails((String) data[61]);
	    	grantRequestDTO.setPurpose((String) data[62]);
	    	grantRequestDTO.setTarget((String) data[63]);
	    	grantRequestDTO.setContents((String) data[64]);
	    	grantRequestDTO.setExpectedResults((String) data[65]);
	    	grantRequestDTO.setApproval((String) data[66]);
	    	grantRequestDTO.setResearchSubjects((String) data[67]);
	    	grantRequestDTO.setKeywords((String) data[68]);
	    	grantRequestDTO.setOriginality((String) data[69]);
	    	grantRequestDTO.setAchievements((String) data[70]);
	    	grantRequestDTO.setOrgPrincipal((String) data[71]);
	    	grantRequestDTO.setAffiliation((String) data[72]);
	    	grantRequestDTO.setAge0((String) data[73]);
	    	grantRequestDTO.setRole0((String) data[74]);
	    	grantRequestDTO.setCoordinator1((String) data[75]);
	    	grantRequestDTO.setAffiliation1((String) data[76]);
	    	grantRequestDTO.setAge1((String) data[77]);
	    	grantRequestDTO.setRole1((String) data[78]);
	    	grantRequestDTO.setCoordinator2((String) data[79]);
	    	grantRequestDTO.setAffiliation2((String) data[80]);
	    	grantRequestDTO.setAge2((String) data[81]);
	    	grantRequestDTO.setRole2((String) data[82]);
	    	grantRequestDTO.setCoordinator3((String) data[83]);
	    	grantRequestDTO.setAffiliation3((String) data[84]);
	    	grantRequestDTO.setAge3((String) data[85]);
	    	grantRequestDTO.setRole3((String) data[86]);
	    	grantRequestDTO.setCoordinator4((String) data[87]);
	    	grantRequestDTO.setAffiliation4((String) data[88]);
	    	grantRequestDTO.setAge4((String) data[89]);
	    	grantRequestDTO.setRole4((String) data[90]);
	    	grantRequestDTO.setCoordinator5((String) data[91]);
	    	grantRequestDTO.setAffiliation5((String) data[92]);
	    	grantRequestDTO.setAge5((String) data[93]);
	    	grantRequestDTO.setRole5((String) data[94]);
	    	grantRequestDTO.setResearchContents((String) data[95]);   	
	    	grantRequestDTO.setRemark((String) data[96]);

	    	grantRequestDTOList.add(grantRequestDTO);
	    }
	    return grantRequestDTOList;
	}

	public String saveGrantRequest(GrantRequest grantRequest, GrantRequest2 grantRequest2)  {
		try {
		GrantRequest savedGrantRequest = grantRequestRepository.save(grantRequest);
		grantRequest2.setGrantId(savedGrantRequest.getId());
		grantRequest2Repository.save(grantRequest2);
		// send mail to user and admin
		// $mail->AddCC('mentalzaidan@mental-health.org', 'cc account');
		// $mail->AddBCC('mnewton@ops.net', 'bcc account');
		// $mail->AddBCC('e-okamoto@mental-health.org', 'bcc account');
		// $mail->AddBCC('mental-hlt.nishida@outlook.jp', 'bcc account');
		String[] to=null;
		
		if (grantRequest.getRepEmail().length()>0 && grantRequest.getEmail2().length()>0 ) {
		    to = new String[]{grantRequest.getEmail2(), grantRequest.getRepEmail()};
		} else if (grantRequest.getEmail2().length() > 0) {
		    to = new String[]{grantRequest.getEmail2()};
		} else {
		    to = new String[]{grantRequest.getRepEmail()};
		}
		 synchronized (this) {
		String[] cc = new String[]{"neethu@nivid.co","remitha@nivid.co","bijila@nivid.co"};
		String subject = "研究活動助成申請の申請内容 （No." + savedGrantRequest.getId() + ")";

		String body = "この度は当財団の研究活動助成に公募いただきありがとうございます。\n\n" + "申請された内容は以下の通りです。\n\n" + "Form 1\n" + "1. 所属機関の所在地\n"
				+ grantRequest.getCountry1() + "\n" + grantRequest.getLocation1() + "\n"
				+ grantRequest.getPrefectures1() + "\n" + grantRequest.getMunicipality1() + "\n"
				+ grantRequest.getAddress1() + "\n" + grantRequest.getBuilding1() + "\n\n" + "2. 所属機関名・部局・職名\n"
				+ grantRequest.getInstitution() + "\n" + grantRequest.getDept() + "\n\n" + "3.A 個人研究代表者\n"
				+ grantRequest.getFullname() + "\n" + grantRequest.getFurigana() + "\n" + grantRequest.getDob() + "\n"
				+ grantRequest.getAge() + "\n" + grantRequest.getCountry2() + "\n" + grantRequest.getHome() + "\n"
				+ grantRequest.getPrefectures2() + "\n" + grantRequest.getMunicipality2() + "\n"
				+ grantRequest.getAddress2() + "\n" + grantRequest.getBuilding2() + "\n" + grantRequest.getPhone()
				+ "\n" + grantRequest.getEmail2() + "\n\n" + "3.B 団体活動\n" + grantRequest.getRepresentative() + "\n"
				+ grantRequest.getCountry3() + "\n" + grantRequest.getRepLocation() + "\n"
				+ grantRequest.getRepPrefectures() + "\n" + grantRequest.getRepMunicipality() + "\n"
				+ grantRequest.getRepAddress() + "\n" + grantRequest.getRepBuilding() + "\n"
				+ grantRequest.getRepPhone() + "\n" + grantRequest.getRepEmail() + "\n" + grantRequest.getInCharge()
				+ "\n\n" + "4. 推薦者\n" + grantRequest.getRecomdPernInst() + "\n" + grantRequest.getRecomdPernJob() + "\n"
				+ grantRequest.getRecomdPernFullname() + "\n" + grantRequest.getCountry4() + "\n"
				+ grantRequest.getRecomdPernLocation() + "\n" + grantRequest.getRecomdPernPrefectures4() + "\n"
				+ grantRequest.getRecomdPernMunicipality() + "\n" + grantRequest.getRecomdPernAddress() + "\n"
				+ grantRequest.getRecomdPernBuilding() + "\n" + grantRequest.getRecomdPernPhone() + "\n"
				+ "5. 研究のときは研究代表者略歴を、団体活動のときは活動歴を\r\n" + "    書いてください。\n" + grantRequest.getBiography() + "\n \n\n"
				+ "Form 2\n" + "6. 応募申請の区分\n" + grantRequest.getClassification() + "\n\n" + "7. 新規・継続の区分\n"
				+ grantRequest.getNewCont() + "\n\n" + "8. 研究名または活動名\n" + grantRequest.getResearchName() + "\n\n"
				+ "9. 本研究活動に要する今年度の経費（単位：円）\r\n" + "A.設備備品費 \n" + "￥" + grantRequest.getExpEquipmentCosts() + "\n"
				+ "B.消耗品費\n" + "￥" + grantRequest.getExpConsumablesCosts() + "\n" + "C.旅費\n" + "￥"
				+ grantRequest.getExpTravelExpenses() + "\n" + "D.謝礼金\n" + "￥" + grantRequest.getExpRewardMoney() + "\n"
				+ "E.その他\n" + "￥" + grantRequest.getExpOther() + "\n" + "F.合計(A+B+C+D+E）\n" + "￥"
				+ grantRequest.getExpTotal() + "\n\n"

				+ "10. 上記に関する助成申請額（単位：円）\r\n" + "A.設備備品費\n" + "￥" + grantRequest.getSubEquipmentCosts() + "\n"
				+ "B.消耗品費\n" + "￥" + grantRequest.getSubConsumablesCosts() + "\n" + "C.旅費\n" + "￥"
				+ grantRequest.getSubTravelExpenses() + "\n" + "D.謝礼金\n" + "￥" + grantRequest.getSubRewardMoney() + "\n"
				+ "E.その他\n" + "￥" + grantRequest.getSubOther() + "\n" + "F.合計(A+B+C+D+E）" + "￥"
				+ grantRequest.getSubTotal() + "\n\n" + "11. 上記に関する助成金申請額 の明細\n" + grantRequest2.getDetails() + "\n\n"
				+ "12-1. 研究または活動の内容\r\n" + "1.目的\n" + grantRequest2.getPurpose() + "\n" + "2.対象\n"
				+ grantRequest2.getTarget() + "\n" + "3.方法\n" + grantRequest2.getContents() + "\n" + "4.予想される成果\n"
				+ grantRequest2.getExpectedResults() + "\n\n" + "12-A（１）所属機関の倫理審査委員会 等の承認について\n"
				+ grantRequest2.getApproval() + "\n\n" + "12-B.（2）調査・研究対象個人\n"
				+ grantRequest2.getResearchSubjects() + "\n\n" + "12-2. キーワード\n" + grantRequest2.getKeywords()
				+ "\n\n" + "Form 3 \n"
               	+ "12-3. 本研究の独創性（具体的に）\n"
               	+ grantRequest2.getOriginality() + "\n"
               	+ "12-４. 関連領域における研究発表、実績（投稿論文や学会発表論文など）\n"
               	+ grantRequest2.getAchievements() + "\n"
               	+ "13. 研究組織 \r\n"
               	+ "代表研究者\n"
               	+ grantRequest2.getOrgPrincipal()+ "\n"
               	+ grantRequest2.getAffiliation()+ "\n"
               	+ grantRequest2.getAge0()+ "\n"
               	+ grantRequest2.getRole0()+ "\n\n"
               	+ "研究分担者（1）\n"
               	+ grantRequest2.getCoordinator1()+ "\n"
               	+ grantRequest2.getAffiliation1()+ "\n"
               	+ grantRequest2.getAge1()+ "\n"
               	+ grantRequest2.getRole1()+ "\n\n"
               	+"研究分担者（2）\n"
               	+ grantRequest2.getCoordinator2()+ "\n"
               	+ grantRequest2.getAffiliation2()+ "\n"
               	+ grantRequest2.getAge2()+ "\n"
               	+ grantRequest2.getRole2()+ "\n\n"
               	+ "研究分担者（3）\n"
               	+ grantRequest2.getCoordinator3()+ "\n"
               	+ grantRequest2.getAffiliation3()+ "\n"
               	+ grantRequest2.getAge3()+ "\n"
               	+ grantRequest2.getRole3()+ "\n\n"
               	+ "研究分担者（4）\n"
               	+ grantRequest2.getCoordinator4()+ "\n"
               	+ grantRequest2.getAffiliation4()+ "\n"
               	+ grantRequest2.getAge4()+ "\n"
               	+ grantRequest2.getRole4()+ "\n\n"
               	+ "研究分担者（5）\n"
               	+ grantRequest2.getCoordinator5()+ "\n"
               	+ grantRequest2.getAffiliation5()+ "\n"
               	+ grantRequest2.getAge5()+ "\n"
               	+ grantRequest2.getRole5()+ "\n\n"
               	+ "14.研究内容と実績\n"
               	+ grantRequest2.getResearchContents()+ "\n\n\n"
               	+ "＜送信先・お問合せ＞ \r\n"
               	+ "〒530-0057 \r\n"
               	+ "大阪市北区曾根崎2-5-10梅田パシフィックビル7F \r\n"
               	+ "(公財）メンタルヘルス岡本記念財団事務局 \r\n"
               	+ "HP:https://www.mental-health.org \r\n"
               	+ "Email:mentalzaidan@mental-health.org \r\n"
               	+ "TEL:06-6809-1211 FAX:06-6809-1233 \n \n\"";

		Uploads file2=null;
		Uploads file1=uploadService.getFileName(Long.parseLong(grantRequest.getFile1()));
		if(grantRequest.getFile2()!="")
		file2=uploadService.getFileName(Long.parseLong(grantRequest.getFile2()));
		
		//String folderPath = resourceLoader.getResource("classpath:/uploads/").getFile().getAbsolutePath();
		String folderPath = resourceLoader.getResource("file:src/main/resources/uploads/").getFile().getAbsolutePath();
	   
		List<String> attachmentFilePaths = new ArrayList<>();
		
		
		if (file1 != null && !file1.getFileName().isEmpty()) {
		    // File 1 has been uploaded
		    attachmentFilePaths.add(folderPath + "/" + file1.getFileName());
		}
		if (file2 != null && !file2.getFileName().isEmpty()) {
		    // File 2 has been uploaded
		    attachmentFilePaths.add(folderPath + "/" + file2.getFileName());
		}

		// attachmentFilePaths.add("C:/Users/bijil/Downloads/st4.jpeg");

		try {
			emailService.sendEmailWithAttachments(to, cc, subject, body, attachmentFilePaths);
			System.out.println("Email sent successfully.");
		} catch (Exception e) {
			System.out.println("Failed to send email: " + e.getMessage());
		}
		 }
		} catch (Exception e) {
			System.out.println("Failed to send email: " + e.getMessage());
		}
		return "Grant Request Saved Sussessfully";
		
	}

}
