package com.mentalhealthapi.service;

import java.time.*;
import java.util.List;

import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.DiagnoseDataDTO;
import com.mentalhealthapi.model.ChecksheetType;
import com.mentalhealthapi.model.DiagnoseCheck;
import com.mentalhealthapi.model.DiagnoseTalkType;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.CheckSheetTypeRepository;
import com.mentalhealthapi.repository.DiagnoseCheckRepository;
import com.mentalhealthapi.repository.DiagnoseTalkTypeRepository;

import jakarta.mail.MessagingException;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

@Service
public class DiagnoseUserService {
	@PersistenceContext
	private EntityManager entityManager;
	private final DiagnoseCheckRepository diagonsecheckRepository;
	private final CheckSheetTypeRepository checkSheetTypeRepository;
	private final DiagnoseTalkTypeRepository diagnoseTalkTypeRepository;
	private final EmailService emailService;
		DiagnoseUserService(DiagnoseCheckRepository diagonsecheckRepository,
				CheckSheetTypeRepository checkSheetTypeRepository,
				DiagnoseTalkTypeRepository diagnoseTalkTypeRepository,
				 EmailService emailService
				){
			this.emailService=emailService;
			this.diagonsecheckRepository=diagonsecheckRepository;
			this.diagnoseTalkTypeRepository=diagnoseTalkTypeRepository;
			this.checkSheetTypeRepository=checkSheetTypeRepository;
		}
	
	  public String saveDaignoseData(DiagnoseDataDTO request) {
		  ChecksheetType cType= checkSheetTypeRepository.findById(request.getType()).orElse(null);
		  
		  DiagnoseCheck dcObj=new DiagnoseCheck();
		  dcObj.setArchiveDate(null);
		  dcObj.setDeviceInfo(request.getDeviceData());
		  dcObj.setCheckSheetContent(request.getChecksheetContent());
		  dcObj.setUserEmail(request.getEmail());
		  dcObj.setUserName(request.getName());
		  dcObj.setDiagonoseResult(request.getDiagnoseResult());
		  dcObj.setCheckSheetType(cType);
		  dcObj.setReceivedDate(LocalDateTime.now());
		  dcObj.setIsArchived((byte)0);
		  dcObj.setIsDeleted(0);
		  dcObj.setIsSmartphone(request.isSmartphone() ? (byte)1 : (byte)0);
		  
	        int year = LocalDate.now().getYear();
	        if (LocalDate.now().getMonthValue() < 4) {
	            year--;
	        }
	        dcObj.setFinYear(year);
	      
	      
	      
		 DiagnoseCheck dc= diagonsecheckRepository.save(dcObj);
		 saveTalkType(request.getTalkType(),dc.getId());
		 emailService.sendDiagnoseUserEmail(request);
		  return "Saved";
	  }
	  
	  public String saveTalkType(List<Integer> talkTypes,int checkId) {
		  System.out.println("talk type  >>");
		  for(Integer types:talkTypes) {
			  DiagnoseTalkType dtObj=new DiagnoseTalkType(checkId, types);
		  diagnoseTalkTypeRepository.save(dtObj);
		  }
		  return "Saved";
	  }
}
