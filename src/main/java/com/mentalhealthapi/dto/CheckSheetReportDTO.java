package com.mentalhealthapi.dto;

public class CheckSheetReportDTO {
	private int year;
	private int month;
	private String checkType;
	
	
	public String getCheckType() {
		return checkType;
	}
	public void setCheckType(String checkType) {
		this.checkType = checkType;
	}
	public int getYear() {
		return year;
	}
	public void setYear(int year) {
		this.year = year;
	}
	public int getMonth() {
		return month;
	}
	public void setMonth(int month) {
		this.month = month;
	}

}
