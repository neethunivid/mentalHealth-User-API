package com.mentalhealthapi.dto;

import java.sql.Date;
import java.time.LocalDateTime;

public class MemberRemarkDTO {
	private String type;
	private Boolean add;
	private Boolean confirm;
	private Integer id;
	private String text;
	private String title;
	private LocalDateTime date;
	private int page;
	private int size;
	
	public int getPage() {
		return page;
	}

	public void setPage(int page) {
		this.page = page;
	}

	public int getSize() {
		return size;
	}

	public void setSize(int size) {
		this.size = size;
	}

	public MemberRemarkDTO() {
        // Default constructor
    }

    public MemberRemarkDTO(String data) {
        // Constructor with String argument
        // Initialize the object using the data parameter
    }

    // Alternatively, you can use a factory method
    public static MemberRemarkDTO fromString(String data) {
        // Create a new MemberRemarkDTO object and initialize it using the data parameter
        MemberRemarkDTO memberRemarkDTO = new MemberRemarkDTO();
        // Initialize the object using the data parameter
        return memberRemarkDTO;
    }

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Boolean getAdd() {
		return add;
	}

	public void setAdd(Boolean add) {
		this.add = add;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Boolean getConfirm() {
		return confirm;
	}

	public void setConfirm(Boolean confirm) {
		this.confirm = confirm;
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

	public LocalDateTime getDate() {
		return date;
	}

	public void setDate(LocalDateTime date) {
		this.date = date;
	}

	

}
