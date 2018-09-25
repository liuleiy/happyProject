package com.happyProject.admin.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.model.PageBean;

public interface OrderService  extends BaseDao<Order>{
	PageBean<Order> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,Integer result);
	
	List<Order> findByStatusAll(Integer status, Date time,
			Integer start, Integer last) ;
	Integer getToDayOrderNumber(Map<String, Object> map);

	Double getToDayOrderMoney(Map<String, Object> map);

	Double getYesterDayOrderMoney(Map<String, Object> map);

	Double getSevenDayOrderMoney(Map<String, Object> map);
	
	Integer getCount(Integer result);
	
	OrderWeekNumber getOrderNumber(Integer status,Date time,Integer start , Integer last);
}
