package com.mentalhealthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.InquiryDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.InquiryService;


@RestController
@RequestMapping("/api/inquiry")
public class InquiryController {
	private final InquiryService inquiryService;
	public InquiryController(InquiryService inquiryService) {
		this.inquiryService = inquiryService;
	}
	/**
	 * Api to send inquiry mail to admin
	 * @param request
	 * @return
	 */
	
	@PostMapping("/add")	
	public ResponseEntity<ApiResponse<String>> saveLending(@RequestBody InquiryDTO request) {
		try {
			String result = inquiryService.addInquiry(request);

			ApiResponse<String> response = new ApiResponse<String>(1, "Success", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<String>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	

}
