package com.mentalhealthapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import jakarta.persistence.Column;

public class DiagnoseMailMagazineDTO {
	private int checkSheetTypeID;
	private int diagonseMagID;
	private List<Integer> diagonseCheckID;
    private String title;
    private int mailType;
	private String content;
	
	private LocalDateTime sentTime;

	private int sendStatus;
	
	private LocalDateTime createdOn;
	
	private LocalDateTime modifiedOn;
	
	private String magMailSubject;
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

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public void setSentTime(LocalDateTime sentTime) {
		this.sentTime = sentTime;
	}

	public int getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}

	public LocalDateTime getCreatedOn() {
		return createdOn;
	}

	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}

	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}

	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}

	public String getMagMailSubject() {
		return magMailSubject;
	}

	public void setMagMailSubject(String magMailSubject) {
		this.magMailSubject = magMailSubject;
	}

	

	public int getCheckSheetTypeID() {
		return checkSheetTypeID;
	}

	public void setCheckSheetTypeID(int checkSheetTypeID) {
		this.checkSheetTypeID = checkSheetTypeID;
	}

	

	public int getMailType() {
		return mailType;
	}

	public void setMailType(int mailType) {
		this.mailType = mailType;
	}

	public int getDiagonseMagID() {
		return diagonseMagID;
	}

	public void setDiagonseMagID(int diagonseMagID) {
		this.diagonseMagID = diagonseMagID;
	}

	public List<Integer> getDiagonseCheckID() {
		return diagonseCheckID;
	}

	public void setDiagonseCheckID(List<Integer> diagonseCheckID) {
		this.diagonseCheckID = diagonseCheckID;
	}
}
