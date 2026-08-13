package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.util.Date;

public class MemberDataDownloadDTO {
	private boolean batch;
	private String fromDate;
    private String toDate;
	public boolean isBatch() {
		return batch;
	}
	public void setBatch(boolean batch) {
		this.batch = batch;
	}
	public String getFromDate() {
		return fromDate;
	}
	public void setFromDate(String fromDate) {
		this.fromDate = fromDate;
	}
	public String getToDate() {
		return toDate;
	}
	public void setToDate(String toDate) {
		this.toDate = toDate;
	}
	
	
}
