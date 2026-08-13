package com.mentalhealthapi.dto;

import com.mentalhealthapi.model.Member;

public class LoginResponse {
    private String token;
    private Member member;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public Member getMember() {
		return member;
	}
	public void setMember(Member member) {
		this.member = member;
	}
    
    
}