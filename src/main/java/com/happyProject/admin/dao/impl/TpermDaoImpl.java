package com.happyProject.admin.dao.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.TpermDao;
import com.happyProject.admin.model.Tperm;

@Repository
public class TpermDaoImpl implements TpermDao{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public Tperm AddAnaUpdate(Tperm t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(Tperm t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Tperm t) {
		List<? extends Tperm> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(Tperm t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Tperm t) {
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
	public Tperm findById(Object id, Tperm t) {
		Tperm tperm = mongoTemplate.findById(id, t.getClass());
		return tperm;
	}

}
