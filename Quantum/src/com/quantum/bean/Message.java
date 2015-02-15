package com.quantum.bean;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;
@Table(name = "message")
public class Message extends EntityBase {
   
	@Column(column = "title")
	private String title;
	@Column(column = "content")
	private Content content;
	@Column(column = "key")
	private String key;
	@Column(column = "time")
	private long time;

	public String getTitle() {
		return title;
	}

	public long getTime() {
		return time;
	}

	public Content getContent() {
		return content;
	}

	public void setContent(Content content) {
		this.content = content;
	}

	public void setTime(long time) {
		this.time = time;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

}
