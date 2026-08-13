package com.mentalhealthapi.dto;

import java.time.LocalDateTime;

public class CreateReplyDTO {
	 private Integer checkSheetType;
	 private byte mail_type;
	 private String title;
	 private LocalDateTime CreatedOn;
	 private LocalDateTime ModifiedOn;
	 private byte sendStatus;
	 
	 public LocalDateTime getCreatedOn() {
		return CreatedOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		CreatedOn = createdOn;
	}
	public LocalDateTime getModifiedOn() {
		return ModifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		ModifiedOn = modifiedOn;
	}
	public byte getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(byte sendStatus) {
		this.sendStatus = sendStatus;
	}
	public Integer getCheckSheetType() {
		return checkSheetType;
	}
	public void setCheckSheetType(Integer checkSheetType) {
		this.checkSheetType = checkSheetType;
	}
	public byte getMail_type() {
		return mail_type;
	}
	public void setMail_type(byte mail_type) {
		this.mail_type = mail_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getMagMailSubject() {
		return magMailSubject;
	}
	public void setMagMailSubject(String magMailSubject) {
		this.magMailSubject = magMailSubject;
	}
	private String content;
	 private String magMailSubject;

}
