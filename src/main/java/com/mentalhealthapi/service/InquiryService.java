package com.mentalhealthapi.service;

import org.springframework.stereotype.Service;

import com.mentalhealthapi.dto.InquiryDTO;

@Service
public class InquiryService {
	private final EmailService emailService;
	public InquiryService(EmailService emailService) {
		this.emailService = emailService;
		
	}
	
	public String addInquiry(InquiryDTO request) {
		//send mail to admin
		String mailSend = emailService.sendInquiryMail(request);
		if(mailSend=="true") {
			return "Mail send successfully";
		}else {
			return "Mail failed";
		}
	}

}
