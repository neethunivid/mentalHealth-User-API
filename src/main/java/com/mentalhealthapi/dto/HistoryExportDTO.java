package com.mentalhealthapi.dto;

public class HistoryExportDTO {
	    private boolean applicationDetails;
	    private boolean applicationHistory;
	    private int year;

	    public boolean isApplicationDetails() {
	        return applicationDetails;
	    }

	    public void setApplicationDetails(boolean applicationDetails) {
	        this.applicationDetails = applicationDetails;
	    }

	    public boolean isApplicationHistory() {
	        return applicationHistory;
	    }

	    public void setApplicationHistory(boolean applicationHistory) {
	        this.applicationHistory = applicationHistory;
	    }

	    public int getYear() {
	        return year;
	    }

	    public void setYear(int year) {
	        this.year = year;
	    }

}
