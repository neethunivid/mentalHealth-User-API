package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="diagnose_mail_magazine")
@JsonIgnoreProperties(ignoreUnknown = true)
public class DiagnoseMailMagazine {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "check_sheet_type")
	private ChecksheetType checkSheetType;
	@Column(name="mail_type",columnDefinition = "tinyint")
	private byte mail_type;
	@Column(columnDefinition = "VARCHAR(500)")
	private String title;
	@Column(name="job_remark",columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
	private String jobRemark;
	@Column(name="job_status",columnDefinition = "VARCHAR(255) DEFAULT 'PENDING'")
	private String jobStatus;
	private String content;
	@Column(name="send_time")
	private LocalDateTime sentTime;
	@Column(name="send_status",columnDefinition = "tinyint")
	private int sendStatus;
	@Column(name="created_on")
	private LocalDateTime createdOn;
	@Column(name="modified_on")
	private LocalDateTime modifiedOn;
	@Column(name="mag_mail_subject")
	private String magMailSubject;
	public DiagnoseMailMagazine(ChecksheetType checkSheetType, byte mail_type, String title, String content,
			LocalDateTime sentTime, byte sendStatus, LocalDateTime createdOn, LocalDateTime modifiedOn,
			String magMailSubject) {
		super();
		this.checkSheetType = checkSheetType;
		this.mail_type = mail_type;
		this.title = title;
		this.content = content;
		this.sentTime = sentTime;
		this.sendStatus = sendStatus;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
		this.magMailSubject = magMailSubject;
	}
	public DiagnoseMailMagazine() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChecksheetType getCheckSheetType() {
		return checkSheetType;
	}
	public void setCheckSheetType(ChecksheetType checkSheetType) {
		this.checkSheetType = checkSheetType;
	}
	public byte getMail_type() {
		return mail_type;
	}
	public void setMail_type(byte mail_type) {
		this.mail_type = mail_type;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public LocalDateTime getSentTime() {
		return sentTime;
	}
	public void setSentTime(LocalDateTime sentTime) {
		this.sentTime = sentTime;
	}
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getMagMailSubject() {
		return magMailSubject;
	}
	public void setMagMailSubject(String magMailSubject) {
		this.magMailSubject = magMailSubject;
	}
	public String getJobRemark() {
		return jobRemark;
	}
	public void setJobRemark(String jobRemark) {
		this.jobRemark = jobRemark;
	}
	public String getJobStatus() {
		return jobStatus;
	}
	public void setJobStatus(String jobStatus) {
		this.jobStatus = jobStatus;
	}
	
	
}
