package com.mentalhealthapi.dto;

public class RemarkDTO {

private int remarkId;
private	int parentRemark;
private int type;
private String text;
private String title;
private int memberId;
private String remarkTitle;

public int getType() {
	return type;
}
public void setType(int type) {
	this.type = type;
}
public String getText() {
	return text;
}
public void setText(String text) {
	this.text = text;
}
public String getTitle() {
	return title;
}
public void setTitle(String title) {
	this.title = title;
}
public int getMemberId() {
	return memberId;
}
public void setMemberId(int memberId) {
	this.memberId = memberId;
}
public String getRemarkTitle() {
	return remarkTitle;
}
public void setRemarkTitle(String remarkTitle) {
	this.remarkTitle = remarkTitle;
}
public int getParentRemark() {
	return parentRemark;
}
public void setParentRemark(int parentRemark) {
	this.parentRemark = parentRemark;
}
public int getRemarkId() {
	return remarkId;
}
public void setRemarkId(int remarkId) {
	this.remarkId = remarkId;
}



}
