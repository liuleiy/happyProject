package com.happyProject.admin.model;

public class TopUpRanking implements Comparable<TopUpRanking>{
	private Integer ranking;
	private String userid;
	private Integer orderNumber;
	private Double topUp;
	public TopUpRanking(Integer ranking, String userid, Integer orderNumber, Double topUp) {
		super();
		this.ranking = ranking;
		this.userid = userid;
		this.orderNumber = orderNumber;
		this.topUp = topUp;
	}
	public TopUpRanking() {
		super();
	}
	public Integer getRanking() {
		return ranking;
	}
	public void setRanking(Integer ranking) {
		this.ranking = ranking;
	}
	public String getUserid() {
		return userid;
	}
	public void setUserid(String userid) {
		this.userid = userid;
	}
	public Integer getOrderNumber() {
		return orderNumber;
	}
	public void setOrderNumber(Integer orderNumber) {
		this.orderNumber = orderNumber;
	}
	public Double getTopUp() {
		return topUp;
	}
	public void setTopUp(Double topUp) {
		this.topUp = topUp;
	}
	@Override
	public String toString() {
		return "TopUpRanking [ranking=" + ranking + ", userid=" + userid + ", orderNumber=" + orderNumber + ", topUp="
				+ topUp + "]";
	}
	@Override
	public int compareTo(TopUpRanking o) {
		if(this.getTopUp()<=o.getTopUp()){
			return 1;
		}
		return -1;
	}
	
}
