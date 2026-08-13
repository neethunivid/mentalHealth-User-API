package com.mentalhealthapi.dto;

import java.util.List;

public class CheckSheetTemplateDTO {

	
	private String templateContent;
	private String templateTitle;
	private int templateType;
	public String getTemplateContent() {
		return templateContent;
	}
	public void setTemplateContent(String templateContent) {
		this.templateContent = templateContent;
	}
	public String getTemplateTitle() {
		return templateTitle;
	}
	public void setTemplateTitle(String templateTitle) {
		this.templateTitle = templateTitle;
	}
	public int getTemplateType() {
		return templateType;
	}
	public void setTemplateType(int templateType) {
		this.templateType = templateType;
	}

	
}
