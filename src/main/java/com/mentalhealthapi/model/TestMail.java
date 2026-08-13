package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="test_mail")
public class TestMail {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	 @Column(columnDefinition = "TEXT")
	    private String text;

	 @Column(columnDefinition = "VARCHAR(255)")
	    private String title;

	public TestMail(String text, String title) {
		super();
		this.text = text;
		this.title = title;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
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
	 
	    

}
