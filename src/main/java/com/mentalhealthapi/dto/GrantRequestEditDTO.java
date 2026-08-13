package com.mentalhealthapi.dto;

import java.time.LocalDate;

public class GrantRequestEditDTO {
	private String remark;
	private LocalDate dateCreated;
	private String newCont;
	private String classification;
	private String institution;
	private String dept;
	private String representative;
	private String age;
	private String recomdPernInst;
	private String recomdPernJob;
	private String recomdPernFullname;
	private String researchName;
	
	
	

	public LocalDate getDateCreated() {
		return dateCreated;
	}

	public void setDateCreated(LocalDate dateCreated) {
		this.dateCreated = dateCreated;
	}
	
	public String getClassification() {
		return classification;
	}

	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getInstitution() {
		return institution;
	}

	public void setInstitution(String institution) {
		this.institution = institution;
	}

	public String getDept() {
		return dept;
	}

	public void setDept(String dept) {
		this.dept = dept;
	}

	public String getRepresentative() {
		return representative;
	}

	public void setRepresentative(String representative) {
		this.representative = representative;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getRecomdPernInst() {
		return recomdPernInst;
	}

	public void setRecomdPernInst(String recomdPernInst) {
		this.recomdPernInst = recomdPernInst;
	}

	public String getRecomdPernJob() {
		return recomdPernJob;
	}

	public void setRecomdPernJob(String recomdPernJob) {
		this.recomdPernJob = recomdPernJob;
	}

	public String getRecomdPernFullname() {
		return recomdPernFullname;
	}

	public void setRecomdPernFullname(String recomdPernFullname) {
		this.recomdPernFullname = recomdPernFullname;
	}

	public String getResearchName() {
		return researchName;
	}

	public void setResearchName(String researchName) {
		this.researchName = researchName;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getNewCont() {
		return newCont;
	}

	public void setNewCont(String newCont) {
		this.newCont = newCont;
	}
	
	

}
