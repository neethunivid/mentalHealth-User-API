package com.mentalhealthapi.model;

import java.time.LocalDate;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.mentalhealthapi.helper.LocalDateDeserializer;
import com.mentalhealthapi.helper.LocalDateSerializer;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "member")
public class Member {
	 @Id
	    @GeneratedValue(strategy = GenerationType.IDENTITY)
	    private Integer id;	 
	    private Integer memberno;
	    private String name;      
	    private String namek;	    
	    private String name2;	    
	    private String namek2;	   
	    private String memberid;	   
	    private String password;   
	    private Integer sex;	    	    
	    private LocalDate dob;    
	    private String job;   
	    private String job_additional;	   
	    private String zipcode;	   
	    private Integer pref; 
	    private String address1;	    
	    private String address2;    
	    private String address3;   
	    private String tel;    
	    private String mob;	    
	    private String workplace;	   
	    private String howfound;	
	    @Column(name = "email_pc")
	    private String email_pc;   
	    @Column(name = "email_mob")
	    private String email_mob;	    
	    private Integer mailmagazine;	    
	    private Integer remarks_initial;	  
	    private LocalDate joinDate;	    
	    private LocalDate changeDate;	   
	    private String changeCause;	    
	    private Integer status;	    
	    private Integer ngMember;	    
	    private Integer deleteRequest;	 	   
	    private LocalDate date;	    
	    private String email_smart;
	    private String role;		

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


		public String getName2() {
			return name2;
		}


		public void setName2(String name2) {
			this.name2 = name2;
		}


		public String getNamek2() {
			return namek2;
		}


		public void setNamek2(String namek2) {
			this.namek2 = namek2;
		}


		public String getMemberid() {
			return memberid;
		}


		public void setMemberid(String memberid) {
			this.memberid = memberid;
		}


		public String getPassword() {
			return password;
		}


		public void setPassword(String password) {
			this.password = password;
		}


		public Integer getSex() {
			return sex;
		}


		public void setSex(Integer sex) {
			this.sex = sex;
		}


		public LocalDate getDob() {
			return dob;
		}


		public void setDob(LocalDate dob) {
			this.dob = dob;
		}


		public String getJob() {
			return job;
		}


		public void setJob(String job) {
			this.job = job;
		}


		public String getJob_additional() {
			return job_additional;
		}


		public void setJob_additional(String job_additional) {
			this.job_additional = job_additional;
		}


		public String getZipcode() {
			return zipcode;
		}


		public void setZipcode(String zipcode) {
			this.zipcode = zipcode;
		}


		public Integer getPref() {
			return pref;
		}


		public void setPref(Integer pref) {
			this.pref = pref;
		}


		public String getAddress1() {
			return address1;
		}


		public void setAddress1(String address1) {
			this.address1 = address1;
		}


		public String getAddress2() {
			return address2;
		}


		public void setAddress2(String address2) {
			this.address2 = address2;
		}


		public String getAddress3() {
			return address3;
		}


		public void setAddress3(String address3) {
			this.address3 = address3;
		}


		public String getTel() {
			return tel;
		}


		public void setTel(String tel) {
			this.tel = tel;
		}


		public String getMob() {
			return mob;
		}


		public void setMob(String mob) {
			this.mob = mob;
		}


		public String getWorkplace() {
			return workplace;
		}


		public void setWorkplace(String workplace) {
			this.workplace = workplace;
		}


		public String getHowfound() {
			return howfound;
		}


		public void setHowfound(String howfound) {
			this.howfound = howfound;
		}


		public String getEmail_pc() {
			return email_pc;
		}


		public void setEmail_pc(String email_pc) {
			this.email_pc = email_pc;
		}


		public String getEmail_mob() {
			return email_mob;
		}


		public void setEmail_mob(String email_mob) {
			this.email_mob = email_mob;
		}


		public Integer getMailmagazine() {
			return mailmagazine;
		}


		public void setMailmagazine(Integer mailmagazine) {
			this.mailmagazine = mailmagazine;
		}


		public Integer getRemarks_initial() {
			return remarks_initial;
		}


		public void setRemarks_initial(Integer remarks_initial) {
			this.remarks_initial = remarks_initial;
		}


		public LocalDate getJoinDate() {
			return joinDate;
		}


		public void setJoinDate(LocalDate joinDate) {
			this.joinDate = joinDate;
		}


		public LocalDate getChangeDate() {
			return changeDate;
		}


		public void setChangeDate(LocalDate changeDate) {
			this.changeDate = changeDate;
		}


		public String getChangeCause() {
			return changeCause;
		}


		public void setChangeCause(String changeCause) {
			this.changeCause = changeCause;
		}


		public Integer getStatus() {
			return status;
		}


		public void setStatus(Integer status) {
			this.status = status;
		}


		public Integer getNgMember() {
			return ngMember;
		}


		public void setNgMember(Integer ngMember) {
			this.ngMember = ngMember;
		}


		public Integer getDeleteRequest() {
			return deleteRequest;
		}


		public void setDeleteRequest(Integer deleteRequest) {
			this.deleteRequest = deleteRequest;
		}


		public LocalDate getDate() {
			return date;
		}


		public void setDate(LocalDate date) {
			this.date = date;
		}


		
	    
		public Integer getMemberNo() {
	        return memberno;
	    }

	    public void setMemberNo(Integer memberNo) {
	        this.memberno = memberNo;
	    }
	    
	    public Integer getMemberno() {
			return memberno;
		}


		public void setMemberno(Integer memberno) {
			this.memberno = memberno;
		}


		public Integer getId() {
			
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

		public String getRole() {
			return role;
		}


		public void setRole(String role) {
			this.role = role;
		}


		public String getEmail_smart() {
			return email_smart;
		}


		public void setEmail_smart(String email_smart) {
			this.email_smart = email_smart;
		}


}
