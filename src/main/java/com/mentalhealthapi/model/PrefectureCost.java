package com.mentalhealthapi.model;
import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="prefecture_cost")
public class PrefectureCost {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	@Column(name="prefecture_id")
	private int prefectureId;
	
	@Column(name="mail_cost",precision = 10, scale = 0)
    private BigDecimal mailCost;
	
	@Column(name="daibiki_cost",precision = 10, scale = 0)
    private BigDecimal daibikiCost;

	public PrefectureCost(int prefectureId, BigDecimal mailCost, BigDecimal daibikiCost) {
		super();
		this.prefectureId = prefectureId;
		this.mailCost = mailCost;
		this.daibikiCost = daibikiCost;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getPrefectureId() {
		return prefectureId;
	}

	public void setPrefectureId(int prefectureId) {
		this.prefectureId = prefectureId;
	}

	public BigDecimal getMailCost() {
		return mailCost;
	}

	public void setMailCost(BigDecimal mailCost) {
		this.mailCost = mailCost;
	}

	public BigDecimal getDaibikiCost() {
		return daibikiCost;
	}

	public void setDaibikiCost(BigDecimal daibikiCost) {
		this.daibikiCost = daibikiCost;
	}
	
	

}
