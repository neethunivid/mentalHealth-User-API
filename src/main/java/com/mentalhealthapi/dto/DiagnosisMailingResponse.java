package com.mentalhealthapi.dto;

import java.time.LocalDateTime;

public class DiagnosisMailingResponse {
	 private int id;
	    private String checksheetname;
	    private String magMailSubject;
	    private String device;
	    private LocalDateTime sentTime;
	    private int sendStatus;
	    private int typeId;
	    private String content;
	    private String title;
	    private String userEmail;
	    private String dcCheckID; 

	    // Constructor 
	    public DiagnosisMailingResponse(int id, String checksheetname, String device, LocalDateTime sentTime, int sendStatus,
	    		String userEmail,int typeId,String dcCheckID,String magMailSubject,String content,
	    		String title) {
	        this.id = id;
	        this.checksheetname = checksheetname;
	        this.device = device;
	        this.dcCheckID = dcCheckID;
	        this.sentTime = sentTime;
	        this.sendStatus=sendStatus;
	        this.setUserEmail(userEmail);
	        this.typeId=typeId;
	        this.magMailSubject=magMailSubject;
	        this.content=content;
	        this.setTitle(title);
	    }

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getChecksheetname() {
		return checksheetname;
	}

	public void setChecksheetname(String checksheetname) {
		this.checksheetname = checksheetname;
	}

	public String getDevice() {
		return device;
	}

	public void setDevice(String device) {
		this.device = device;
	}

	public LocalDateTime getSentTime() {
		return sentTime;
	}

	public void setSentTime(LocalDateTime sentTime) {
		this.sentTime = sentTime;
	}

	

	public Integer getSendStatus() {
		return sendStatus;
	}

	public void setSendStatus(Integer sendStatus) {
		this.sendStatus = sendStatus;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public String getDcCheckID() {
		return dcCheckID;
	}

	public void setDcCheckID(String dcCheckID) {
		this.dcCheckID = dcCheckID;
	}

	public int getTypeId() {
		return typeId;
	}

	public void setTypeId(int typeId) {
		this.typeId = typeId;
	}

	public String getMagMailSubject() {
		return magMailSubject;
	}

	public void setMagMailSubject(String magMailSubject) {
		this.magMailSubject = magMailSubject;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

}
