package com.mentalhealthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.JobformDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.service.JobformService;

@RestController
@RequestMapping("/api/jobform")
public class JobformController {
	private final JobformService jobformService;
	
	public JobformController(JobformService jobformService) {
		this.jobformService = jobformService;
	}
	
	/**
	 * Save job form data
	 * @param request
	 * @return
	 */
	@PostMapping("/add")
	@PreAuthorize("hasAuthority('USER')")
	public ResponseEntity<ApiResponse<String>> saveJobform(@RequestBody JobformDTO request) {
		try {
			String result = jobformService.saveJobform(request);

			ApiResponse<String> response = new ApiResponse<String>(1, "Success", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<String>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}

}
