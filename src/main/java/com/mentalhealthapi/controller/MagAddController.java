package com.mentalhealthapi.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.InquiryDTO;
import com.mentalhealthapi.dto.MagaddUserDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.service.MagAddService;

@RestController
@RequestMapping("/api/magadd")
public class MagAddController {
	private final MagAddService magAddService;
	
	public MagAddController(MagAddService magAddService) {
		this.magAddService = magAddService;
	}
	
	/**
	 * Mail mag user add api 
	 * @param request
	 * @return
	 */
	@PostMapping("/add")	
	public ResponseEntity<ApiResponse<MailmagazineUser>> add(@RequestBody MagaddUserDTO request) {
		try {
			MailmagazineUser result = magAddService.add(request);
			ApiResponse<MailmagazineUser> response = new ApiResponse<MailmagazineUser>(1, "Success", result);
			return ResponseEntity.ok(response);
		} catch (Exception e) {
			ApiResponse<MailmagazineUser> response = new ApiResponse<MailmagazineUser>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
	}
	
	/**
	 * check email already exist
	 * 
	 */
	@PostMapping("/checkEmail")
	public ResponseEntity<ApiResponse<String>> checkEmail(@RequestBody MagaddUserDTO request){
		try {
			String result = magAddService.checkEmail(request);
			ApiResponse<String> response = new ApiResponse<String>(1,"success",result);
			return ResponseEntity.ok(response);			
		} catch (Exception e) {
			ApiResponse<String> response = new ApiResponse<String>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	/**
	 * get magazine id by passing email id
	 */
	
	@PostMapping("/getMagid")	
	public ResponseEntity<ApiResponse<Integer>> checkMagid(@RequestBody MagaddUserDTO request){
		try {
			Integer result = magAddService.getMagId(request);
			ApiResponse<Integer> response = new ApiResponse<Integer>(1,"success",result);
			return ResponseEntity.ok(response);			
		} catch (Exception e) {
			ApiResponse<Integer> response = new ApiResponse<Integer>(0, e.getMessage(), null);
			return ResponseEntity.badRequest().body(response);
		}
		
	}
	/**
	 * Mail magazine delete for user
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
	public ResponseEntity<ApiResponse<String>> delete(@PathVariable("id") Integer id) {  
		 try {
	            String result = magAddService.delete(id);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }

}
