package com.mentalhealthapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="member_deleted")
public class MemberDeleted {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	private Integer member;
	
	

	@Column(columnDefinition = "VARCHAR(255)")
	 private Integer memberno;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String name;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String namek;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String memberid;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String password;
	
	 private Integer sex;
	 
	 private LocalDate dob;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String job;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String address1;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String address2;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String address3;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String tel;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String mob;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String workplace;
	 
	 @Column(columnDefinition = "VARCHAR(255)")
	 private String howfound;
	 
	 @Column(name="email_pc",columnDefinition = "VARCHAR(255)")
	 private String emailPc;
	 
	 @Column(name="email_mob",columnDefinition = "VARCHAR(255)")
	 private String emailMob;
	 
	 private Integer mailmagazine;
	 
	 private Integer remarks_initial;
	 
	 @Column(name="join_date")
	 private LocalDate joinDate;
	 
	 @Column(name="change_date")
	 private LocalDate changeDate;
	 
	 @Column(name="change_cause",columnDefinition = "VARCHAR(255)")
	 private String changeCause;
	 
	 @Column(columnDefinition = "TEXT")
	    private String reasonLeaving;
	 
	 @Column(columnDefinition = "tinyint")
	    private Integer status;
	 
	 @Column(name="date_effect")
	 private LocalDateTime dateEffect;
	 
	 private Integer status2;
	 
	 @Column(name="remove_status")
	  private Integer removeStatus;
	 
	 private LocalDate date;
	 
	 @Column(name="email_smart",columnDefinition = "VARCHAR(255)")
	 private String emailSmart;

	
	 public Integer getId() {
			return id;
		}

		public void setId(Integer id) {
			this.id = id;
		}

	
	

	public Integer getMemberno() {
		return memberno;
	}

	public void setMemberno(Integer memberno) {
		this.memberno = memberno;
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

	public String getReasonLeaving() {
		return reasonLeaving;
	}

	public void setReasonLeaving(String reasonLeaving) {
		this.reasonLeaving = reasonLeaving;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getDateEffect() {
		return dateEffect;
	}

	public void setDateEffect(LocalDateTime dateEffect) {
		this.dateEffect = dateEffect;
	}

	public Integer getStatus2() {
		return status2;
	}

	public void setStatus2(Integer status2) {
		this.status2 = status2;
	}

	public Integer getRemoveStatus() {
		return removeStatus;
	}

	public void setRemoveStatus(Integer removeStatus) {
		this.removeStatus = removeStatus;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public String getEmailSmart() {
		return emailSmart;
	}

	public void setEmailSmart(String emailSmart) {
		this.emailSmart = emailSmart;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}
	 
	 
	 	

}
