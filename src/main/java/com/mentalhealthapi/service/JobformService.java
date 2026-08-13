package com.mentalhealthapi.service;

import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.JobformDTO;

import jakarta.mail.MessagingException;

@Service
public class JobformService {
	private final EmailService emailService;
	
	public JobformService(EmailService emailService) {
		this.emailService = emailService;
		
	}

	public String saveJobform(JobformDTO request) {
		String mailSend;
		try {
			mailSend = emailService.sendJobform(request);
			if(mailSend=="true") {
				return "Mail send successfully";
			}
		} catch (MessagingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return " Failed ";
		
	}

}
