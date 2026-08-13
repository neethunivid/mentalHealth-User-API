package com.mentalhealthapi.controller;

import java.util.List;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.time.LocalDate;


import org.springframework.http.ResponseEntity;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.GrantRequestCreateDTO;
import com.mentalhealthapi.dto.GrantRequestDTO;
import com.mentalhealthapi.dto.GrantRequestEditDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.GrantRequest;
import com.mentalhealthapi.model.GrantRequest2;
import com.mentalhealthapi.service.GrantRequestService;

@RestController
@RequestMapping("/api/grant-requests")
public class GrantRequestController {
    private final GrantRequestService grantRequestService;

   
    public GrantRequestController(GrantRequestService grantRequestService) {
        this.grantRequestService = grantRequestService;
    }
    
    /**
     * Create api to log all form data to grant request table and send mail
     * @param request
     * @return
     */
    @PostMapping("/create")
    public ResponseEntity<ApiResponse<String>> createGrantRequest(@RequestBody GrantRequestCreateDTO request) {
    	String result=  grantRequestService.saveGrantRequest(request.getGrandRequest(), request.getGrantRequest2());
        ApiResponse<String> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
       
    } 
    
     
    /**
     * Api to view all grantRequest
     * @param grantIds
     * @return
     */
  @GetMapping("/view/{grantIds}")
  @PreAuthorize("hasAuthority('ADMIN')")     
  //@PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
  public ResponseEntity<ApiResponse<List<GrantRequestDTO>>> getCombinedData(@PathVariable("grantIds") List<Long> grantIds) {
	    List<Object[]> combinedData = grantRequestService.getCombinedDataByGrantIds(grantIds);
	    List<GrantRequestDTO> grantRequestDTOList= grantRequestService.combineGrandValues(combinedData);

	    ApiResponse<List<GrantRequestDTO>> response = new ApiResponse<>(1, null, grantRequestDTOList);
	    return ResponseEntity.ok(response);
	}

    /**
     * List api to list all data from grantrequest table
     * @return
     */
    @PostMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<GrantRequest>>> getAllGrantRequests() {
        List<GrantRequest> grantRequests = grantRequestService.getAllGrantRequests();
        ApiResponse<List<GrantRequest>> response = new ApiResponse<>(1, null, grantRequests);	
        return ResponseEntity.ok(response);
    }
    
    /**
     *edit api for admin to edit grant request details
     * @param id
     * @param request
     * @return
     */
    
    @PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<GrantRequest>> updateGrantRequest(@PathVariable int id,@RequestBody GrantRequestEditDTO request) {  
    	try {
        GrantRequest result= grantRequestService.updateGrantRequest(id,request);
        ApiResponse<GrantRequest> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<GrantRequest> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
   
    /**
     * GrantRequest delete api 
     * This api delete value in grantrequest and grandrequest2 table connected with grantId
     * @param grantId
     * @return
     */
    
    @DeleteMapping("/{grantIds}")
    @PreAuthorize("hasAuthority('ADMIN')")  
    public ResponseEntity<ApiResponse<String>> deleteGrantRequests(@PathVariable("grantIds") List<Long> grantIds) {
        try {
            String result = grantRequestService.deleteGrantRequest(grantIds);
            ApiResponse<String> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
   
    
    

	
}