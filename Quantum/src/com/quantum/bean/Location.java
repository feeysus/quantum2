package com.quantum.bean;

import java.util.Date;

import com.lidroid.xutils.db.annotation.Column;
import com.lidroid.xutils.db.annotation.Table;

@Table(name = "location")
public class Location extends EntityBase {
	@Column(column = "lat")
	private double lat;
	@Column(column = "lng")
	private double lng;
	@Column(column = "radius")
	private double radius;
	@Column(column = "adds")
	private String adds;
	@Column(column = "time")
    private Date time;

	public double getLat() {
		return lat;
	}

	public Date getTime() {
		return time;
	}

	public void setTime(Date time) {
		this.time = time;
	}

	public void setLat(double lat) {
		this.lat = lat;
	}

	public double getLng() {
		return lng;
	}

	public void setLng(double lng) {
		this.lng = lng;
	}

	public double getRadius() {
		return radius;
	}

	public void setRadius(double radius) {
		this.radius = radius;
	}

	public String getAdds() {
		return adds;
	}

	public void setAdds(String adds) {
		this.adds = adds;
	}
}
