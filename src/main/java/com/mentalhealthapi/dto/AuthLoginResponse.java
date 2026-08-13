package com.mentalhealthapi.dto;

import java.util.Date;

public class AuthLoginResponse {
	    private Integer id;
	    private String name;
	    private String namek;
	    private String emailPc;
	    private String emailMob;
	    private String memberId;
	    private String mobile;
	    private Integer status;
	    public Integer getStatus() {
			return status;
		}
		public void setStatus(Integer status) {
			this.status = status;
		}
		public Integer getId() {
			return id;
		}
		public void setId(Integer id) {
			this.id = id;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getNamek() {
			return namek;
		}
		public void setNamek(String namek) {
			this.namek = namek;
		}
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
		public String getMemberId() {
			return memberId;
		}
		public void setMemberId(String memberId) {
			this.memberId = memberId;
		}
		public String getMobile() {
			return mobile;
		}
		public void setMobile(String mobile) {
			this.mobile = mobile;
		}
		public Integer getMemberNo() {
			return memberNo;
		}
		public void setMemberNo(Integer memberNo) {
			this.memberNo = memberNo;
		}
		private Integer memberNo;
	   
}
