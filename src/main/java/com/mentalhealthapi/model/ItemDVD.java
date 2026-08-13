package com.mentalhealthapi.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="item_dvd")
public class ItemDVD {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="jan_code",columnDefinition="VARCHAR(50)")
	private String janCode;
	@Column(name="item_name",columnDefinition="VARCHAR(150)")
	private String itemName;
	@Column(precision = 10, scale = 2)
	private BigDecimal price;
	@Column(precision = 10, scale = 2)
	private BigDecimal tax;
	@Column(columnDefinition="VARCHAR(50)")
	private String status;
	private LocalDateTime createdOn;
	private LocalDateTime modifiedOn;
	public ItemDVD(String janCode, String itemName, BigDecimal price, BigDecimal tax, String status,
			LocalDateTime createdOn, LocalDateTime modifiedOn) {
		super();
		this.janCode = janCode;
		this.itemName = itemName;
		this.price = price;
		this.tax = tax;
		this.status = status;
		this.createdOn = createdOn;
		this.modifiedOn = modifiedOn;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public String getJanCode() {
		return janCode;
	}
	public void setJanCode(String janCode) {
		this.janCode = janCode;
	}
	public String getItemName() {
		return itemName;
	}
	public void setItemName(String itemName) {
		this.itemName = itemName;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public BigDecimal getTax() {
		return tax;
	}
	public void setTax(BigDecimal tax) {
		this.tax = tax;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
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
	
}
