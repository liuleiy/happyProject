package com.happyProject.admin.dao.impl;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.ColLogRegistDao;
import com.happyProject.admin.model.ColLogRegist;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class ColLogRegistDaoImpl implements ColLogRegistDao{

	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public ColLogRegist AddAnaUpdate(ColLogRegist t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(ColLogRegist t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(ColLogRegist t) {
		List<? extends ColLogRegist> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(ColLogRegist t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, ColLogRegist t) {
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
	public ColLogRegist findById(Object id, ColLogRegist t) {
		ColLogRegist findById = mongoTemplate.findById(id, t.getClass());
		return findById;
	}

	@Override
	public OrderWeekNumber getOrderNumber(Date time, Integer start, Integer last) {
		
		//DateFormat
		Query query = new Query();
		Date lastDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(time, "yyyy-MM-dd"), last, "yyyy-MM-dd", true);

		Date startDay = DateFormat.getNextDay(
				DateFormat.setTimeZone(time, "yyyy-MM-dd"), start, "yyyy-MM-dd", true);

		query.addCriteria(Criteria.where("ctime").gte(startDay).lt(lastDay));
		Integer count = (int) mongoTemplate.count(query, ColLogRegist.class);
		String format = new SimpleDateFormat("MM-dd").format(startDay);
		OrderWeekNumber weekNumber = new OrderWeekNumber(format, count, null);
		return weekNumber;
	}

}
