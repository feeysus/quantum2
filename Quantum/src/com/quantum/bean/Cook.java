package com.quantum.bean;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "cook")
public class Cook extends EntityBase{
	@Column(column = "title")
	private String title;
	@Column(column = "img")
	private String img;
	@Column(column = "img_name")
	private String img_name;
	@Column(column = "tip")
	private String tip;
	public String getImg_name() {
		return img_name;
	}
	public void setImg_name(String img_name) {
		this.img_name = img_name;
	}
	@Column(column = "info")
	private String info;
	@Column(column = "herf")
	private String herf;
	@Column(column = "tm")
	private String tm;
	@Column(column = "btitle")
	private String btitle;
	@Column(column = "time")
	private Date time;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public String getBtitle() {
		return btitle;
	}
	public void setBtitle(String btitle) {
		this.btitle = btitle;
	}
	 
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getImg() {
		return img;
	}
	public void setImg(String img) {
		this.img = img;
	}
	public String getTip() {
		return tip;
	}
	public void setTip(String tip) {
		this.tip = tip;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String getHerf() {
		return herf;
	}
	public void setHerf(String herf) {
		this.herf = herf;
	}
	public String getTm() {
		return tm;
	}
	public void setTm(String tm) {
		this.tm = tm;
	}
}
