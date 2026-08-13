package com.mentalhealthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.MemberDeleteDTO;
import com.mentalhealthapi.dto.SeminarRequestDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.MemberUserService;
import com.mentalhealthapi.service.SeminarService;

@RestController
@RequestMapping("/api/seminar")
public class SeminarController {
	
	private final SeminarService seminarService;
	public SeminarController(SeminarService seminarService){
		this.seminarService=seminarService;
	}
	
	@PostMapping("/email")
	//@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse<String>> sendSeminarEmail(@RequestBody SeminarRequestDTO request) {
		 //sending mail to user and admin
		String result = seminarService.sendSeminarApplication(request);
		
		ApiResponse<String> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}
	
}
