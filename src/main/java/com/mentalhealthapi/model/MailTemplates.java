package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="mail_templates")
public class MailTemplates {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	@Column(name="mail_category",columnDefinition = "VARCHAR(255)")
	 private String mailCategory;
	
	@Column(name="mail_from",columnDefinition = "VARCHAR(255)")
	 private String mailFrom;
	
	@Column(name="from_name",columnDefinition = "VARCHAR(255)")
	 private String fromName;
	
	@Column(name="mail_subject",columnDefinition = "VARCHAR(255)")
	 private String mailSubject;
	
	@Column(name="mail_body",columnDefinition = "TEXT")
    private String mailBody;
	
	@Column(columnDefinition = "TEXT")
    private String variables;

	public MailTemplates(String mailCategory, String mailFrom, String fromName, String mailSubject, String mailBody,
			String variables) {
		super();
		this.mailCategory = mailCategory;
		this.mailFrom = mailFrom;
		this.fromName = fromName;
		this.mailSubject = mailSubject;
		this.mailBody = mailBody;
		this.variables = variables;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getMailCategory() {
		return mailCategory;
	}

	public void setMailCategory(String mailCategory) {
		this.mailCategory = mailCategory;
	}

	public String getMailFrom() {
		return mailFrom;
	}

	public void setMailFrom(String mailFrom) {
		this.mailFrom = mailFrom;
	}

	public String getFromName() {
		return fromName;
	}

	public void setFromName(String fromName) {
		this.fromName = fromName;
	}

	public String getMailSubject() {
		return mailSubject;
	}

	public void setMailSubject(String mailSubject) {
		this.mailSubject = mailSubject;
	}

	public String getMailBody() {
		return mailBody;
	}

	public void setMailBody(String mailBody) {
		this.mailBody = mailBody;
	}

	public String getVariables() {
		return variables;
	}

	public void setVariables(String variables) {
		this.variables = variables;
	}
	
	
	
}
