package com.mentalhealthapi.dto;

import java.util.Date;

public class RemarkSearchDTO {
	private String remarkid;
	private String memberid;
	private String title;
	private Date fromDate;
	private Date toDate;
	
	
	
	
	public Date getFromDate() {
		return fromDate;
	}
	public void setFromDate(Date fromDate) {
		this.fromDate = fromDate;
	}
	public Date getToDate() {
		return toDate;
	}
	public void setToDate(Date toDate) {
		this.toDate = toDate;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getRemarkid() {
		return remarkid;
	}
	public void setRemarkid(String remarkid) {
		this.remarkid = remarkid;
	}
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	
	

}
