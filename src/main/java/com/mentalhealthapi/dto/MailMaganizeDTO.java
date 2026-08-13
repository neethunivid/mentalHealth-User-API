package com.mentalhealthapi.dto;

public class MailMaganizeDTO {
	private Byte non;
	private Integer mailmagId;
	private String type;
	private Integer pageNumber=0;
	private Integer pageSize=10;
	public Byte getNon() {
		return non;
	}

	public void setNon(Byte non) {
		this.non = non;
	}

	public Integer getMailmagId() {
		return mailmagId;
	}

	public void setMailmagId(Integer mailmagId) {
		this.mailmagId = mailmagId;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

}
