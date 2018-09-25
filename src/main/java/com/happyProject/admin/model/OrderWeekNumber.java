package com.happyProject.admin.model;

public class OrderWeekNumber {
	private String week;//时间
	private Object number;//人数
	private Double money;//钱
	public OrderWeekNumber(String week, Object number, Double money) {
		super();
		this.week = week;
		this.number = number;
		this.money = money;
	}
	public OrderWeekNumber() {
		super();
	}
	public String getWeek() {
		return week;
	}
	public void setWeek(String week) {
		this.week = week;
	}
	public Object getNumber() {
		return number;
	}
	public void setNumber(Object number) {
		this.number = number;
	}
	public Double getMoney() {
		return money;
	}
	public void setMoney(Double money) {
		this.money = money;
	}
	@Override
	public String toString() {
		return "OrderWeekNumber [week=" + week + ", number=" + number + ", money=" + money + "]";
	}
	
}
