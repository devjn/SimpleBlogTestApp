package com.github.devjn.simpleblogclient.data;

import java.util.Date;

public class Post {

	private long id;

	private String title;
	private String description;

	private Date creationDate;

	public Post() {
		super();
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
