package com.mentalhealthapi.dto;

import com.mentalhealthapi.service.SortDirection;

public class MemberDeleteDTO {
	private int id;
	private String memberId;
	private String password;
    private String email_pc;
    private String reasonLeaving;
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getMemberId() {
		return memberId;
	}
	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}
	public String getEmail_pc() {
		return email_pc;
	}
	public void setEmail_pc(String email_pc) {
		this.email_pc = email_pc;
	}
	public String getReasonLeaving() {
		return reasonLeaving;
	}
	public void setReasonLeaving(String reasonLeaving) {
		this.reasonLeaving = reasonLeaving;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
    
 
}