package com.github.devjn.simpleblogserver.data;

import java.util.Date;

import io.objectbox.annotation.Entity;
import io.objectbox.annotation.Id;

@Entity
public class Post {
	
	@Id
	long id;

	String title;
	String description;
	
	Date creationDate;
	
	public Post() {
		super();
	}

	public Post(String title, String description) {
		super();
		this.title = title;
		this.description = description;
		this.creationDate = new Date();
	}
	
	
	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public String getDescription() {
		return description;
	}

	public Date getCreationDate() {
		return creationDate;
	}

}
