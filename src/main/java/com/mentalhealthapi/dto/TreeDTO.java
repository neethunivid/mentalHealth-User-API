package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.util.Date;

public class TreeDTO {
	private int id;
	 private String remarkNo;
	 private int ParentId;
	 private String title;
	 private String text;
	 private int remarks;
	 private LocalDate date;

	 public TreeDTO(int id, String remarkNo, int ParentId, String title, String text, int remarks, LocalDate date) {
		 super();
		this.id = id; 
		this.remarkNo=remarkNo;
		this.ParentId=ParentId;
		this.title=title;
		this.text=text;
		this.setRemarks(remarks);
		this.date=date;
		    
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
	
	public LocalDate getDate() {
		return date;
	}
	public void setDate(LocalDate date) {
		this.date = date;
	}
	public int getParentId() {
		return ParentId;
	}
	public void setParentId(int parentId) {
		ParentId = parentId;
	}
	public int getRemarks() {
		return remarks;
	}
	public void setRemarks(int remarks) {
		this.remarks = remarks;
	}
	
}
