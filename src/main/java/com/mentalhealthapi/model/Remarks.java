package com.mentalhealthapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.Table;

@Entity
@Table(name="remarks")
public class Remarks {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="remark_no",columnDefinition = "VARCHAR(255)")
	 private String remarkNo;
	
//	@ManyToOne(fetch = FetchType.LAZY)
//    @JoinColumn(name = "member", referencedColumnName = "memberid")
//	
//    private Member members;
	
	private Integer member;
	
	@Column(columnDefinition = "tinyint")
    private byte normal;
	
	@Column(columnDefinition = "tinyint")
    private byte anxiety;
	
	@Column(columnDefinition = "tinyint")
    private byte blackmail;
	
	@Column(columnDefinition = "tinyint")
    private byte other;

	@Column(columnDefinition = "tinyint")
    private byte growingup;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String addition1;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String addition2;
	
	@Column(columnDefinition = "VARCHAR(255)")
	 private String title;
	
	@Column(columnDefinition = "TEXT")
    private String text;
	
	private Integer remarks;
	
	private LocalDateTime date;
	
	private Integer status;
	
	@Column(name="org_created_date")
	private LocalDateTime orgCreatedDate;
	
	
	
	
	
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

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	
//	public Member getMembers() {
//		return members;
//	}
//
//	public void setMembers(Member members) {
//		this.members = members;
//	}

	public byte getNormal() {
		return normal;
	}

	public void setNormal(byte normal) {
		this.normal = normal;
	}

	public byte getAnxiety() {
		return anxiety;
	}

	public void setAnxiety(byte anxiety) {
		this.anxiety = anxiety;
	}

	public byte getBlackmail() {
		return blackmail;
	}

	public void setBlackmail(byte blackmail) {
		this.blackmail = blackmail;
	}

	public byte getOther() {
		return other;
	}

	public void setOther(byte other) {
		this.other = other;
	}

	public byte getGrowingup() {
		return growingup;
	}

	public void setGrowingup(byte growingup) {
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

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public LocalDateTime getOrgCreatedDate() {
		return orgCreatedDate;
	}

	public void setOrgCreatedDate(LocalDateTime orgCreatedDate) {
		this.orgCreatedDate = orgCreatedDate;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public int getTotal() {
		// TODO Auto-generated method stub
		return 0;
	}

	public void setTotal(int i) {
		// TODO Auto-generated method stub
		
	}

	

	

	

}
