package com.mentalhealthapi.dto;


import java.sql.Date;
import java.time.LocalDate;

public class MailMagUserList {
	private int id;
    private Date date;
    private String email;
    private String name;
    private String memberid;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	
}
