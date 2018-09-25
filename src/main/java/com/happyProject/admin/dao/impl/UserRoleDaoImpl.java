package com.happyProject.admin.dao.impl;

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

import com.happyProject.admin.dao.UserRoleDao;
import com.happyProject.admin.model.UserRole;

@Repository
public class UserRoleDaoImpl implements UserRoleDao{

	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public UserRole AddAnaUpdate(UserRole t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(UserRole t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(UserRole t) {
		List<? extends UserRole> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(UserRole t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, UserRole t) {
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
	public UserRole findById(Object id, UserRole t) {
		UserRole userRole = mongoTemplate.findById(id,t.getClass());
		return userRole;
	}

	@Override
	public Integer count(UserRole userRole, Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);

		int count = (int) mongoTemplate.count(query, userRole.getClass());
		return count;
	}

	@Override
	public List<UserRole> findByMap(Integer currentPage, Integer pageSize, Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		if (currentPage != -1 && pageSize != -1) {
			query.skip((currentPage - 1) * pageSize).limit(pageSize);
		}
		List<UserRole> find = mongoTemplate.find(query, UserRole.class);
		return find;
	}

	@Override
	public void removeByField(String field, String value) {
		Query query = Query.query(Criteria.where(field).is(value));
		mongoTemplate.remove(query,UserRole.class);
	}

}
