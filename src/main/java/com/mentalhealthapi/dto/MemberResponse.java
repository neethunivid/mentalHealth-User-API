package com.mentalhealthapi.dto;

import java.util.List;
import java.util.Map;

public class MemberResponse {
    private Map<String, Object> member;
   
    private List<Map<String, Object>> remarks;

	public Map<String, Object> getMember() {
		return member;
	}

	public void setMember(Map<String, Object> member) {
		this.member = member;
	}

	public List<Map<String, Object>> getRemarks() {
		return remarks;
	}

	public void setRemarks(List<Map<String, Object>> remarks) {
		this.remarks = remarks;
	}
   
    
    
	
}

