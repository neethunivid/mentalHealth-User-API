package com.mentalhealthapi.dto;

import com.mentalhealthapi.service.SortDirection;

public class ListDTO {
	private Integer pageNumber;    
    private Integer pageSize;
    private String sortField;
    private SortDirection sortDirection;
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
	public SortDirection getSortDirection() {
		return sortDirection;
	}
	public void setSortDirection(SortDirection sortDirection) {
		this.sortDirection = sortDirection;
	}

}
