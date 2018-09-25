package com.happyProject.admin.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.OrderDao;
import com.happyProject.admin.model.Order;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class OrderDaoImpl implements OrderDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Order AddAnaUpdate(Order t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(Order t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Order t) {
		List<? extends Order> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(Order t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Order t) {
		Query query = new Query(Criteria.where("id").is(id));
		Update update = new Update();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				update.set(in, str);
			}
			mongoTemplate.updateMulti(query, update, t.getClass());
		}
	}

	@Override
	public PageBean<Order> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime, Integer result) {
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startTime!=null) {
			Date state = new Date(startTime);
			String startStr = s.format(state);
			String getfmt = DateFormat.getfmt(startStr, -12, "yyyy-MM-dd HH:mm:ss", false);
			try {
				Date parse = s.parse(getfmt);
				Long time = parse.getTime();
				startTime = time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(endTime!=null) {
			Date end = new Date(endTime);
			String entStr = s.format(end);
			String getfmt = DateFormat.getfmt(entStr, -12, "yyyy-MM-dd HH:mm:ss", false);
			try {
				Date parse = s.parse(getfmt);
				Long time = parse.getTime();
				endTime = time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		Criteria criatira = new Criteria();
		Criteria uCriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		Criteria rCriatira = new Criteria();
		if (userid != null && userid != "") {
			uCriatira = Criteria.where("userid").is(userid);
		}
		if (startTime != null && endTime != null) {
			Date start = new Date(startTime);
			Date end = new Date(endTime);
			tCriatira = Criteria.where("ctime").gte(start).lt(end);
		} else {
			if (startTime != null) {
				Date start = new Date(startTime);
				tCriatira = Criteria.where("ctime").gte(start);
			}
			if (endTime != null) {
				Date end = new Date(endTime);
				tCriatira = Criteria.where("ctime").lt(end);
			}
		}
		if (result != null) {
			rCriatira = Criteria.where("result").is(result);
		}
		criatira.andOperator(uCriatira, tCriatira, rCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, Order.class);
		if (currentPage != null && pageSize != null) {
			query.skip((currentPage - 1) * pageSize).limit(pageSize);
		}
		List<Order> find = mongoTemplate.find(query, Order.class);
		PageBean<Order> pb = new PageBean<Order>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

	@Override
	public Order findById(Object id, Order t) {
		Order order = mongoTemplate.findById(id, t.getClass());
		return order;
	}

	@Override
	public List<Order> findByStatusAll(Integer status, Date time, Integer start, Integer last) {
		Criteria criatira = new Criteria();
		if (status != null) {
			criatira.andOperator(Criteria.where("result").is(status));
		}
		Query query = new Query(criatira);
		if (time != null) {
			Date lastDay = DateFormat.getNextDay(
					DateFormat.setTimeZone(time, "yyyy-MM-dd"), last, "yyyy-MM-dd", true);

			Date startDay = DateFormat.getNextDay(
					DateFormat.setTimeZone(time, "yyyy-MM-dd"), start, "yyyy-MM-dd", true);

			query.addCriteria(Criteria.where("ctime").gte(startDay).lt(lastDay));
		}
		query.with(new Sort(Direction.ASC, "userid"));//升序
		List<Order> find = mongoTemplate.find(query, Order.class);

		return find;
	}

	@Override
	public Integer getToDayOrderNumber(Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);
		Date toDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), 0, "yyyy-MM-dd", true);

		Date nextDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), 1, "yyyy-MM-dd", true);

		query.addCriteria(Criteria.where("ctime").gte(toDay).lt(nextDay));
		Integer count = (int) mongoTemplate.count(query, Order.class);
		return count;
	}

	@Override
	public Double getToDayOrderMoney(Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);
		Date toDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), 0, "yyyy-MM-dd", true);

		Date nextDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), 1, "yyyy-MM-dd", true);

		query.addCriteria(Criteria.where("ctime").gte(toDay).lt(nextDay));
		List<Order> orderList = mongoTemplate.find(query,
				Order.class);
		Double money = 0d;
		if (orderList != null && orderList.size() > 0) {
			for (Order tradeRecord : orderList) {
				money += tradeRecord.getMoney();
			}
		}
		return money;
	}

	@Override
	public Double getYesterDayOrderMoney(Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);
		Date toDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), 0, "yyyy-MM-dd", true);

		Date yesterDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), -1, "yyyy-MM-dd", true);

		query.addCriteria(Criteria.where("ctime").gte(yesterDay).lt(toDay));
		List<Order> orderList = mongoTemplate.find(query,
				Order.class);
		Double money = 0d;
		if (orderList != null && orderList.size() > 0) {
			for (Order tradeRecord : orderList) {
				money += tradeRecord.getMoney();
			}
		}
		return money;
	}

	@Override
	public Double getSevenDayOrderMoney(Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);
		Date toDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), 1, "yyyy-MM-dd", true);
		
		Date time = DateFormat.setTimeZone(new Date(), "yyyy-MM-dd");
		int monthNumber = DateFormat.getMonthNumber(time);
		int m = 0-monthNumber;
//		Date yesterDay = DateUtilsClass.getNextDay(
//				DateUtilsClass.getTime(new Date(), "yyyy-MM-dd"), -7, "yyyy-MM-dd", true);
		
		Date yesterDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), m, "yyyy-MM-dd", true);

		query.addCriteria(Criteria.where("ctime").gte(yesterDay).lt(toDay));
		List<Order> orderList = mongoTemplate.find(query,
				Order.class);
		Double money = 0d;
		if (orderList != null && orderList.size() > 0) {
			for (Order tradeRecord : orderList) {
				money += tradeRecord.getMoney();
			}
		}
		return money;
	}

	@Override
	public Integer getCount(Integer result) {
		Query query = new Query(Criteria.where("result").is(result));
		int count = (int)mongoTemplate.count(query , Order.class);
		return count;
	}

	@Override
	public OrderWeekNumber getOrderNumber(Integer status, Date time, Integer start, Integer last) {
		Criteria criatira = new Criteria();
		/*
		 * if (map != null && map.size() != 0) { for (String in : map.keySet())
		 * { // map.keySet()返回的是所有key的值 Object str = map.get(in);//
		 * 得到每个key多对用value的值 criatira.andOperator(Criteria.where(in).is(str)); }
		 * }
		 */
		criatira.andOperator(Criteria.where("result").is(status));
		Query query = new Query(criatira);
		String format = null;
		if (time != null) {
			Date lastDay = DateFormat.getNextDay(
					DateFormat.setTimeZone(time, "yyyy-MM-dd"), last, "yyyy-MM-dd", true);

			Date startDay = DateFormat.getNextDay(
					DateFormat.setTimeZone(time, "yyyy-MM-dd"), start, "yyyy-MM-dd", true);
			// 星期几
			// String weekOfDate2 = DateUtilsClass.getWeekOfDate(startDay);
			format = new SimpleDateFormat("MM-dd").format(startDay);

			query.addCriteria(Criteria.where("ctime").gte(startDay).lt(lastDay));
		}
		Integer count = (int) mongoTemplate.count(query, Order.class);
		// 订单金额
		List<Order> orderList = mongoTemplate.find(query,
				Order.class);
		Double money = 0d;
		if (orderList != null && orderList.size() > 0) {
			for (Order tradeRecord : orderList) {
				money += tradeRecord.getMoney();
			}
		}

		// String wf = weekOfDate2+"("+format+")";

		OrderWeekNumber weekNumber = new OrderWeekNumber(format, count, money);
		return weekNumber;
	}

}
