package com.mentalhealthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.DiagnoseDataDTO;
import com.mentalhealthapi.dto.TourJobRequestDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.TourJobService;

@RestController
@RequestMapping("/api/tourjob")
public class TourJobController {
	
	public TourJobService tourJobService;
	public TourJobController(TourJobService tourJobService) {
		this.tourJobService=tourJobService;
	}
	/**
	 * Sending email 
	 * @param request
	 * @return
	 */
	@PostMapping("/email")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse<String>> sendTourJobEmail(@RequestBody TourJobRequestDTO request) {
		String result = tourJobService.sendJobEmail(request);
	
		ApiResponse<String> response = new ApiResponse<>(1, null, result);
		return ResponseEntity.ok(response);
	}
}
