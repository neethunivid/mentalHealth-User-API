package com.mentalhealthapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.CheckSheetReportDTO;
import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.service.CheckSheetMailService;
import com.mentalhealthapi.service.CheckSheetReportService;

@RestController
@RequestMapping("/api/checksheetReport")
public class CheckSheetReportController {
	private final CheckSheetReportService checkSheetReportservice ;
	
	public CheckSheetReportController(CheckSheetReportService checkSheetReportservice) {
		this.checkSheetReportservice = checkSheetReportservice;
	}
	
	/*
	 * Api for Daily report
	 * **/
	
	@PostMapping("/daily")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<Object>>> dailyReport(@RequestBody CheckSheetReportDTO request) {    	
  	  List<Object> result = checkSheetReportservice.dailyReport(request);    	 
  	  ApiResponse<List<Object>> response = new ApiResponse<>(1, null, result);	        
  	  return ResponseEntity.ok(response);
	}
	
	/*
	 * Api for Monthly report*/
	
	@PostMapping("/monthly")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<Map<String, Object>>>> monthlyReport(@RequestBody CheckSheetReportDTO request) {    	
	  List<Map<String, Object>>  result = checkSheetReportservice.generateMonthlyReport(request.getYear());    
	 
  	  ApiResponse<List<Map<String, Object>>> response = new ApiResponse<>(1, null, result);	        
  	  return ResponseEntity.ok(response);
	    }
	
	/*
	 * Api for daily report by checksheet*/
	@PostMapping("/dailyChecksheet")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<Object>>> dailyChecksheetReport(@RequestBody CheckSheetReportDTO request) {    	
  	  List<Object> result = checkSheetReportservice.dailyChecksheetReport(request);    	 
  	  ApiResponse<List<Object>> response = new ApiResponse<>(1, null, result);	        
  	  return ResponseEntity.ok(response);
	    }
	
	/*Api for monthly check sheet*/
	@PostMapping("/monthlyChecksheet")
	@PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<Map<String, Object>> >> monthlyChecksheetReport(@RequestBody CheckSheetReportDTO request) {    	
		List<Map<String, Object>>  result = checkSheetReportservice.monthlyChecksheetReport(request);    	 
  	  ApiResponse<List<Map<String, Object>> > response = new ApiResponse<>(1, null, result);	        
  	  return ResponseEntity.ok(response);
	    }
	

}
