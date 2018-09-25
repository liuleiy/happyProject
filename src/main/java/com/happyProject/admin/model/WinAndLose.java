package com.happyProject.admin.model;

public class WinAndLose {
	private String useid;
	private String nickname;//昵称
	private Integer score;
	private Long coin;
	public WinAndLose(String useid, String nickname, Integer score, Long coin) {
		super();
		this.useid = useid;
		this.nickname = nickname;
		this.score = score;
		this.coin = coin;
	}
	public WinAndLose() {
		super();
	}
	public String getUseid() {
		return useid;
	}
	public void setUseid(String useid) {
		this.useid = useid;
	}
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getScore() {
		return score;
	}
	public void setScore(Integer score) {
		this.score = score;
	}
	public Long getCoin() {
		return coin;
	}
	public void setCoin(Long coin) {
		this.coin = coin;
	}
	@Override
	public String toString() {
		return "WinAndLose [useid=" + useid + ", nickname=" + nickname + ", score=" + score + ", coin=" + coin + "]";
	}
	
}
