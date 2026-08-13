package com.mentalhealthapi.dto;

import java.time.LocalDateTime;
import java.util.List;

import com.mentalhealthapi.model.Remarks;

public class RemarkSearchResponse {
	  
	    private Integer id;
	    private String remarkNo;
	    private Integer member;
	    private Byte normal;
        private Byte anxiety;
        private Byte blackmail;
        private Byte other;
        private Byte growingup;
        private String addition1;
        private String addition2;
	    private String title;
	    private String text;
	    private Integer remarks;
	    private LocalDateTime date;
	    private LocalDateTime orgCreatedDate;
	    private String Name;
	    private String EmailPc;
	    private String memberid;
	    private Integer Member;
        
        
        
	    public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getRemarkNo() {
			return remarkNo;
		}
		public void setRemarkNo(String remarkNo) {
			this.remarkNo = remarkNo;
		}
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getText() {
			return text;
		}
		public void setText(String text) {
			this.text = text;
		}
		public Integer getRemarks() {
			return remarks;
		}
		public void setRemarks(Integer remarks) {
			this.remarks = remarks;
		}
		public LocalDateTime getDate() {
			return date;
		}
		public void setDate(LocalDateTime date) {
			this.date = date;
		}
		public String getName() {
			return Name;
		}
		public void setName(String name) {
			Name = name;
		}
		public String getEmailPc() {
			return EmailPc;
		}
		public void setEmailPc(String emailPc) {
			EmailPc = emailPc;
		}
		public Integer getMember() {
			return Member;
		}
		public void setMember(Integer member) {
			Member = member;
		}
		public Byte getNormal() {
			return normal;
		}
		public void setNormal(Byte normal) {
			this.normal = normal;
		}
		public Byte getAnxiety() {
			return anxiety;
		}
		public void setAnxiety(Byte anxiety) {
			this.anxiety = anxiety;
		}
		public Byte getBlackmail() {
			return blackmail;
		}
		public void setBlackmail(Byte blackmail) {
			this.blackmail = blackmail;
		}
		public Byte getOther() {
			return other;
		}
		public void setOther(Byte other) {
			this.other = other;
		}
		public Byte getGrowingup() {
			return growingup;
		}
		public void setGrowingup(Byte growingup) {
			this.growingup = growingup;
		}
		public String getAddition1() {
			return addition1;
		}
		public void setAddition1(String addition1) {
			this.addition1 = addition1;
		}
		public String getAddition2() {
			return addition2;
		}
		public void setAddition2(String addition2) {
			this.addition2 = addition2;
		}
		//private int totalCount;
	   // private int searchCount;
		
		
		public LocalDateTime getOrgCreatedDate() {
			return orgCreatedDate;
		}
		public void setOrgCreatedDate(LocalDateTime orgCreatedDate) {
			this.orgCreatedDate = orgCreatedDate;
		}
		public String getMemberid() {
			return memberid;
		}
		public void setMemberid(String memberid) {
			this.memberid = memberid;
		}

	    
	}


