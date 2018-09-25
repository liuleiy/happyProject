package com.happyProject.admin.service.impl;


import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.OrderDaoImpl;
import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.OrderService;

@Repository
public class OrderServiceImpl implements OrderService{
	@Resource
	private OrderDaoImpl orderDaoImpl;
	@Override
	public Order AddAnaUpdate(Order t) {
		return orderDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(Order t) {
		orderDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Order t) {
		return orderDaoImpl.getAll(t);
	}

	@Override
	public int getCount(Order t) {
		return orderDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Order t) {
		orderDaoImpl.updateById(id, map, t);
	}

	@Override
	public PageBean<Order> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime, Integer result) {
		
		return orderDaoImpl.findByDataAndCount(userid, currentPage, pageSize, startTime, endTime, result);
	}

	@Override
	public Order findById(Object id, Order t) {
		return orderDaoImpl.findById(id, t);
	}

	@Override
	public List<Order> findByStatusAll(Integer status, Date time, Integer start, Integer last) {
		return orderDaoImpl.findByStatusAll(status, time, start, last);
	}

	@Override
	public Integer getToDayOrderNumber(Map<String, Object> map) {
		return orderDaoImpl.getToDayOrderNumber(map);
	}

	@Override
	public Double getToDayOrderMoney(Map<String, Object> map) {
		return orderDaoImpl.getToDayOrderMoney(map);
	}

	@Override
	public Double getYesterDayOrderMoney(Map<String, Object> map) {
		return orderDaoImpl.getYesterDayOrderMoney(map);
	}

	@Override
	public Double getSevenDayOrderMoney(Map<String, Object> map) {
		return orderDaoImpl.getSevenDayOrderMoney(map);
	}

	@Override
	public Integer getCount(Integer result) {
		return orderDaoImpl.getCount(result);
	}

	@Override
	public OrderWeekNumber getOrderNumber(Integer status, Date time, Integer start, Integer last) {
		return orderDaoImpl.getOrderNumber(status, time, start, last);
	}
}
