package com.mentalhealthapi.dto;

import java.time.LocalDate;

public class DiagnoseCheckDTO {
	private Integer id;
	private String Date;
	private String username;
	private String userEmail;
	private String mailSendDate;
	private String title;
	private String deviceinfo;
	private String diagnosisResult;
	private String checksheetContent;
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getDate() {
		return Date;
	}
	public void setDate(String date) {
		Date = date;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getUserEmail() {
		return userEmail;
	}
	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}
	public String getMailSendDate() {
		return mailSendDate;
	}
	public void setMailSendDate(String mailSendDate) {
		this.mailSendDate = mailSendDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getDeviceinfo() {
		return deviceinfo;
	}
	public void setDeviceinfo(String deviceinfo) {
		this.deviceinfo = deviceinfo;
	}
	public String getDiagnosisResult() {
		return diagnosisResult;
	}
	public void setDiagnosisResult(String diagnosisResult) {
		this.diagnosisResult = diagnosisResult;
	}
	public String getChecksheetContent() {
		return checksheetContent;
	}
	public void setChecksheetContent(String checksheetContent) {
		this.checksheetContent = checksheetContent;
	}
	
	
	

}
