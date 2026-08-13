package com.mentalhealthapi.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.CheckSheetListDTO;
import com.mentalhealthapi.dto.DiagnoseDataDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.DiagnoseUserService;


@RestController
@RequestMapping("/api/diagnose")
public class DiagnoseUserController {
	
	DiagnoseUserService diagnoseUserService;
	DiagnoseUserController(DiagnoseUserService diagnoseUserService){
		this.diagnoseUserService=diagnoseUserService;
	}
	/**
	 * API for saving details to diagnose check and talktype
	 * DTO DiagnoseDataDTO
	 * return string
	 **/
	@PostMapping("/save")
//	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse<String>> listCheckSheetByType(@RequestBody DiagnoseDataDTO request) {
		String result = diagnoseUserService.saveDaignoseData(request);
	
		ApiResponse<String> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}
}
