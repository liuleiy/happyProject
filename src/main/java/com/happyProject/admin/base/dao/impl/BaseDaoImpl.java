package com.happyProject.admin.base.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import com.happyProject.admin.base.dao.BaseDao;

public class BaseDaoImpl<T> implements BaseDao<T> {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public T AddAnaUpdate(T t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(T t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(T t) {
		List<? extends Object> all = mongoTemplate.findAll(t.getClass());
		return all;
	}

	@Override
	public int getCount(T t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, T t) {
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
	public T findById(Object id, T t) {
		@SuppressWarnings("unchecked")
		T findById = (T) mongoTemplate.findById(id, t.getClass());
		return findById;
	}

}
