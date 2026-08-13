package com.mentalhealthapi.dto;

public class MailMagazineDTO {
	 private String emailPc;
	 private String emailMob;
	 private String email_smart;
	 private Integer mailmagazine;
	 private Integer id;
	 private String name;
	
	 
	public String getEmailPc() {
		return emailPc;
	}
	public void setEmailPc(String emailPc) {
		this.emailPc = emailPc;
	}
	public String getEmailMob() {
		return emailMob;
	}
	public void setEmailMob(String emailMob) {
		this.emailMob = emailMob;
	}
	public Integer getMailMagazine() {
		return mailmagazine;
	}
	public void setMailMagazine(Integer mailmagazine) {
		this.mailmagazine = mailmagazine;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getEmail_smart() {
		return email_smart;
	}
	public void setEmail_smart(String email_smart) {
		this.email_smart = email_smart;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	

}
