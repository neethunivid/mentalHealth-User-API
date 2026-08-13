package com.mentalhealthapi.dto;

import com.mentalhealthapi.model.GrantRequest;
import com.mentalhealthapi.model.GrantRequest2;

public class GrantRequestCreateDTO {

	private GrantRequest grandRequest;
    private GrantRequest2 grantRequest2;
	public GrantRequest getGrandRequest() {
		return grandRequest;
	}
	public void setGrandRequest(GrantRequest grandRequest) {
		this.grandRequest = grandRequest;
	}
	public GrantRequest2 getGrantRequest2() {
		return grantRequest2;
	}
	public void setGrantRequest2(GrantRequest2 grantRequest2) {
		this.grantRequest2 = grantRequest2;
	}
	}
