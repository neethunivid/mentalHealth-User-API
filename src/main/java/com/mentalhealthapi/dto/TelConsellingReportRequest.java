package com.mentalhealthapi.dto;

public class TelConsellingReportRequest {
    private int year;
    private Integer month; // Optional month filter

    // Getters and setters
    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    public Integer getMonth() {
        return month;
    }

    public void setMonth(Integer month) {
        this.month = month;
    }
}
