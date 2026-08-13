package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Index;
import jakarta.persistence.Table;

@Entity
@Table(name = "diagnose_talk_type", indexes = {@Index(name = "diagnose_check", columnList = "diagnose_check")})
public class DiagnoseTalkType {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="diagnose_check")
	private int diagnoseCheck;
	@Column(name="talk_type")
	private int talkType;
	public DiagnoseTalkType(int diagnoseCheck, int talkType) {
		super();
		this.diagnoseCheck = diagnoseCheck;
		this.talkType = talkType;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getDiagnoseCheck() {
		return diagnoseCheck;
	}
	public void setDiagnoseCheck(int diagnoseCheck) {
		this.diagnoseCheck = diagnoseCheck;
	}
	public int getTalkType() {
		return talkType;
	}
	public void setTalkType(int talkType) {
		this.talkType = talkType;
	}
	
}
