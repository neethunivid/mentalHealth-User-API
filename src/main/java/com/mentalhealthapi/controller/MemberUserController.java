package com.mentalhealthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.DiagnoseDataDTO;
import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberDeleteDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.MemberService;
import com.mentalhealthapi.service.MemberUserService;

@RestController
@RequestMapping("/api/memberuser")
public class MemberUserController {
	
	private final MemberUserService memberService;
	
	MemberUserController(MemberUserService memberService){
		this.memberService=memberService;
		
	}
	/**
	 * delete member (updating member status to 3)
	 * Insert data to memberdeleted table
	 * @param request
	 * @return string
	 */
	@PostMapping("/deletemember")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse<String>> deleteMember(@RequestBody MemberDeleteDTO request) {
		String result = memberService.deleteMember(request);
	
		ApiResponse<String> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}
	
}
