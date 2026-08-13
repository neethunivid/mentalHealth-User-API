package com.mentalhealthapi.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberDataDownloadDTO;
import com.mentalhealthapi.dto.MemberRemarkDTO;
import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.MemberResponse;
import com.mentalhealthapi.dto.RemarkSearchDTO;
import com.mentalhealthapi.dto.RemarkSearchResponse;
import com.mentalhealthapi.dto.TreeDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.Config;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.NgWords;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.service.MemberManagementService;


@RestController
@RequestMapping("/api")
public class MemberManagementController {
	 private MemberManagementService memberManagementService;
	    @Autowired
	    private JavaMailSender mailSender;
	    
	public MemberManagementController(MemberManagementService memberManagementService) {
		this.memberManagementService =memberManagementService;
	}
	/**
	 *  This API method is used to retrieve counts of remarks or reviews 
	 *  in various categories based on their status.
	 * @return set 
	 */
	@GetMapping("/pendingReviewCount")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Integer>>> findMemberById() {
        try {
            Map<String, Integer> result = memberManagementService.getPendingReviewCount();
            ApiResponse<Map<String, Integer>> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Map<String, Integer>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	/**
	 *  This API method is used to view all remarks for a given member
	 *  based on the provided request.
	 * @param request
	 * @return
	 */
	@PostMapping("/viewRemarks")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<MemberResponse>>> viewAllRemarks(@RequestBody MemberRemarkDTO request) {
	    try {
	        List<MemberResponse> result = memberManagementService.viewAllRemarks(request);
	        ApiResponse<List<MemberResponse>> response = new ApiResponse<>(1, null, result);
	        return ResponseEntity.ok(response);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<List<MemberResponse>> response = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	/**
	 * Search remark by id or title or member
	 * @param request
	 * @return a list of matching remarks
	 */
	@PostMapping("/searchRemarks")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<RemarkSearchResponse>>> searchRemarks(@RequestBody RemarkSearchDTO request) {
        try {
            ApiListResponse<List<RemarkSearchResponse>> response = memberManagementService.searchRemarks(request);
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            ApiListResponse<List<RemarkSearchResponse>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
            return ResponseEntity.badRequest().body(response);
        }
    }
	/**
	 * This method updates an existing remark and returns the updated remark.
	 * @param id
	 * @param remarks
	 * @return updated remark details
	 */
	@PutMapping("/updateRemark/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Remarks>> updateRemarks(@PathVariable Integer id, @RequestBody Remarks remarks) {  
    	try {
        Remarks result= memberManagementService.updateRemarksView(id,remarks);
        ApiResponse<Remarks> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Remarks> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	
	/**
	 * This method updates an existing remark with new values
	 * based on the provided Remarks object.
	 * @param id
	 * @param remarks
	 * @return
	 */
	@PutMapping("/updateRemarkById/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Remarks>> updateRemarkById(@PathVariable Integer id, @RequestBody Remarks remarks) {  
    	try {
        Remarks result= memberManagementService.updateRemarks(id,remarks);
        ApiResponse<Remarks> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Remarks> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	/**
	 * delete remark
	 * @param id
	 * @return string
	 */
	@DeleteMapping("/deleteRemark/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> deleteRemark(@PathVariable("id") Integer id) {  
		 try {
	            String result = memberManagementService.deleteRemarks(id);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	/**
	 * add NG word to NgWords
	 * @param ngwords
	 * @return
	 */
	@PostMapping("/ngword")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<NgWords>> addNgwords( @RequestBody NgWords ngwords) {  
    	try {
    		NgWords result= memberManagementService.addNgwords(ngwords);
        ApiResponse<NgWords> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<NgWords> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	/**
	 * Delete NgWords 
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deleteNgwords/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> deleteNgwords(@PathVariable("id") Integer id) {  
		 try {
	            String result = memberManagementService.deleteNgwords(id);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	/**
	 * list all NgWords
	 * @return
	 */
	@PostMapping("/listNgWords")
    @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<ApiResponse<List<NgWords>>> searchMembers() {    	
    	  List<NgWords> ngWords = memberManagementService.listNgWords();    	 
    	  ApiResponse<List<NgWords>> response = new ApiResponse<>(1, null, ngWords);	        
    	  return ResponseEntity.ok(response);
	    }
	/**
	 * update auto approval
	 * updates a configuration value and returns a message indicating the update was successful.
	 * @param config
	 * @return
	 */
	@PutMapping("/updateConfig")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> updateAutoApproval(@RequestBody Config config) {  
    	try {
        String result= memberManagementService.updateAutoApproval(config);
        ApiResponse<String> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	/**
	 * list configuration details
	 * @param config
	 * @return
	 */
	@PostMapping("/listConfig")
    @PreAuthorize("hasAuthority('ADMIN')")
	 public ResponseEntity<ApiResponse<List<Config>>> listConfig(@RequestBody Config config) {    	
    	  List<Config> configList = memberManagementService.listConfig(config);    	 
    	  ApiResponse<List<Config>> response = new ApiResponse<>(1, null, configList);	        
    	  return ResponseEntity.ok(response);
	    }
	
	/**
	 * This method generates a tree view of remarks based on the provided request type.
	 * @param request
	 * @return
	 */
	@PostMapping("/treeView")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<List<TreeDTO>>> treeView(@RequestBody MemberRemarkDTO request) {
	    try {
	        List<TreeDTO> result = memberManagementService.treeView(request);
	        ApiResponse<List<TreeDTO>> response = new ApiResponse<>(1, null, result);
	        return ResponseEntity.ok(response);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<List<TreeDTO>> response = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
    

}
