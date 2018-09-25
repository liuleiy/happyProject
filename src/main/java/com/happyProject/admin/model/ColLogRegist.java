package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;

/**
 * 玩家注册记录
 * @author Administrator
 *
 */
@Document(collection="col_log_regist")
public class ColLogRegist {
	private String id;
	private String userid;
	private String nickname;
	private String ip;
	private Date day_stamp;
	private Integer day_date;
	private Date ctime;
	private Integer atype;
	public ColLogRegist(String id, String userid, String nickname, String ip, Date day_stamp, Integer day_date,
			Date ctime, Integer atype) {
		super();
		this.id = id;
		this.userid = userid;
		this.nickname = nickname;
		this.ip = ip;
		this.day_stamp = day_stamp;
		this.day_date = day_date;
		this.ctime = ctime;
		this.atype = atype;
	}
	public ColLogRegist() {
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
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public Date getDay_stamp() {
		return day_stamp;
	}
	public void setDay_stamp(Date day_stamp) {
		this.day_stamp = day_stamp;
	}
	public Integer getDay_date() {
		return day_date;
	}
	public void setDay_date(Integer day_date) {
		this.day_date = day_date;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public Integer getAtype() {
		return atype;
	}
	public void setAtype(Integer atype) {
		this.atype = atype;
	}
	@Override
	public String toString() {
		return "ColLogRegist [id=" + id + ", userid=" + userid + ", nickname=" + nickname + ", ip=" + ip
				+ ", day_stamp=" + day_stamp + ", day_date=" + day_date + ", ctime=" + ctime + ", atype=" + atype + "]";
	}
	
}
