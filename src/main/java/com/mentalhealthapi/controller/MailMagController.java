package com.mentalhealthapi.controller;

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

import com.mentalhealthapi.dto.MailMagDTO;
import com.mentalhealthapi.dto.MailMagUserList;
import com.mentalhealthapi.dto.MailMaganizeDTO;
import com.mentalhealthapi.dto.ResponseList;
import com.mentalhealthapi.dto.SendMagDTO;
import com.mentalhealthapi.dto.TopicDTO;
import com.mentalhealthapi.helper.ApiListResponse;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.MagUser;
import com.mentalhealthapi.model.MailMagJob;
import com.mentalhealthapi.model.MailMagazineBody;
import com.mentalhealthapi.model.Mailmagazine;
import com.mentalhealthapi.model.MailmagazineUser;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.model.Remarks;
import com.mentalhealthapi.service.MailMagazineService;




@RestController
@RequestMapping("/api/mailmag")
public class MailMagController {
	private final MailMagazineService mailMagazineService;
    @Autowired
    private JavaMailSender mailSender;
        
    public MailMagController(MailMagazineService mailMagazineService) {
        this.mailMagazineService = mailMagazineService;
    }
    
	/**
	 * list bbs user 
	 * @param request
	 * @return
	 */
	@PostMapping("/listbbs")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<MailmagazineUser>>> mailMagList(@RequestBody MailMagDTO request) {
	    try {
	        List<MailmagazineUser> mailmagazineUsers = mailMagazineService.findMailmagazineUsers2(request);
	        int totalCount = (int) mailMagazineService.getTotalCountforActiveMailSubMember(request,"BBS"); // Get the total count
	        ApiListResponse<List<MailmagazineUser>> response = new ApiListResponse<>(
	                1,
	                "Success",
	                mailmagazineUsers,
	                totalCount
	        );
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        ApiListResponse<List<MailmagazineUser>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	/**
	 * list general user
	 * @param request
	 * @return
	 */
	
	@PostMapping("/listgeneral")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<MailmagazineUser>>> mailMagListGeneral(@RequestBody MailMagDTO request) {
	    try {
	        List<MailmagazineUser> mailmagazineUsers = mailMagazineService.findMailmagazineUsersGeneral(request);
	        int totalCount = (int) mailMagazineService.getTotalCount(request,"GENERAL"); // Get the total count
	        ApiListResponse<List<MailmagazineUser>> response = new ApiListResponse<>(
	                1,
	                "Success",
	                mailmagazineUsers,
	                totalCount
	        );
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        ApiListResponse<List<MailmagazineUser>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	
	/**
	 * update general user details
	 * @param id
	 * @param muser
	 * @return
	 */
	@PutMapping("/update/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<MailmagazineUser>> updateGeneral(@PathVariable Integer id, @RequestBody MailmagazineUser muser) {  
    	try {
    		MailmagazineUser result= mailMagazineService.updateGeneral(id,muser);
            ApiResponse<MailmagazineUser> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<MailmagazineUser> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }

	/**
	 * Delete general user
	 * @param id
	 * @return
	 */
	@DeleteMapping("/delete/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> deleteGeneral(@PathVariable("id") Integer id) {  
		 try {
	            String result = mailMagazineService.deleteGeneral(id);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	
/**
 * Mail magazine register save data to mail magazine 
 * @param request
 * @return
 */
	@PostMapping("/register")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<Mailmagazine>> mailMagRegister(@RequestBody Mailmagazine request) {
	    try {
	    	Mailmagazine mailmagazine = mailMagazineService.registerMailmagazine(request);
	        
	        ApiResponse<Mailmagazine> response = new ApiResponse<>(1,"Success",mailmagazine);
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        ApiResponse<Mailmagazine> response = new ApiResponse<>(0, e.getMessage(), null);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	

    /**
     *    send newsletter by mail
     * @return
     */
	@GetMapping("/sendNewslettersemail")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiResponse<String>> sendNewsLetterJob() {  
		 try {
	            String result = mailMagazineService.sendMailMagSchedulerService();
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	        } catch (Exception e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	
	
	/**
	 * list of general mag user
	 * @param request
	 * @return
	 */
	@PostMapping("/list")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<Mailmagazine>>> mailMagEmailList(@RequestBody MailMaganizeDTO request) {
	    try {
	        List<Mailmagazine> mailmagazine = mailMagazineService.findMailMagazine(request);
	        
	        ApiListResponse<List<Mailmagazine>> response = new ApiListResponse<>(1,"Success", mailmagazine,mailMagazineService.countMailMagazine(request));
	        return ResponseEntity.ok(response);
	    } catch (Exception e) {
	        ApiListResponse<List<Mailmagazine>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
	        return ResponseEntity.badRequest().body(response);
	    }
	}
	/**
	 * Update mail magazine by id
	 * @param id
	 * @param mailmag
	 * @return
	 */
	@PutMapping("/updatemail/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Mailmagazine>> updateMail(@PathVariable Integer id, @RequestBody Mailmagazine mailmag) {  
    	try {
    		Mailmagazine result= mailMagazineService.updateMail(id,mailmag);
            ApiResponse<Mailmagazine> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Mailmagazine> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	
	/**
	 * Get details from mailmagazine by id
	 * @param id
	 * @return
	 */
	@GetMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<Mailmagazine>> getMailMag(@PathVariable Integer id) {  
    	try {
    		Mailmagazine result= mailMagazineService.getMailMag(id);
            ApiResponse<Mailmagazine> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<Mailmagazine> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	
	/**
	 * list of mail magazine users
	 * @param request
	 * @return
	 */
	
	@PostMapping("/maguserlist")
    @PreAuthorize("hasAuthority('ADMIN')")
	public ResponseEntity<ApiListResponse<List<MailMagUserList>>> nonuserlist(@RequestBody MailMaganizeDTO request) {
	    try {
	        Map<String, Object> mailmagazine = mailMagazineService.nonuserlist(request);
	        
	        if (mailmagazine != null) {
	            List<MailMagUserList> data = (List<MailMagUserList>) mailmagazine.get("data");
	            Long totalCount = (Long) mailmagazine.get("count");
	            
	            ApiListResponse<List<MailMagUserList>> response = new ApiListResponse<>(1, "Success", data, totalCount);
	            return ResponseEntity.ok(response);
	        } else {
	            ApiListResponse<List<MailMagUserList>> response = new ApiListResponse<>(0, "Data not available", null, 0);
	            return ResponseEntity.badRequest().body(response);
	        }
	    } catch (Exception e) {
	        ApiListResponse<List<MailMagUserList>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
	        return ResponseEntity.badRequest().body(response);
	    }
	}

	
	/**
	 * Delete mail magazine user by id
	 * @param id
	 * @return
	 */
	@DeleteMapping("/deletemailmaguser/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public ResponseEntity<ApiResponse<String>> deletemailmaguser(@PathVariable Integer id) {  
    	try {
    		String result= mailMagazineService.deletemailmaguser(id);
            ApiResponse<String> response = new ApiResponse<>(1, null, result);
            return ResponseEntity.ok(response);
    	}catch (IllegalArgumentException e) {
            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
            return ResponseEntity.badRequest().body(response);
        }
    }
	

/**
 * get topic by type 	
 * @param type
 * @return
 */
	 @GetMapping("/get-topic/{type}")
	    public ResponseEntity<ApiResponse<List<ResponseList>>> getTopic(@PathVariable String type) {
	        try {
	            List<ResponseList> result = mailMagazineService.getTopic(type);
	            ApiResponse<List<ResponseList>> response = new ApiResponse<>(1, "Success", result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<List<ResponseList>> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	
	 /**
	  * edit topic by id
	  * @param id
	  * @param topic
	  * @return
	  */
	 @PutMapping("/edittopic/{id}")
	    public ResponseEntity<ApiResponse<String>> editTopic(@PathVariable Integer id,@RequestBody TopicDTO topic) {
	        try {
	        	String result = mailMagazineService.editTopic(id,topic.getTopic());
	            ApiResponse<String> response = new ApiResponse<>(1, "Success", result);
	            return ResponseEntity.ok(response);
	        } catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
	 /**
	  * magazine theme list 
	  * @param request
	  * @return
	  */
		@PostMapping("/magthemelist")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiListResponse<List<MailMagazineBody>>> mailMagListByTheme(@RequestBody MailMaganizeDTO request) {
		    try {
		        List<MailMagazineBody> mailmagazine = mailMagazineService.findMailmagazineByTheme();
		       
		        ApiListResponse<List<MailMagazineBody>> response = new ApiListResponse<>(1,"Success", mailmagazine,0);
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        ApiListResponse<List<MailMagazineBody>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		/**
		 * Delete mail magazine body
		 * @param id
		 * @return
		 */
		@DeleteMapping("/deletemailmagbody/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<String>> deletemailmagbody(@PathVariable Integer id) {  
	    	try {
	    		String result= mailMagazineService.deletemailmagBody(id);
	            ApiResponse<String> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	    	}catch (IllegalArgumentException e) {
	            ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
		
		/**
		 * create mail magazine by theme
		 * @param request
		 * @return
		 */
		@PostMapping("/magThemeCreate")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<MailMagazineBody>> mailMagRegister(@RequestBody MailMagazineBody request) {
		    try {
		    	MailMagazineBody mailmagazine = mailMagazineService.registerMailmagazineBody(request);
		        
		        ApiResponse<MailMagazineBody> response = new ApiResponse<>(1,"Success",mailmagazine);
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        ApiResponse<MailMagazineBody> response = new ApiResponse<>(0, e.getMessage(), null);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		
		/**
		 * edit mail magazine theme by id
		 * @param id
		 * @param request
		 * @return
		 */
		@PutMapping("/magThemeUpdate/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<MailMagazineBody>> mailMagBodyUpdate(@PathVariable Integer id,@RequestBody MailMagazineBody request) {
		    try {
		    	MailMagazineBody mailmagazine = mailMagazineService.updateMailMagBody(id,request);
		        
		        ApiResponse<MailMagazineBody> response = new ApiResponse<>(1,"Success",mailmagazine);
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        ApiResponse<MailMagazineBody> response = new ApiResponse<>(0, e.getMessage(), null);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		/**
		 * 
		 * @param request
		 * @return
		 */
		@PostMapping("/sendMag")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiResponse<String>> mailMagSend(@RequestBody SendMagDTO request) {
		    try {
		    	String result = mailMagazineService.sendMagazine(request);
		        
		        ApiResponse<String> response = new ApiResponse<>(1,"Success",result);
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        ApiResponse<String> response = new ApiResponse<>(0, e.getMessage(), null);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		
		@GetMapping("/mailjob/{id}")
	    @PreAuthorize("hasAuthority('ADMIN')")
	    public ResponseEntity<ApiResponse<MailMagJob>> getMailMagjob(@PathVariable Integer id) {  
	    	try {
	    		MailMagJob result= mailMagazineService.getMailMagJob(id);
	            ApiResponse<MailMagJob> response = new ApiResponse<>(1, null, result);
	            return ResponseEntity.ok(response);
	    	}catch (IllegalArgumentException e) {
	            ApiResponse<MailMagJob> response = new ApiResponse<>(0, e.getMessage(), null);
	            return ResponseEntity.badRequest().body(response);
	        }
	    }
		
		@PostMapping("/maguserSelected")
	    @PreAuthorize("hasAuthority('ADMIN')")
		public ResponseEntity<ApiListResponse<List<MagUser>>> maguserSelected(@RequestBody MailMaganizeDTO request) {
		    try {
		        List<MagUser> mailmagazine = mailMagazineService.getMagUsersByMailMagazine(request.getMailmagId());
		       
		        ApiListResponse<List<MagUser>> response = new ApiListResponse<>(1,"Success", mailmagazine,mailMagazineService.countMailMagazine(request.getMailmagId()));
		        return ResponseEntity.ok(response);
		    } catch (Exception e) {
		        ApiListResponse<List<MagUser>> response = new ApiListResponse<>(0, e.getMessage(), null, 0);
		        return ResponseEntity.badRequest().body(response);
		    }
		}
		
		
}
