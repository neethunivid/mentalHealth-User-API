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
import jakarta.persistence.Transient;

@Entity
@Table(name="checksheet_template")
public class ChecksheetTemplate {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@ManyToOne
    @JoinColumn(name = "checksheet_type")
    private ChecksheetType checksheetType; 
	@Column(name ="text_pc",columnDefinition = "TEXT")
	private String textPc;
	@Column(name ="text_mobile",columnDefinition = "TEXT")
	private String textMobile;
	private LocalDateTime modifiedOn;
	@Column(name = "template_title",columnDefinition="VARCHAR(255)")
	private String templateTitle;
	@Column(name = "template_content")
	private String templateContent;
	@Column(name = "template_type",columnDefinition = "tinyint")
	private byte templateType;
	@Column(name = "created_on")
	private LocalDateTime createdOn;
	@Column(name = "is_deleted",columnDefinition = "tinyint")
	private byte isDeleted;
	@Column(name = "is_default",columnDefinition = "tinyint")
	private byte isDefault;
	@Transient // This annotation tells Hibernate to ignore this field for database mapping
	private Integer checksheetId;
	 
	public Integer getChecksheetId() {
		return checksheetId;
	}
	public void setChecksheetId(Integer checksheetId) {
		this.checksheetId = checksheetId;
	}
	public ChecksheetTemplate() {};
	public ChecksheetTemplate(ChecksheetType checksheetType, String textPc, String textMobile, LocalDateTime modifiedOn,
			String templateTitle, String templateContent, byte templateType, LocalDateTime createdOn, byte isDeleted,
			byte isDefault) {
		super();
		this.checksheetType = checksheetType;
		this.textPc = textPc;
		this.textMobile = textMobile;
		this.modifiedOn = modifiedOn;
		this.templateTitle = templateTitle;
		this.templateContent = templateContent;
		this.templateType = templateType;
		this.createdOn = createdOn;
		this.isDeleted = isDeleted;
		this.isDefault = isDefault;
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public ChecksheetType getChecksheetType() {
		return checksheetType;
	}
	public void setChecksheetType(ChecksheetType checksheetType) {
		this.checksheetType = checksheetType;
	}
	public String getTextPc() {
		return textPc;
	}
	public void setTextPc(String textPc) {
		this.textPc = textPc;
	}
	public String getTextMobile() {
		return textMobile;
	}
	public void setTextMobile(String textMobile) {
		this.textMobile = textMobile;
	}
	public LocalDateTime getModifiedOn() {
		return modifiedOn;
	}
	public void setModifiedOn(LocalDateTime modifiedOn) {
		this.modifiedOn = modifiedOn;
	}
	public String getTemplateTitle() {
		return templateTitle;
	}
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public byte getTemplateType() {
		return templateType;
	}
	public void setTemplateType(byte templateType) {
		this.templateType = templateType;
	}
	public LocalDateTime getCreatedOn() {
		return createdOn;
	}
	public void setCreatedOn(LocalDateTime createdOn) {
		this.createdOn = createdOn;
	}
	public byte getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(byte isDeleted) {
		this.isDeleted = isDeleted;
	}
	public byte getIsDefault() {
		return isDefault;
	}
	public void setIsDefault(byte isDefault) {
		this.isDefault = isDefault;
	}
	
}
