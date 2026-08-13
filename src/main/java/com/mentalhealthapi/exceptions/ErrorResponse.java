package com.mentalhealthapi.exceptions;

public class ErrorResponse {
    private int code;
    private String error;
   
    public ErrorResponse(int code, String error) {
        this.setCode(code);
        this.setError(error);
    }

	public int getCode() {
		return code;
	}

	public void setCode(int code) {
		this.code = code;
	}

	public String getError() {
		return error;
	}

	public void setError(String error) {
		this.error = error;
	}
}
