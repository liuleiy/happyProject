package com.happyProject.admin.model;

import java.util.Arrays;

/**
 * 每局详情记录
 * @author Administrator
 *
 */
public class Roles {
	private String userid;//玩家id
	private Integer [] cards; //手牌
	//private List<Integer> cards = new ArrayList<Integer>();
	private Integer value;//牌力
	private Integer score; //输赢数量
	private Integer rest;//剩余
	private Integer bets;//下注数量倍数
	public Roles(String userid, Integer[] cards, Integer value, Integer score,
			Integer rest, Integer bets) {
		super();
		this.userid = userid;
		this.cards = cards;
		this.value = value;
		this.score = score;
		this.rest = rest;
		this.bets = bets;
	}
	public Roles() {
		super();
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer[] getCards() {
		return cards;
	}
	public void setCards(Integer[] cards) {
		this.cards = cards;
	}
	public Integer getValue() {
		return value;
	}
	public void setValue(Integer value) {
		this.value = value;
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
	public Integer getBets() {
		return bets;
	}
	public void setBets(Integer bets) {
		this.bets = bets;
	}
	@Override
	public String toString() {
		return "Roles [userid=" + userid + ", cards=" + Arrays.toString(cards)
				+ ", value=" + value + ", score=" + score + ", rest=" + rest
				+ ", bets=" + bets + "]";
	}
}
