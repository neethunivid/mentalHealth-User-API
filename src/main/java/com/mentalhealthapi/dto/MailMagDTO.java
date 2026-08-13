package com.mentalhealthapi.dto;

import com.mentalhealthapi.service.SortDirection;

public class MailMagDTO {
	   
	private String type;
	private String email;
	private String name;
    private Integer pageNumber=0; 
    private Integer pageSize=10; 
    private String sortField; 
    private String sortOrder;
    private String listType;
    
    
    
	
	public Integer getPageNumber() {
		return pageNumber;
	}
	public void setPageNumber(Integer pageNumber) {
		this.pageNumber = pageNumber;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public String getSortField() {
		return sortField;
	}
	public void setSortField(String sortField) {
		this.sortField = sortField;
	}
	
	public String getSortOrder() {
		return sortOrder;
	}
	public void setSortOrder(String sortOrder) {
		this.sortOrder = sortOrder;
	}
	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getListType() {
		return listType;
	}
	public void setListType(String listType) {
		this.listType = listType;
	}
	

	
}
