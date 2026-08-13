package com.mentalhealthapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name="tree")
public class Tree {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private int parent;
	
	@Column(columnDefinition = "VARCHAR(255)")
	private String title;
	
	public Tree(int parent, String title) {
		super();
		this.parent = parent;
		this.title = title;
	
	}
	public int getId() {
		return id;
	}
	public void setId(int id) {
		this.id = id;
	}
	public int getParent() {
		return parent;
	}
	public void setParent(int parent) {
		this.parent = parent;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}


}
