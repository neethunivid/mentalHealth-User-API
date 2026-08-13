package com.mentalhealthapi.dto;

import java.util.Date;
import java.util.List;

public class RemarkResponse {
    private Integer id;
    private String remarkNo;
    private String name;
    private String emailPc;
    private String title;
    private String text;
    private Integer remark;
    private Date date;
    private List<RemarkResponse> remarks;
	
	public String getRemarkNo() {
		return remarkNo;
	}
	public void setRemarkNo(String remarkNo) {
		this.remarkNo = remarkNo;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmailPc() {
		return emailPc;
	}
	public void setEmailPc(String emailPc) {
		this.emailPc = emailPc;
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
	public Integer getRemark() {
		return remark;
	}
	public void setRemark(Integer remark) {
		this.remark = remark;
	}
	
	public List<RemarkResponse> getRemarks() {
		return remarks;
	}
	public void setRemarks(List<RemarkResponse> remarks) {
		this.remarks = remarks;
	}
	public Date getDate() {
		return date;
	}
	public void setDate(Date date) {
		this.date = date;
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}

}
