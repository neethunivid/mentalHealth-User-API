package com.mentalhealthapi.model;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name="diagnose_mail_users")
public class DiagnoseMailUsers {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@ManyToOne
    @JoinColumn(name = "diagnose_mail_magazine", referencedColumnName = "id")
    private DiagnoseMailMagazine diagnoseMailMagazine;

    public DiagnoseMailMagazine getDiagnoseMailMagazine() {
		return diagnoseMailMagazine;
	}
	public void setDiagnoseMailMagazine(DiagnoseMailMagazine diagnoseMailMagazine) {
		this.diagnoseMailMagazine = diagnoseMailMagazine;
	}
	public DiagnoseCheck getDiagnoseCheck() {
		return diagnoseCheck;
	}
	public void setDiagnoseCheck(DiagnoseCheck diagnoseCheck) {
		this.diagnoseCheck = diagnoseCheck;
	}
	@ManyToOne
    @JoinColumn(name = "diagnose_check", referencedColumnName = "id")
    private DiagnoseCheck diagnoseCheck;
	@Column(name="send_time")
	private LocalDateTime sendTime;
	@Column(name="send_status",columnDefinition = "tinyint")
	private int sendStatus;
	public DiagnoseMailUsers(DiagnoseMailMagazine diagnoseMailMagazine, DiagnoseCheck diagnoseCheck, LocalDateTime sendTime, byte sendStatus) {
		super();
		this.diagnoseMailMagazine = diagnoseMailMagazine;
		this.diagnoseCheck = diagnoseCheck;
		this.sendTime = sendTime;
		this.sendStatus = sendStatus;
	}
	public DiagnoseMailUsers() {}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	
	public LocalDateTime getSendTime() {
		return sendTime;
	}
	public void setSendTime(LocalDateTime sendTime) {
		this.sendTime = sendTime;
	}
	public int getSendStatus() {
		return sendStatus;
	}
	public void setSendStatus(int sendStatus) {
		this.sendStatus = sendStatus;
	}
	
	
}
