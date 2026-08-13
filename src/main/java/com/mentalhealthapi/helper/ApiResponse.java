package com.mentalhealthapi.helper;

public class ApiResponse<T> {
    private int code;
    private String error;
    private Object data;
    
    public ApiResponse(int code, String error, T data) {
        this.setCode(code);
        this.setError(error);
        this.setData(data);
    }

    public ApiResponse() {
     
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

	public Object getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
    
   
}