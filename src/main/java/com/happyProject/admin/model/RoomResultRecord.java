package com.happyProject.admin.model;

import java.util.Date;

import org.springframework.data.mongodb.core.mapping.Document;
/**
 * 个人房间结果记录
 * @author Administrator
 *
 */
@Document(collection="col_role_record")
public class RoomResultRecord {
	
		private String id;
		private String roomid;//房间id
		private Integer gtype;
		private String userid;//玩家id
		private String nickname;
		private String photo;
		private Integer score;//输赢数量
		private Integer rest; //剩余
		private Integer joins; //参与局数
		private Date ctime;
		public RoomResultRecord(String id, String roomid, Integer gtype, String userid, String nickname, String photo,
				Integer score, Integer rest, Integer joins, Date ctime) {
			super();
			this.id = id;
			this.roomid = roomid;
			this.gtype = gtype;
			this.userid = userid;
			this.nickname = nickname;
			this.photo = photo;
			this.score = score;
			this.rest = rest;
			this.joins = joins;
			this.ctime = ctime;
		}
		public RoomResultRecord() {
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
		public String getPhoto() {
			return photo;
		}
		public void setPhoto(String photo) {
			this.photo = photo;
		}
		public Integer getScore() {
			return score;
		}
		public void setScore(Integer score) {
			this.score = score;
		}
		public Integer getRest() {
			return rest;
		}
		public void setRest(Integer rest) {
			this.rest = rest;
		}
		public Integer getJoins() {
			return joins;
		}
		public void setJoins(Integer joins) {
			this.joins = joins;
		}
		public Date getCtime() {
			return ctime;
		}
		public void setCtime(Date ctime) {
			this.ctime = ctime;
		}
		@Override
		public String toString() {
			return "RoomResultRecord [id=" + id + ", roomid=" + roomid + ", gtype=" + gtype + ", userid=" + userid
					+ ", nickname=" + nickname + ", photo=" + photo + ", score=" + score + ", rest=" + rest + ", joins="
					+ joins + ", ctime=" + ctime + "]";
		}
		
		
}
