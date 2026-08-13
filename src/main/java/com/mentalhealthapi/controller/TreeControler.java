package com.mentalhealthapi.controller;

import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.mentalhealthapi.dto.DrawNodeRequestDTO;
import com.mentalhealthapi.dto.MemberDTO;
import com.mentalhealthapi.dto.MemberRemarkDTO;
import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.RemarkDTO;
import com.mentalhealthapi.dto.RemarkResponseDTO;
import com.mentalhealthapi.dto.RemarkWithMemberDetailsDTO;
import com.mentalhealthapi.dto.TreeDTO;
import com.mentalhealthapi.dto.TreeListDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.service.TreeService;

@RestController
@RequestMapping("/api/reply")
public class TreeControler {
	private final TreeService treeService;
	
	public TreeControler(TreeService treeService) {
		this.treeService =treeService;
	}
	/**
	 * Api to add reply check ng word, autoapproval 
	 * @param request
	 * @return
	 */
	
	 @PostMapping("/reply")
	 @PreAuthorize("hasAuthority('USER')")
	    public ResponseEntity<ApiResponse<Remarks>> addReply(@RequestBody RemarkDTO request) {  
	    	try {
	    		
	    		Remarks result= treeService.saveReply(request);
	        ApiResponse<Remarks> response = new ApiResponse<>(1, null, result);
	        return ResponseEntity.ok(response);
	    	}catch (IllegalArgumentException e) {
	            ApiResponse<Remarks> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	
	 /**
		 * This method generates a tree view of remarks based on the provided request type.
		 * @param request
		 * @return
		 */
		@PostMapping("/tree/list")
	    @PreAuthorize("hasAuthority('USER')")
		public ResponseEntity<ApiListResponse<List<TreeListDTO>>> treeList(@RequestBody MemberRemarkDTO request) {
		    try {
		        List<TreeListDTO> result = treeService.treeList(request,request.getPage(),request.getSize());
		        Long count= treeService.getCountOfFilteredMembers(request);
		        ApiListResponse<List<TreeListDTO>> response = new ApiListResponse<>(1, null, result,count);
		        return ResponseEntity.ok(response);
		    } catch (IllegalArgumentException e) {
		    	ApiListResponse<List<TreeListDTO>> response = new ApiListResponse<>(0, e.getMessage(), null,0);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		
		
		
	 

	 /**
		 * This method generates a tree view of remarks based on the provided request type.
		 * @param request
		 * @return
		 */
		@PostMapping("/treeView")
	    @PreAuthorize("hasAuthority('USER')")
		public ResponseEntity<ApiResponse<List<TreeDTO>>> treeView(@RequestBody MemberRemarkDTO request) {
		    try {
		        List<TreeDTO> result = treeService.treeView(request);
		        ApiResponse<List<TreeDTO>> response = new ApiResponse<>(1, null, result);
		        return ResponseEntity.ok(response);
		    } catch (IllegalArgumentException e) {
		        ApiResponse<List<TreeDTO>> response = new ApiResponse<>(0, e.getMessage(), null);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		
		/**
		 * Remark add api for user
		 * @param request
		 * @return
		 */
		 @PostMapping("/remarks")
		 @PreAuthorize("hasAuthority('USER')")
		    public ResponseEntity<ApiResponse<Remarks>> addRemarks(@RequestBody RemarkDTO request) {  
		    	try {
		    		
		    		Remarks result= treeService.addRemarks(request);
		        ApiResponse<Remarks> response = new ApiResponse<>(1, null, result);
		        return ResponseEntity.ok(response);
		    	}catch (IllegalArgumentException e) {
		            ApiResponse<Remarks> response = new ApiResponse<>(0, e.getMessage(), null);
		            return ResponseEntity.badRequest().body(response);
		        }
		    }
		 
		 
		 
		 
		 
		 @GetMapping("/children/{parentId}")
		    public ResponseEntity<ApiResponse<List<RemarkResponseDTO>>> getChildRemarks(@PathVariable Integer parentId) {
		        try {
		            List<RemarkResponseDTO> result = treeService.getAllChildRemarks(parentId);
		            ApiResponse<List<RemarkResponseDTO>> response = new ApiResponse<>(1, null, result);
		            return ResponseEntity.ok(response);
		        } catch (IllegalArgumentException e) {
		            ApiResponse<List<RemarkResponseDTO>> response = new ApiResponse<>(0, e.getMessage(), null);
		            return ResponseEntity.badRequest().body(response);
		        }
		    }
		 
		 
//		 @GetMapping("/children/{parentId}")
//		    public ResponseEntity<ApiResponse<List<Remarks>>> getChildRemarks(@PathVariable Integer parentId) {
//			 try {
//				 List<Remarks> result = treeService.getAllChildRemarks(parentId);
//				 ApiResponse<List<Remarks>> response = new ApiResponse<>(1, null, result);
//			        return ResponseEntity.ok(response); 
//			 }catch(IllegalArgumentException e) {
//				 ApiResponse<List<Remarks>> response = new ApiResponse<>(0, e.getMessage(), null);
//			        return ResponseEntity.badRequest().body(response);
//			 }
//		    }
		

}
