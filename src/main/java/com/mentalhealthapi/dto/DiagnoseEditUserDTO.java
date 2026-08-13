package com.mentalhealthapi.dto;

import java.util.List;

public class DiagnoseEditUserDTO {
	private List<Integer> checkIds;
	private int diagnoseMailMagazineId;

	public int getDiagnoseMailMagazineId() {
		return diagnoseMailMagazineId;
	}
	public void setDiagnoseMailMagazineId(int diagnoseMailMagazineId) {
		this.diagnoseMailMagazineId = diagnoseMailMagazineId;
	}
	public List<Integer> getCheckIds() {
		return checkIds;
	}
	public void setCheckIds(List<Integer> checkIds) {
		this.checkIds = checkIds;
	}
	
}
