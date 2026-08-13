package com.mentalhealthapi.dto;

import java.time.LocalDateTime;
import java.util.Date;

public class MailMagazineBodyDTO {

		private String subject;
		
	    private String text;
		
	    private LocalDateTime sendOutTime;
		
		private int sendStatus;
		
		private int mailMagJobId;
		
		private int createdId;
		
		private int status;
		
		private String type;
		private Date date;

		public String getSubject() {
			return subject;
		}

		public void setSubject(String subject) {
			this.subject = subject;
		}

		public String getText() {
			return text;
		}

		public void setText(String text) {
			this.text = text;
		}

		public LocalDateTime getSendOutTime() {
			return sendOutTime;
		}

		public void setSendOutTime(LocalDateTime sendOutTime) {
			this.sendOutTime = sendOutTime;
		}

		public int getSendStatus() {
			return sendStatus;
		}

		public void setSendStatus(int sendStatus) {
			this.sendStatus = sendStatus;
		}

		public int getMailMagJobId() {
			return mailMagJobId;
		}

		public void setMailMagJobId(int mailMagJobId) {
			this.mailMagJobId = mailMagJobId;
		}

		public int getCreatedId() {
			return createdId;
		}

		public void setCreatedId(int createdId) {
			this.createdId = createdId;
		}

		public int getStatus() {
			return status;
		}

		public void setStatus(int status) {
			this.status = status;
		}

		public String getType() {
			return type;
		}

		public void setType(String type) {
			this.type = type;
		}

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}
		
			

	}



