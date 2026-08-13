package com.mentalhealthapi.dto;

import java.util.List;

public class DiagnoseDataDTO {
	
	    private String deviceData;
	    private String diagnoseResult;
	    private String checksheetContent;
	    private String name;
	    private String email;
	    private String other;
	    private int type;
	    private List<Integer> talkType;
	    private boolean isSmartphone;
		public String getDeviceData() {
			return deviceData;
		}
		public void setDeviceData(String deviceData) {
			this.deviceData = deviceData;
		}
		public String getDiagnoseResult() {
			return diagnoseResult;
		}
		public void setDiagnoseResult(String diagnoseResult) {
			this.diagnoseResult = diagnoseResult;
		}
		public String getChecksheetContent() {
			return checksheetContent;
		}
		public void setChecksheetContent(String checksheetContent) {
			this.checksheetContent = checksheetContent;
		}
		public String getName() {
			return name;
		}
		public void setName(String name) {
			this.name = name;
		}
		public String getEmail() {
			return email;
		}
		public void setEmail(String email) {
			this.email = email;
		}
		public int getType() {
			return type;
		}
		public void setType(int type) {
			this.type = type;
		}
		public List<Integer> getTalkType() {
			return talkType;
		}
		public void setTalkType(List<Integer> talkType) {
			this.talkType = talkType;
		}
		public boolean isSmartphone() {
			return isSmartphone;
		}
		public void setSmartphone(boolean isSmartphone) {
			this.isSmartphone = isSmartphone;
		}
		public String getOther() {
			return other;
		}
		public void setOther(String other) {
			this.other = other;
		}
	    
}
