package com.mentalhealthapi.dto;

import java.time.LocalDateTime;

import com.mentalhealthapi.model.Remarks;

public class RemarkResponseDTO {	
	    private Integer id;
	    private String remarkNo;
	    private String title;
	    private String text;
	    private LocalDateTime date;
	    private Integer status;
	    private Integer member;
	    private Integer remarks;
	    private String memberName;
	    private Integer memberSex;

	    public RemarkResponseDTO(Integer id, String remarkNo, String title, String text, LocalDateTime date, Integer status,Integer member,Integer remarks, String memberName, Integer memberSex) {
	        this.id = id;
	        this.remarkNo = remarkNo;
	        this.title = title;
	        this.text = text;
	        this.date = date;
	        this.status = status;
	        this.member=member;
	        this.remarks=remarks;
	        this.memberName = memberName;
	        this.memberSex = memberSex;
	    }
    
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public Integer getMemberSex() {
		return memberSex;
	}

	public void setMemberSex(Integer memberSex) {
		this.memberSex = memberSex;
	}

	public Integer getMember() {
		return member;
	}

	public void setMember(Integer member) {
		this.member = member;
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

}
