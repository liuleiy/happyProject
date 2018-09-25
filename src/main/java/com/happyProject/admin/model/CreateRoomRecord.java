package com.happyProject.admin.model;

import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection="col_room_record")
public class CreateRoomRecord {
	private String id;
	private String roomid;//房间id
	private Integer gtype;
	private Integer rtype;
	private Integer dtype;
	private String rname;//房间名称
	private Integer count;
	private Integer ante;//房间底分
	private String code;//牌局ID
	private Integer round;
	private String cid;
	private Integer ctime;
	private boolean pub;
	private Integer minimum;//房间最低限制
	private Integer maximum;//房间最高限制
	private Integer mode;
	private Integer multiple;
	public CreateRoomRecord(String id, String roomid, Integer gtype, Integer rtype, Integer dtype, String rname,
			Integer count, Integer ante, String code, Integer round, String cid, Integer ctime, boolean pub,
			Integer minimum, Integer maximum, Integer mode, Integer multiple) {
		super();
		this.id = id;
		this.roomid = roomid;
		this.gtype = gtype;
		this.rtype = rtype;
		this.dtype = dtype;
		this.rname = rname;
		this.count = count;
		this.ante = ante;
		this.code = code;
		this.round = round;
		this.cid = cid;
		this.ctime = ctime;
		this.pub = pub;
		this.minimum = minimum;
		this.maximum = maximum;
		this.mode = mode;
		this.multiple = multiple;
	}
	public CreateRoomRecord() {
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
	public Integer getGtype() {
		return gtype;
	}
	public void setGtype(Integer gtype) {
		this.gtype = gtype;
	}
	public Integer getRtype() {
		return rtype;
	}
	public void setRtype(Integer rtype) {
		this.rtype = rtype;
	}
	public Integer getDtype() {
		return dtype;
	}
	public void setDtype(Integer dtype) {
		this.dtype = dtype;
	}
	public String getRname() {
		return rname;
	}
	public void setRname(String rname) {
		this.rname = rname;
	}
	public Integer getCount() {
		return count;
	}
	public void setCount(Integer count) {
		this.count = count;
	}
	public Integer getAnte() {
		return ante;
	}
	public void setAnte(Integer ante) {
		this.ante = ante;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public Integer getRound() {
		return round;
	}
	public void setRound(Integer round) {
		this.round = round;
	}
	public String getCid() {
		return cid;
	}
	public void setCid(String cid) {
		this.cid = cid;
	}
	public Integer getCtime() {
		return ctime;
	}
	public void setCtime(Integer ctime) {
		this.ctime = ctime;
	}
	public boolean isPub() {
		return pub;
	}
	public void setPub(boolean pub) {
		this.pub = pub;
	}
	public Integer getMinimum() {
		return minimum;
	}
	public void setMinimum(Integer minimum) {
		this.minimum = minimum;
	}
	public Integer getMaximum() {
		return maximum;
	}
	public void setMaximum(Integer maximum) {
		this.maximum = maximum;
	}
	public Integer getMode() {
		return mode;
	}
	public void setMode(Integer mode) {
		this.mode = mode;
	}
	public Integer getMultiple() {
		return multiple;
	}
	public void setMultiple(Integer multiple) {
		this.multiple = multiple;
	}
	@Override
	public String toString() {
		return "CreateRoomRecord [id=" + id + ", roomid=" + roomid + ", gtype=" + gtype + ", rtype=" + rtype
				+ ", dtype=" + dtype + ", rname=" + rname + ", count=" + count + ", ante=" + ante + ", code=" + code
				+ ", round=" + round + ", cid=" + cid + ", ctime=" + ctime + ", pub=" + pub + ", minimum=" + minimum
				+ ", maximum=" + maximum + ", mode=" + mode + ", multiple=" + multiple + "]";
	}
	
}
