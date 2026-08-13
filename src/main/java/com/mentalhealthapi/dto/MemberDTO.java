package com.mentalhealthapi.dto;

import com.mentalhealthapi.service.SortDirection;

public class MemberDTO {
	private String name;
    private String memberno;
    private String email_pc;
    private Integer sex;
    private Integer job;
    private Integer Status;
    private Integer ngMember;
    private Boolean withdrawList=false;
    private Integer pageNumber=0;
    private String memberid;
    private Integer pageSize=10;
    private String sortField;
    private SortDirection sortDirection;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Boolean getWithdrawList() {
		return withdrawList;
	}
	public void setWithdrawList(boolean withdrawList) {
		this.withdrawList = withdrawList;
	}
	public String getMemberno() {
		return memberno;
	}
	public void setMemberno(String memberno) {
		this.memberno = memberno;
	}
	
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
	public String getMemberid() {
		return memberid;
	}
	public void setMemberid(String memberid) {
		this.memberid = memberid;
	}
	public String getEmail_pc() {
		return email_pc;
	}
	public void setEmail_pc(String email_pc) {
		this.email_pc = email_pc;
	}
	public Integer getSex() {
		return sex;
	}
	public void setSex(Integer sex) {
		this.sex = sex;
	}
	public Integer getJob() {
		return job;
	}
	public void setJob(Integer job) {
		this.job = job;
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
	public Integer getStatus() {
		return Status;
	}
	public void setStatus(Integer status) {
		Status = status;
	}
	public Integer getNgMember() {
		return ngMember;
	}
	public void setNgMember(Integer ng) {
		ngMember = ng;
	}
   
}
