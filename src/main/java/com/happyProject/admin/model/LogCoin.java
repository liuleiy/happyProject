package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="col_log_coin")
public class LogCoin {
	private String id;
	private String userid;
	private String fromid;
	private Integer type;
	private Integer num;//数量
	private Integer rest;//剩余
	private Date ctime;
	public LogCoin(String id, String userid, String fromid, Integer type, Integer num, Integer rest, Date ctime) {
		super();
		this.id = id;
		this.userid = userid;
		this.fromid = fromid;
		this.type = type;
		this.num = num;
		this.rest = rest;
		this.ctime = ctime;
	}
	public LogCoin() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public String getFromid() {
		return fromid;
	}
	public void setFromid(String fromid) {
		this.fromid = fromid;
	}
	public Integer getType() {
		return type;
	}
	public void setType(Integer type) {
		this.type = type;
	}
	public Integer getNum() {
		return num;
	}
	public void setNum(Integer num) {
		this.num = num;
	}
	public Integer getRest() {
		return rest;
	}
	public void setRest(Integer rest) {
		this.rest = rest;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	@Override
	public String toString() {
		return "LogCoin [id=" + id + ", userid=" + userid + ", fromid=" + fromid + ", type=" + type + ", num=" + num
				+ ", rest=" + rest + ", ctime=" + ctime + "]";
	}
	
}
