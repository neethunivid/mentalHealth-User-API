package com.mentalhealthapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.AuthRequest;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.service.LoginService;

@RequestMapping("/login")
@RestController
public class LoginController {
	@Autowired
	private LoginService loginService;

	/**
	 * login by username and password
	 * @param authRequest
	 * @return token
	 */
	@PostMapping("/authenticate")
	public ResponseEntity<ApiResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {

		return loginService.authenticationService(authRequest.getUsername(), authRequest.getPassword(),
				authRequest.getRole());
	}
/**
 * Update config table 
 * @param authRequest
 * @return
 */
	@PostMapping("/updateadminconfig")
	public ResponseEntity<ApiResponse> updateAdminDetails(@RequestBody AuthRequest authRequest) {
		try {
			String result= loginService.updateAdminDetails(authRequest.getUsername(), authRequest.getPassword(),authRequest.getSex());
			 ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
		} catch (IllegalArgumentException e) {
			ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
}
