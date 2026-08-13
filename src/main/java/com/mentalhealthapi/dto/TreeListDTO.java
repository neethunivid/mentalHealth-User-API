package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TreeListDTO {
	private int id;
	 private String remarkNo;
	 private int ParentId;
	 private String title;
	 private String text;
	 private int remarks;
	 private LocalDateTime date;
	 private int memberid;
	 private String memberName;
	 private int count;
	 
	 public TreeListDTO(int id, String remarkNo, int ParentId, String title, String text, int remarks, LocalDateTime date,int memberid,String memberName,int count) {
		 super();
		this.id = id; 
		this.remarkNo=remarkNo;
		this.ParentId=ParentId;
		this.title=title;
		this.text=text;
		this.setRemarks(remarks);
		this.date=date;
		this.memberid=memberid;
		this.memberName=memberName;
		this.count=count;
		    
		}
	 
	public int getMemberid() {
		return memberid;
	}

	public void setMemberid(int memberid) {
		this.memberid = memberid;
	}

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getRemarkNo() {
		return remarkNo;
	}
	public void setRemarkNo(String remarkNo) {
		this.remarkNo = remarkNo;
	}
	public int getParentId() {
		return ParentId;
	}
	public void setParentId(int parentId) {
		ParentId = parentId;
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
	public LocalDateTime getDate() {
		return date;
	}
	public void setDate(LocalDateTime date) {
		this.date = date;
	}
	public int getCount() {
		return count;
	}
	public void setCount(int count) {
		this.count = count;
	}

}
