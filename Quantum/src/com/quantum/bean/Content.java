package com.quantum.bean;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "content")
public class Content extends EntityBase {
	@Column(column = "type")
	private String type;
	@Column(column = "mode")
	private String mode;
	@Column(column = "url")
	private String url;
	@Column(column = "content")
	private String content;
	@Column(column = "time")
	private Date time;
	@Column(column = "autoUpdate")
	private Boolean autoUpdate;

	public Boolean getAutoUpdate() {
		return autoUpdate;
	}

	public void setAutoUpdate(Boolean autoUpdate) {
		this.autoUpdate = autoUpdate;
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

	@Column(column = "rate")
	private String rate;

	public String getContent() {
		return content;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
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

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
}
