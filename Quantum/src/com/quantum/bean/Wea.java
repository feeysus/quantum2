package com.quantum.bean;

import java.util.ArrayList;

public class Wea {
	private int error;
	private String status;
	public int getError() {
		return error;
	}

	public void setError(int error) {
		this.error = error;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getDate() {
		return date;
	}

	public void setDate(String date) {
		this.date = date;
	}
	
	private String date;
	private ArrayList<Results> results;
	
	public ArrayList<Results> getResults() {
		return results;
	}

	public void setResults(ArrayList<Results> results) {
		this.results = results;
	}

	public class Results {
		public String getCurrentCity() {
			return currentCity;
		}
		public void setCurrentCity(String currentCity) {
			this.currentCity = currentCity;
		}

		public ArrayList<Index> getIndex() {
			return index;
		}
		public void setIndex(ArrayList<Index> index) {
			this.index = index;
		}
		public String getPm25() {
			return pm25;
		}
		public void setPm25(String pm25) {
			this.pm25 = pm25;
		}
		public ArrayList<Data> getWeather_data() {
			return weather_data;
		}
		public void setWeather_data(ArrayList<Data> weather_data) {
			this.weather_data = weather_data;
		}
		private String currentCity;
		private ArrayList<Index> index;
		private ArrayList<Data> weather_data;
		private String pm25;
	}

	public class Index {
		private String title;
		private String zs;
		public String getTitle() {
			return title;
		}
		public void setTitle(String title) {
			this.title = title;
		}
		public String getZs() {
			return zs;
		}
		public void setZs(String zs) {
			this.zs = zs;
		}
		public String getTipt() {
			return tipt;
		}
		public void setTipt(String tipt) {
			this.tipt = tipt;
		}
		public String getDes() {
			return des;
		}
		public void setDes(String des) {
			this.des = des;
		}
		private String tipt;
		private String des;
	}

	public class Data {
		private String date;
		private String weather;
		private String wind;
		public String getDate() {
			return date;
		}
		public void setDate(String date) {
			this.date = date;
		}
		public String getWeather() {
			return weather;
		}
		public void setWeather(String weather) {
			this.weather = weather;
		}
		public String getWind() {
			return wind;
		}
		public void setWind(String wind) {
			this.wind = wind;
		}
		public String getTemperature() {
			return temperature;
		}
		public void setTemperature(String temperature) {
			this.temperature = temperature;
		}
		private String temperature;
	}
}

