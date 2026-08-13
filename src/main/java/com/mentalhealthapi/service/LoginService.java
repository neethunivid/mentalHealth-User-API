package com.mentalhealthapi.service;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.AuthLoginResponse;
import com.mentalhealthapi.exceptions.ResourceNotFoundException;
import com.mentalhealthapi.helper.ApiResponse;
import com.mentalhealthapi.model.AdminUpdateHistory;
import com.mentalhealthapi.model.Config;
import com.mentalhealthapi.model.Member;
import com.mentalhealthapi.repository.AdminUpdateHistoryRepository;
import com.mentalhealthapi.repository.ConfigRepository;
import com.mentalhealthapi.repository.MemberRepository;

import jakarta.transaction.Transactional;

@Service
public class LoginService {
	@Autowired
	private MemberService memberService;
	private ConfigRepository configRepo;
	private MemberRepository memberRepo;
	private AdminUpdateHistoryRepository adHistoryRepo;
	public LoginService(MemberService memberService,AdminUpdateHistoryRepository adHistoryRepo,ConfigRepository configRepo,MemberRepository memberRepo) {
		super();
		this.memberService = memberService;
		this.configRepo=configRepo;
		this.memberRepo=memberRepo;
		this.adHistoryRepo=adHistoryRepo;
	}
	public ResponseEntity<ApiResponse> authenticationService(String username, String password, String role) {

		Member m = memberService.validateUser(username, password);
		ApiResponse response = new ApiResponse();
		AuthLoginResponse responseObj = new AuthLoginResponse();
		if (m != null) {
			// Generate and return a JWT token
			String token = memberService.generateToken(username);
			
			responseObj.setName(m.getName()); // Set the name value
			responseObj.setMemberId(m.getMemberid()); // Set the memberId value
			responseObj.setEmailMob(m.getEmail_mob()); // Set the email value
			responseObj.setStatus(m.getStatus());
			responseObj.setMemberNo(m.getMemberno());
			responseObj.setMobile(m.getMob());
			responseObj.setId(m.getId());
			responseObj.setNamek(m.getNamek()); 
			responseObj.setEmailPc(m.getEmail_pc());
			if (!role.equals(m.getRole())) {
				response.setCode(1);
				response.setError(null);
				// Return an error message or throw an exception
				response.setData("Invalid credentials");
			} else {
				Map<String, Object> result = new HashMap<>();
				result.put("token", token);
				result.put("user", responseObj);
				response.setCode(1);
				response.setError(null);
				response.setData(result);

			}
		} else {
			response.setCode(1);
			response.setError(null);
			// Return an error message or throw an exception
			response.setData("Invalid credentials");

		}
		return ResponseEntity.ok(response);

	}
	@Transactional
	public String updateAdminDetails(String username, String password, String sex) {
		
		try {
		Config c=configRepo.findByKey("ADMIN_USERNAME").orElseThrow(() ->new ResourceNotFoundException("Key value not found "));
		
		
		Config c_pas=configRepo.findByKey("ADMIN_PASSWORD").orElseThrow(() ->new ResourceNotFoundException("Key value not found "));
	
		Config c_sex=configRepo.findByKey("SEX").orElseThrow(() ->new ResourceNotFoundException("Key value not found "));
		
		
		configRepo.updateConfigByKey(c.getId(),"",username);
		configRepo.updateConfigByKey(c_pas.getId(),"",password);
		configRepo.updateConfigByKey(c_sex.getId(),"",sex);

		
		///Updating member table for admin role
		Member m = memberRepo.getAdminMember();
		if(m.getRole().equals("ADMIN"))
		{
			m.setMemberid(username);
			m.setPassword(password);
			m.setSex(sex == "男性" ? 1 : 2);
			memberRepo.save(m);
		}
		
		//insert details to admin update history table
		AdminUpdateHistory adhistory=new AdminUpdateHistory(username,sex,LocalDateTime.now());
		adHistoryRepo.save(adhistory);
		ApiResponse response = new ApiResponse();
		return "Updated Successfully";
		}catch(Exception e)
		{
			return "error: "+e.getMessage();
		}
	}
}
