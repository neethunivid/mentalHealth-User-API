package com.mentalhealthapi.dto;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public class TelCounsellingSearchDTO {
    private String counsellor;
    private String gender;
    private List<String> ages;
    private String name;
    private List<String> mainIssues;
    private String userType;
    private LocalDate fromDate;
    private LocalDate toDate;
    private Integer pageNumber = 1;
    private Integer pageSize = 10;

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


    public String getCounsellor() {
        return counsellor;
    }

    public void setCounsellor(String counsellor) {
        this.counsellor = counsellor;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public List<String> getAges() {
        return ages;
    }

    public void setAges(List<String> ages) {
        this.ages = ages;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getMainIssues() {
        return mainIssues;
    }

    public void setMainIssues(List<String> mainIssues) {
        this.mainIssues = mainIssues;
    }

    public String getUserType() {
        return userType;
    }

    public void setUserType(String userType) {
        this.userType = userType;
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
}
