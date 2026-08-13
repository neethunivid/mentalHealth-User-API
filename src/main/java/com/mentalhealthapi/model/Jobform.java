package com.mentalhealthapi.model;

import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="jobform")
public class Jobform {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(columnDefinition="VARCHAR(255)")
	private String name1;
	@Column(columnDefinition="VARCHAR(255)")
	private String name2 ;
	@Column(columnDefinition="VARCHAR(255)")
	private String namek;
	@Column(columnDefinition="VARCHAR(255)")
	private String namek2;
	@Column(columnDefinition="VARCHAR(255)")
	private String sex;
	@Column(columnDefinition="VARCHAR(255)")
	private String job;
	private int other;
	@Column(name="job_other",columnDefinition="VARCHAR(255)")
	private String jobOther;
	private Date dob;
	@Column(columnDefinition="VARCHAR(255)")
	private String email;
	@Column(columnDefinition="VARCHAR(255)")
	private String phone;
	@Column(columnDefinition="VARCHAR(500)")
	private String resume;
	public Jobform(String name1, String name2, String namek, String namek2, String sex, String job, int other,
			String jobOther, Date dob, String email, String phone, String resume) {
		super();
		this.name1 = name1;
		this.name2 = name2;
		this.namek = namek;
		this.namek2 = namek2;
		this.sex = sex;
		this.job = job;
		this.other = other;
		this.jobOther = jobOther;
		this.dob = dob;
		this.email = email;
		this.phone = phone;
		this.resume = resume;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getName1() {
		return name1;
	}
	public void setName1(String name1) {
		this.name1 = name1;
	}
	public String getName2() {
		return name2;
	}
	public void setName2(String name2) {
		this.name2 = name2;
	}
	public String getNamek() {
		return namek;
	}
	public void setNamek(String namek) {
		this.namek = namek;
	}
	public String getNamek2() {
		return namek2;
	}
	public void setNamek2(String namek2) {
		this.namek2 = namek2;
	}
	public String getSex() {
		return sex;
	}
	public void setSex(String sex) {
		this.sex = sex;
	}
	public String getJob() {
		return job;
	}
	public void setJob(String job) {
		this.job = job;
	}
	public int getOther() {
		return other;
	}
	public void setOther(int other) {
		this.other = other;
	}
	public String getJobOther() {
		return jobOther;
	}
	public void setJobOther(String jobOther) {
		this.jobOther = jobOther;
	}
	public Date getDob() {
		return dob;
	}
	public void setDob(Date dob) {
		this.dob = dob;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getPhone() {
		return phone;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public String getResume() {
		return resume;
	}
	public void setResume(String resume) {
		this.resume = resume;
	}
	
	
	
	
}
