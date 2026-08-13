package com.mentalhealthapi.dto;

import java.time.LocalDate;

public class ReservationDTO {
	private Integer pageNumber;    
    private Integer pageSize;
    private LocalDate fromDate;    
    private LocalDate toDate;
    private String status;
    private int regionId;
    private Boolean allFilter=true;
    private String typeOfCounselling;
    
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
	
	public LocalDate getFromDate() {
		return fromDate;
	}
	public void setFromDate(LocalDate fromDate) {
		this.fromDate = fromDate;
	}
	public LocalDate getToDate() {
		return toDate;
	}
	public void setToDate(LocalDate toDate) {
		this.toDate = toDate;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public int getRegionId() {
		return regionId;
	}
	public void setRegionId(int regionId) {
		this.regionId = regionId;
	}

	public Boolean getAllFilter() {
		return allFilter;
	}
	public void setAllFilter(Boolean allFilter) {
		this.allFilter = allFilter;
	}
	public String getTypeOfCounselling() {
		return typeOfCounselling;
	}

}
