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

import com.happyProject.admin.dao.OperationLogDao;
import com.happyProject.admin.model.OperationLog;
import com.happyProject.admin.model.PageBean;

@Repository
public class OperationLogDaoImpl implements OperationLogDao{
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public OperationLog AddAnaUpdate(OperationLog t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(OperationLog t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(OperationLog t) {
		List<? extends OperationLog> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(OperationLog t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, OperationLog t) {
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
	public OperationLog findById(Object id, OperationLog t) {
		OperationLog operationLog = mongoTemplate.findById(id, t.getClass());
		return operationLog;
	}

	@Override
	public PageBean<OperationLog> findByDataAndCount(String username, Integer currentPage, Integer pageSize,
			Long startTime, Long endTime) {
		Criteria criatira = new Criteria();
		Criteria uCriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		
		if (username != null && username != "") {
			uCriatira = Criteria.where("actor").is(username);
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
		
		criatira.andOperator(uCriatira, tCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		
		int count = (int) mongoTemplate.count(query, OperationLog.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<OperationLog> find = mongoTemplate.find(query, OperationLog.class);
		PageBean<OperationLog> pb = new PageBean<OperationLog>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

}
