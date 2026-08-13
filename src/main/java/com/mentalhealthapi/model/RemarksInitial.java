package com.mentalhealthapi.model;

import java.time.LocalDate;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="remarks_initial")
public class RemarksInitial {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@Column(name="remark_no",columnDefinition = "VARCHAR(255)")
	 private String remarkNo;
	
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
	 
	 private int remarks;
	 
	 private LocalDateTime date;
	
	 private int status;

	
	

	public String getRemarkNo() {
		return remarkNo;
	}

	public void setRemarkNo(String remarkNo) {
		this.remarkNo = remarkNo;
	}

	

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

	public int getRemarks() {
		return remarks;
	}

	public void setRemarks(int remarks) {
		this.remarks = remarks;
	}

	
	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
	}

	

	public Integer getId() {
		// TODO Auto-generated method stub
		return id;
	}

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	 
	 	 
	 

}
