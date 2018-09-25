package com.happyProject.admin.dao.impl;

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

import com.happyProject.admin.dao.NoticesDao;
import com.happyProject.admin.model.Notices;
import com.happyProject.admin.model.PageBean;
@Repository
public class NoticesDaoImpl implements NoticesDao{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public Notices AddAnaUpdate(Notices t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(Notices t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Notices t) {
		List<? extends Notices> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(Notices t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Notices t) {
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
	public PageBean<Notices> findByDataAndCount(Integer rtype, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime) {
		Criteria criatira = new Criteria();
		Criteria rCriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		
		if (rtype != null) {
			rCriatira = Criteria.where("rtype").is(rtype);
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
		
		criatira.andOperator(rCriatira, tCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, Notices.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<Notices> find = mongoTemplate.find(query, Notices.class);
		PageBean<Notices> pb = new PageBean<Notices>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	
	
	}

	@Override
	public Notices findById(Object id, Notices t) {
		Notices notices = mongoTemplate.findById(id, t.getClass());
		return notices;
	}

}
