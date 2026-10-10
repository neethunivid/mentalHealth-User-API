package com.mentalhealthapi.controller;


import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
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
import com.mentalhealthapi.dto.MemberRequest;
import com.mentalhealthapi.dto.MemberWithdrawExportDTO;
//import com.mentalhealthapi.dto.WithDrawMemberDTO;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.model.GrantRequest;
import com.mentalhealthapi.model.Job;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.Prefecture;
import com.mentalhealthapi.service.MemberService;

import jakarta.servlet.http.HttpServletResponse;





@RestController
@RequestMapping("/api/members")
public class MemberController {
    private final MemberService memberService;
    @Autowired
    private JavaMailSender mailSender;
        
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }
    
    /**
     * member search api with filters no count
     * @param request
     * @return
     */
    @PostMapping("/memberSearch")
    @PreAuthorize("hasAnyAuthority('USER', 'ADMIN')")
	 public ResponseEntity<ApiResponse<List<Member>>> searchMembers(@RequestBody MemberDTO request) {
    	  List<Member> members = memberService.searchMember(request);    	 
    	  ApiResponse<List<Member>> response = new ApiResponse<>(1, null, members);	        
    	  return ResponseEntity.ok(response);
	    }
    
    

    @PostMapping("/prefList")
	 public ResponseEntity<ApiResponse<List<Prefecture>>> prefecturelist() {    	
    	  List<Prefecture> members = memberService.prefList();    	 
    	  ApiResponse<List<Prefecture>> response = new ApiResponse<>(1, null, members);	        
    	  return ResponseEntity.ok(response);
	    }
    //job list api
    
    @PostMapping("/job")
	 public ResponseEntity<ApiResponse<List<Job>>> joblist() {    	
    	  List<Job> list = memberService.jobList();    	 
    	  ApiResponse<List<Job>> response = new ApiResponse<>(1, null, list);	        
    	  return ResponseEntity.ok(response);
	    }
    
    /**
     * member search api with count and filter
     * @param request
     * @return
     */
    
    @PostMapping("/memberlistsearch")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiListResponse<List<Member>>> searchMember2(@RequestBody MemberDTO request) {    	
  	try {
    	ApiListResponse<List<Member>> members = memberService.searchMember2(request);
  	 
  	 // ApiListResponse<List<Member>> response = new ApiListResponse<>(1, null, members,count);	        
  	  return ResponseEntity.ok(members);
	    }
  	catch(Exception e) {
  		ApiListResponse<List<Member>> response = new ApiListResponse<List<Member>>(0, e.getMessage(),null, 0);
         return ResponseEntity.badRequest().body(response);
  		}
  	}
 
    @PostMapping("/memberremarks")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiListResponse<List<Map<String, Object>>>> getMemberDetails(@RequestBody MemberDTO request) {    	
  	try {
       List<Map<String, Object>>  members = (List<Map<String, Object>> ) memberService.memberManagement(request);
	  
	   Long count= memberService.getCountOfFilteredMembers(request);
	   ApiListResponse<List<Map<String, Object>> > response = new ApiListResponse<>(1, null, members,count);	
	   return ResponseEntity.ok(response);
	    }
  	catch(Exception e) {
  		ApiListResponse<List<Map<String, Object>>> response = new ApiListResponse<List<Map<String, Object>>>(0, e.getMessage(),null, 0);
         return ResponseEntity.badRequest().body(response);
  		}
  	}
    @PostMapping("/withdrawlist")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiListResponse<List<Map<String, Object>>>> getAllWithdrawList(@RequestBody MemberDTO request) {    	
  	try {
  		List<Map<String, Object>>  members = (List<Map<String, Object>> ) memberService.getAllWithdrawMembers(request); 
  		Long total = memberService.getCountWithdrawMembers();
  		
  	   ApiListResponse<List<Map<String, Object>> > response = new ApiListResponse<>(1, null, members,total);	
  	   return ResponseEntity.ok(response);
	    }
  	catch(Exception e) {
  		ApiListResponse<List<Map<String, Object>> > response = new ApiListResponse<List<Map<String, Object>> >(0, e.getMessage(),null, 0);
         return ResponseEntity.badRequest().body(response);
  		}
	//return null;
  	}
    
    /**
     * Add member function for user registration
     * @param member
     * @return
     */
    @PostMapping("/addMember")
    public ResponseEntity<ApiResponse<Member>> addMember(@RequestBody MemberRequest member) {  
    	try {
    		
        Member result= memberService.saveMember(member);
        ApiResponse<Member> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Add member by Admin 
     * @param request
     * @return
     */
    @PostMapping("/admin/addMember")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Member>> addMemberAdmin(@RequestBody MemberRequest request) {  
    	try {
    		
        Member result= memberService.saveMemberAdmin(request);
        ApiResponse<Member> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
   
    /**
     * Update member details user 
     * @param member
     * @return
     */
    @PutMapping("/updatemember")
    @PreAuthorize("hasAuthority('USER')")
    public ResponseEntity<ApiResponse<Member>> updateMember(@RequestBody Member member) {  
    	try {
        Member result= memberService.updateMember(member);
        ApiResponse<Member> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Update member by admin
     * @param request
     * @return
     */
    @PutMapping("admin/updatemember")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Member>> updateMemberAdmin(@RequestBody MemberRequest request) {  
    	try {
        Member result= memberService.updateMemberAdmin(request);
        ApiResponse<Member> response = new ApiResponse<>(1, null, result);
        return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Get member details by member id
     * @param id
     * @return
     */
    @GetMapping("/getMemberById/{id}")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Map<String, Object>>> findMemberById(@PathVariable int id) {
        try {
            Map<String, Object> result = memberService.getMemberById(id);
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
        } catch (IllegalArgumentException e) {
            ApiResponse<Map<String, Object>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Get all member details
     * @return
     */
    
    @GetMapping("/allMembers")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<List<Member>>>  findAllProducts() {      
    	try {
    		
        List<Member> members= memberService.getAllMembers();
        ApiResponse<List<Member>> response = new ApiResponse<>(1, null, members);
        return ResponseEntity.ok(response);
    	}catch(IllegalArgumentException e) {
    		ApiResponse<List<Member>> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
    	}
    }
    
    /**
     * Api to block member
     * @param id
     * @return
     */
    @GetMapping("/blockMember/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Member>> blockMember(@PathVariable int id){
    	try {
    		 Member result= memberService.blockMember(id);
             ApiResponse<Member> response = new ApiResponse<>(1, null, result);
             return ResponseEntity.ok(response);
    		
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Api to UnBlock Member
     * @param id
     * @return
     */
    
    @GetMapping("/unBlockMember/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Member>> UnBlockMember(@PathVariable int id){
    	try {
    		 Member result= memberService.unBlockMember(id);
             ApiResponse<Member> response = new ApiResponse<>(1, null, result);
             return ResponseEntity.ok(response);
    		
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * delete member
     */
    
    @DeleteMapping("/member/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Member>> deleteMember(@PathVariable("id") Integer memberId){
    	try {
    		 Member result= memberService.deleteMember(memberId);
             ApiResponse<Member> response = new ApiResponse<>(1, null, result);
             return ResponseEntity.ok(response);
    		
    	}catch (IllegalArgumentException e) {
            ApiResponse<Member> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
    
    /**
     * Download member data
     * 
     */
    @PostMapping("member/downloadmemberdata")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> exportmemberdataToCSV(@RequestBody MemberDataDownloadDTO request, HttpServletResponse response){
	    try {
	        String msg = memberService.exportMemberDataCsv(request, response);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
    /**
     * get member by id
     * @param request
     * @return
     */
    @PostMapping("checkMemberId")
    @PreAuthorize("hasAuthority('USER') or hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> checkMemberId(@RequestBody Member request){
	    try {
	        String msg = memberService.checkMemberId(request);

	        ApiResponse<String> resp = new ApiResponse<>(1, null, msg);
	        return ResponseEntity.ok(resp);
	    } catch (IllegalArgumentException e) {
	        ApiResponse<String> resp = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.ok(resp);
	    }
	}
}


