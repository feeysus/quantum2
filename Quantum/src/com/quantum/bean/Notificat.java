package com.quantum.bean;

import java.sql.Timestamp;

public class Notificat {
	private Integer id;
	private String title;
	private String content;
	private String type;
	private Timestamp tm;

	public Notificat() {

	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public Timestamp getTm() {
		return tm;
	}

	public void setTm(Timestamp tm) {
		this.tm = tm;
	}
 
}
