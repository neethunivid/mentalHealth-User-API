package com.mentalhealthapi.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class SendMailMagScheduler {
	private final MailMagazineService mailMagService;
	private final CheckSheetMailService checkSheetService;
    @Autowired
    public SendMailMagScheduler(MailMagazineService mailMagService,CheckSheetMailService checkSheetService) {
        this.mailMagService = mailMagService;
        this.checkSheetService=checkSheetService;
    }

	@Scheduled(cron = "0 0/15 * * * ?") // Cron expression for every 15 minutes
    public void sendScheduledEmail() {
		try {
		System.out.println("15min >>");
		  String mag_body =this.mailMagService.sendMailMagSchedulerService();
		  String mag_body_bbs =this.mailMagService.sendMailMagSchedulerServiceForBBS();
		  this.checkSheetService.scheduledEmailSend();
		}catch(Exception e)
		{
			System.out.println("error : "+e.getMessage());
		}
	}
	
}
