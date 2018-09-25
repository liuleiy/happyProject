package com.happyProject.admin.model;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "col_round_record")
public class RoundRecord {
	// 每局结算详情记录
	private String id;
	private String roomid; // 房间id
	private Integer round; // 局数
	private String dealer; // 庄家
	private List<Roles> roles = new ArrayList<Roles>();// 局数
	private Date ctime;
	private RoomResultRecord roomRecord = new RoomResultRecord();
	public RoundRecord(String id, String roomid, Integer round, String dealer, List<Roles> roles, Date ctime,
			RoomResultRecord roomRecord) {
		super();
		this.id = id;
		this.roomid = roomid;
		this.round = round;
		this.dealer = dealer;
		this.roles = roles;
		this.ctime = ctime;
		this.roomRecord = roomRecord;
	}
	public RoundRecord() {
		super();
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getRoomid() {
		return roomid;
	}
	public void setRoomid(String roomid) {
		this.roomid = roomid;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	public String getDealer() {
		return dealer;
	}
	public void setDealer(String dealer) {
		this.dealer = dealer;
	}
	public List<Roles> getRoles() {
		return roles;
	}
	public void setRoles(List<Roles> roles) {
		this.roles = roles;
	}
	public Date getCtime() {
		return ctime;
	}
	public void setCtime(Date ctime) {
		this.ctime = ctime;
	}
	public RoomResultRecord getRoomRecord() {
		return roomRecord;
	}
	public void setRoomRecord(RoomResultRecord roomRecord) {
		this.roomRecord = roomRecord;
	}
	@Override
	public String toString() {
		return "RoundRecord [id=" + id + ", roomid=" + roomid + ", round=" + round + ", dealer=" + dealer + ", roles="
				+ roles + ", ctime=" + ctime + ", roomRecord=" + roomRecord + "]";
	}
	
}
