package com.mentalhealthapi.helper;

public class ApiListResponse<T> {
    private int code;
    private String error;
    private Object data;
    private long count;
    public ApiListResponse(int code, String error, T data , long count) {
        this.setCode(code);
        this.setError(error);
        this.setData(data);
        this.setCount(count);
    }

    

	public ApiListResponse() {
     
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



	public long getCount() {
		return count;
	}



	public void setCount(long count) {
		this.count = count;
	}



	
    
   
}