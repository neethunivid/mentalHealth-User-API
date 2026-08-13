package com.mentalhealthapi.dto;

import java.util.List;

public class CheckSheetListDTO {

	
	private Integer pageNumber=1;    
    private Integer pageSize=25;
    private Integer checkSheetType;
    private int finYear=0;
    private List<Integer> diagonseCheckId;
    private Integer type=1;
    private Integer template_type;
    private String userEmail;
	
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

	public Integer getCheckSheetType() {
		return checkSheetType;
	}

	public void setCheckSheetType(Integer checkSheetType) {
		this.checkSheetType = checkSheetType;
	}

	public Integer getType() {
		return type;
	}

	public void setType(Integer type) {
		this.type = type;
	}

	public List<Integer> getDiagonseCheckId() {
		return diagonseCheckId;
	}

	public void setDiagonseCheckId(List<Integer> diagonseCheckId) {
		this.diagonseCheckId = diagonseCheckId;
	}

	

	public Integer getTemplate_type() {
		return template_type;
	}

	public void setTemplate_type(Integer template_type) {
		this.template_type = template_type;
	}

	public int getFinYear() {
		return finYear;
	}

	public void setFinYear(int finYear) {
		this.finYear = finYear;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	
}
