package com.happyProject.admin.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Direction;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import com.happyProject.admin.base.dao.impl.BaseDaoImpl;
import com.happyProject.admin.dao.GoodsDao;
import com.happyProject.admin.model.Goods;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class GoodsDaoImpl extends BaseDaoImpl<Goods> implements GoodsDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public PageBean<Goods> findByDataAndCount(String name, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,Integer del) {
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
		Criteria nCriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		Criteria dCriatira = new Criteria();
		if (name != null && name != "") {
			nCriatira = Criteria.where("name").is(name);
		}
		if (startTime != null && endTime != null) {
//			LocalDateTime start = DateFormat.getDateTimeOfTimestamp(startTime);
//			LocalDateTime end = DateFormat.getDateTimeOfTimestamp(endTime);
			Date start = new Date(startTime);
			Date end = new Date(endTime);
			tCriatira = Criteria.where("ctime").gte(start).lt(end);
		} else {
			if (startTime != null) {
				//LocalDateTime start = DateFormat.getDateTimeOfTimestamp(startTime);
				Date start = new Date(startTime);
				tCriatira = Criteria.where("ctime").gte(start);
			}
			if (endTime != null) {
				//LocalDateTime end = DateFormat.getDateTimeOfTimestamp(endTime);
				Date end = new Date(endTime);
				tCriatira = Criteria.where("ctime").lt(end);
			}
		}
		if(del != null) {
			dCriatira = Criteria.where("del").is(del);
		}
		criatira.andOperator(nCriatira, tCriatira, dCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, Goods.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<Goods> find = mongoTemplate.find(query, Goods.class);
		PageBean<Goods> pb = new PageBean<Goods>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

}
