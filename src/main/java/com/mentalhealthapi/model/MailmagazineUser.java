package com.mentalhealthapi.model;

import java.time.LocalDate;
import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "mailmagazine_user")
public class MailmagazineUser {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(columnDefinition = "VARCHAR(255)")
    private String email;

    @Column(name = "email_mob", columnDefinition = "VARCHAR(255)")
    private String emailMob;

    @Column(columnDefinition = "tinyint")
    private Integer status;

    private LocalDate date;

    @Column(name = "member") // Assuming "member" represents the memberno field
    private Integer member;

    @Column(columnDefinition = "VARCHAR(255)")
    private String name;

    @Column(name = "email_smart", columnDefinition = "VARCHAR(255)")
    private String emailSmart;

    @Transient // This annotation tells Hibernate to ignore this field for database mapping
    private String memberName;
    @Transient // This annotation tells Hibernate to ignore this field for database mapping
    private String memberName2;
    public String getMemberName2() {
		return memberName2;
	}

	public void setMemberName2(String memberName2) {
		this.memberName2 = memberName2;
	}

	@Transient
    private String memberId;

    @ManyToOne(fetch = FetchType.LAZY) // Lazy fetch means it will be loaded only when accessed
    @JoinColumn(name = "member", referencedColumnName = "memberno", insertable = false, updatable = false)
    private Member memberDetails;

       
    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmailMob() {
		return emailMob;
	}

	public void setEmailMob(String emailMob) {
		this.emailMob = emailMob;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmailSmart() {
		return emailSmart;
	}

	public void setEmailSmart(String emailSmart) {
		this.emailSmart = emailSmart;
	}

	public Member getMemberDetails() {
		return memberDetails;
	}

	public void setMemberDetails(Member memberDetails) {
		this.memberDetails = memberDetails;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberName() {
        if (memberDetails != null) {
            return memberDetails.getName();
        }
        return memberName;
    }

    public String getMemberId() {
        if (memberDetails != null) {
            return memberDetails.getMemberid();
        }
        return memberId;
    }
}
