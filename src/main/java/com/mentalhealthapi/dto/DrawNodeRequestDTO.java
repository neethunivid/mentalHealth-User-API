package com.mentalhealthapi.dto;

public class DrawNodeRequestDTO {
	private int page;
    private int type;
    private int rid;
    
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getType() {
		return type;
	}
	public void setType(int type) {
		this.type = type;
	}
	public int getRid() {
		return rid;
	}
	public void setRid(int rid) {
		this.rid = rid;
	}

}
