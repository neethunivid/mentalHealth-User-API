package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

public class RemarkDownloadDTO {
	private String remarkid;
	private String memberid;
	private String title;
	private LocalDateTime fromDate;
    private LocalDateTime toDate;	
	private Boolean chkspecify;
	private Boolean chkall;
	private Boolean normal;
	private Boolean anxiety;
	private Boolean blackmail;
	private Boolean other;
	private Boolean growingup;
	private Boolean addition1;
	private Boolean addition2;
	
	
	
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	
		
	public Boolean getChkspecify() {
		return chkspecify;
	}
	public void setChkspecify(Boolean chkspecify) {
		this.chkspecify = chkspecify;
	}
	public Boolean getChkall() {
		return chkall;
	}
	public void setChkall(Boolean chkall) {
		this.chkall = chkall;
	}
	public Boolean getNormal() {
		return normal;
	}
	public void setNormal(Boolean normal) {
		this.normal = normal;
	}
	public Boolean getAnxiety() {
		return anxiety;
	}
	public void setAnxiety(Boolean anxiety) {
		this.anxiety = anxiety;
	}
	public Boolean getBlackmail() {
		return blackmail;
	}
	public void setBlackmail(Boolean blackmail) {
		this.blackmail = blackmail;
	}
	public Boolean getOther() {
		return other;
	}
	public void setOther(Boolean other) {
		this.other = other;
	}
	public Boolean getGrowingup() {
		return growingup;
	}
	public void setGrowingup(Boolean growingup) {
		this.growingup = growingup;
	}
	public Boolean getAddition1() {
		return addition1;
	}
	public void setAddition1(Boolean addition1) {
		this.addition1 = addition1;
	}
	public Boolean getAddition2() {
		return addition2;
	}
	public void setAddition2(Boolean addition2) {
		this.addition2 = addition2;
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
	public LocalDateTime getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDateTime fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDateTime getToDate() {
		return toDate;
	}
	public void setToDate(LocalDateTime toDate) {
		this.toDate = toDate;
	}
	
	
}
