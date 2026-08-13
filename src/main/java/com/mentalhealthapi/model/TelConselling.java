package com.mentalhealthapi.model;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tel_conselling")
public class TelConselling {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private LocalDate date;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String counsellor;
	
	@Column(columnDefinition = "VARCHAR(100)")
	private String gender;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String name;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String age;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String job;

	@Column(columnDefinition = "VARCHAR(250)")
	private String sessions;
	
	@Column(columnDefinition = "VARCHAR(100)")
	private String route;
	
	@Column(columnDefinition = "VARCHAR(500)")
	private String mainIssues;
	
	@Column(columnDefinition = "VARCHAR(500)")
	private String advice;
	
	@Column(columnDefinition = "TEXT")
    private String problemDetails;
	
	@Column(columnDefinition = "TEXT")
    private String adviceDetails;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice1;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice2;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice3;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice4;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice5;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice6;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String advice7;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness1;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness2;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness3;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness4;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness5;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness6;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness7;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String illness8;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String name2;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String route2;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String mainIssues2;
	
	@Column(columnDefinition = "VARCHAR(250)")
	private String userType;
	
	private int status;

	public TelConselling(LocalDate date, String counsellor, String gender, String name, String age, String job,
			String sessions, String route, String mainIssues, String advice, String problemDetails,
			String adviceDetails, String advice1, String advice2, String advice3, String advice4, String advice5,
			String advice6, String advice7, String illness1, String illness2, String illness3, String illness4, String illness5, String illness6,
			String illness7, String illness8, String name2, String route2, String mainIssues2,
			int status, String userType) {
		super();
		this.date = date;
		this.counsellor = counsellor;
		this.gender = gender;
		this.name = name;
		this.age = age;
		this.job = job;
		this.sessions = sessions;
		this.route = route;
		this.mainIssues = mainIssues;
		this.advice = advice;
		this.problemDetails = problemDetails;
		this.adviceDetails = adviceDetails;
		this.advice1 = advice1;
		this.advice2 = advice2;
		this.advice3 = advice3;
		this.advice4 = advice4;
		this.advice5 = advice5;
		this.advice6 = advice6;
		this.advice7 = advice7;
		this.illness1 = illness1;
		this.illness2 = illness2;
		this.illness3 = illness3;
		this.illness4 = illness4;
		this.illness5 = illness5;
		this.illness6 = illness6;
		this.illness7 = illness7;
		this.illness8 = illness8;
		this.name2 = name2;
		this.route2 = route2;
		this.mainIssues2 = mainIssues2;
		this.status = status;
		this.userType = userType;
	}

	public TelConselling() {
		// TODO Auto-generated constructor stub
	}

	public String getUserType() {
		return userType;
	}

	public void setUserType(String userType) {
		this.userType = userType;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public LocalDate getDate() {
		return date;
	}

	public void setDate(LocalDate date) {
		this.date = date;
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

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAge() {
		return age;
	}

	public void setAge(String age) {
		this.age = age;
	}

	public String getJob() {
		return job;
	}

	public void setJob(String job) {
		this.job = job;
	}

	public String getSessions() {
		return sessions;
	}

	public void setSessions(String sessions) {
		this.sessions = sessions;
	}

	public String getRoute() {
		return route;
	}

	public void setRoute(String route) {
		this.route = route;
	}

	public String getMainIssues() {
		return mainIssues;
	}

	public void setMainIssues(String mainIssues) {
		this.mainIssues = mainIssues;
	}

	public String getAdvice() {
		return advice;
	}

	public void setAdvice(String advice) {
		this.advice = advice;
	}

	public String getProblemDetails() {
		return problemDetails;
	}

	public void setProblemDetails(String problemDetails) {
		this.problemDetails = problemDetails;
	}

	public String getAdviceDetails() {
		return adviceDetails;
	}

	public void setAdviceDetails(String adviceDetails) {
		this.adviceDetails = adviceDetails;
	}

	public String getAdvice1() {
		return advice1;
	}

	public void setAdvice1(String advice1) {
		this.advice1 = advice1;
	}

	public String getAdvice2() {
		return advice2;
	}

	public void setAdvice2(String advice2) {
		this.advice2 = advice2;
	}

	public String getAdvice3() {
		return advice3;
	}

	public void setAdvice3(String advice3) {
		this.advice3 = advice3;
	}

	public String getAdvice4() {
		return advice4;
	}

	public void setAdvice4(String advice4) {
		this.advice4 = advice4;
	}

	public String getAdvice5() {
		return advice5;
	}

	public void setAdvice5(String advice5) {
		this.advice5 = advice5;
	}

	public String getAdvice6() {
		return advice6;
	}

	public void setAdvice6(String advice6) {
		this.advice6 = advice6;
	}

	public String getAdvice7() {
		return advice7;
	}

	public void setAdvice7(String advice7) {
		this.advice7 = advice7;
	}

	public String getIllness1() {
		return illness1;
	}

	public void setIllness1(String illness1) {
		this.illness1 = illness1;
	}

	public String getIllness2() {
		return illness2;
	}

	public void setIllness2(String illness2) {
		this.illness2 = illness2;
	}

	public String getIllness3() {
		return illness3;
	}

	public void setIllness3(String illness3) {
		this.illness3 = illness3;
	}

	public String getIllness4() {
		return illness4;
	}

	public void setIllness4(String illness4) {
		this.illness4 = illness4;
	}

	public String getIllness5() {
		return illness5;
	}

	public void setIllness5(String illness5) {
		this.illness5 = illness5;
	}

	public String getIllness6() {
		return illness6;
	}

	public void setIllness6(String illness6) {
		this.illness6 = illness6;
	}

	public String getIllness7() {
		return illness7;
	}

	public void setIllness7(String illness7) {
		this.illness7 = illness7;
	}

	public String getIllness8() {
		return illness8;
	}

	public void setIllness8(String illness8) {
		this.illness8 = illness8;
	}

	public String getName2() {
		return name2;
	}

	public void setName2(String name2) {
		this.name2 = name2;
	}

	public String getRoute2() {
		return route2;
	}

	public void setRoute2(String route2) {
		this.route2 = route2;
	}

	public String getMainIssues2() {
		return mainIssues2;
	}

	public void setMainIssues2(String mainIssues2) {
		this.mainIssues2 = mainIssues2;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}
	
	
	
	
	

}
