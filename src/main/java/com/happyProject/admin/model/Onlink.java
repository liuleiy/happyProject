package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="tt_onlink")
public class Onlink {
	private String id;
	private String time;
	private Integer number;
	private Date ctime;
	public Onlink(String id, String time, Integer number, Date ctime) {
		super();
		this.id = id;
		this.time = time;
		this.number = number;
		this.ctime = ctime;
	}
	public Onlink() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getTime() {
		return time;
	}
	public void setTime(String time) {
		this.time = time;
	}
	public Integer getNumber() {
		return number;
	}
	public void setNumber(Integer number) {
		this.number = number;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "Onlink [id=" + id + ", time=" + time + ", number=" + number + ", ctime=" + ctime + "]";
	}
	
}
