package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Column;

@Entity
@Table(name = "diagnose_check")
public class DiagnoseCheck {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;

	@ManyToOne
	@JoinColumn(name = "check_sheet_type")
	private ChecksheetType checkSheetType;
	@Column(name = "user_name", columnDefinition = "VARCHAR(50)")
	private String userName;
	@Column(name = "user_email", columnDefinition = "VARCHAR(50)")
	private String userEmail;
	@Column(name = "mail_send_status", columnDefinition = "int")
	private int mailSendStatus;
	@Column(name = "mail_send_date")
	private LocalDateTime mailSendDate;
	@Column(name = "device_info", columnDefinition = "VARCHAR(2000)")
	private String deviceInfo;
	@Column(name = "diagnose_result", columnDefinition = "TEXT")
	private String diagonoseResult;
	@Column(name = "check_sheet_content", columnDefinition = "TEXT")
	private String checkSheetContent;
	@Column(name = "received_date")
	private LocalDateTime receivedDate;
	@Column(name = "is_archived", columnDefinition = "tinyint")
	private byte isArchived;
	@Column(name = "archive_date")
	private LocalDateTime archiveDate;
	@Column(name = "talk_type", columnDefinition = "tinyint")
	private byte talkType;
	@Column(name = "is_deleted", columnDefinition = "int")
	private int isDeleted;
	@Column(name = "fin_year")
	private int finYear;
	@Column(name = "is_smartphone", columnDefinition = "tinyint")
	private byte isSmartphone;
	@Column(name = "deleted_date")
	private LocalDateTime deletedDate;
	public DiagnoseCheck() {}
	public DiagnoseCheck(ChecksheetType checkSheetType, String userName, String userEmail, byte mailSendStatus,
			LocalDateTime mailSendDate, String deviceInfo, String diagonoseResult, String checkSheetContent,
			LocalDateTime receivedDate, byte isArchived, LocalDateTime archiveDate, byte talkType, byte isDeleted,
			int finYear, byte isSmartphone, LocalDateTime deletedDate) {
		super();
		this.checkSheetType = checkSheetType;
		this.userName = userName;
		this.userEmail = userEmail;
		this.mailSendStatus = mailSendStatus;
		this.mailSendDate = mailSendDate;
		this.deviceInfo = deviceInfo;
		this.diagonoseResult = diagonoseResult;
		this.checkSheetContent = checkSheetContent;
		this.receivedDate = receivedDate;
		this.isArchived = isArchived;
		this.archiveDate = archiveDate;
		this.talkType = talkType;
		this.isDeleted = isDeleted;
		this.finYear = finYear;
		this.isSmartphone = isSmartphone;
		this.deletedDate = deletedDate;
	}

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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getUserEmail() {
		return userEmail;
	}

	public void setUserEmail(String userEmail) {
		this.userEmail = userEmail;
	}

	public int getMailSendStatus() {
		return mailSendStatus;
	}

	public void setMailSendStatus(int mailSendStatus) {
		this.mailSendStatus = mailSendStatus;
	}

	public LocalDateTime getMailSendDate() {
		return mailSendDate;
	}

	public void setMailSendDate(LocalDateTime mailSendDate) {
		this.mailSendDate = mailSendDate;
	}

	public String getDeviceInfo() {
		return deviceInfo;
	}

	public void setDeviceInfo(String deviceInfo) {
		this.deviceInfo = deviceInfo;
	}

	public String getDiagonoseResult() {
		return diagonoseResult;
	}

	public void setDiagonoseResult(String diagonoseResult) {
		this.diagonoseResult = diagonoseResult;
	}

	public String getCheckSheetContent() {
		return checkSheetContent;
	}

	public void setCheckSheetContent(String checkSheetContent) {
		this.checkSheetContent = checkSheetContent;
	}

	public LocalDateTime getReceivedDate() {
		return receivedDate;
	}

	public void setReceivedDate(LocalDateTime receivedDate) {
		this.receivedDate = receivedDate;
	}

	public byte getIsArchived() {
		return isArchived;
	}

	public void setIsArchived(byte isArchived) {
		this.isArchived = isArchived;
	}

	public LocalDateTime getArchiveDate() {
		return archiveDate;
	}

	public void setArchiveDate(LocalDateTime archiveDate) {
		this.archiveDate = archiveDate;
	}

	public byte getTalkType() {
		return talkType;
	}

	public void setTalkType(byte talkType) {
		this.talkType = talkType;
	}

	public int getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}

	public int getFinYear() {
		return finYear;
	}

	public void setFinYear(int finYear) {
		this.finYear = finYear;
	}

	public byte getIsSmartphone() {
		return isSmartphone;
	}

	public void setIsSmartphone(byte isSmartphone) {
		this.isSmartphone = isSmartphone;
	}

	public LocalDateTime getDeletedDate() {
		return deletedDate;
	}

	public void setDeletedDate(LocalDateTime deletedDate) {
		this.deletedDate = deletedDate;
	}

}
