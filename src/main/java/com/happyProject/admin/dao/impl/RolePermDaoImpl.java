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

import com.happyProject.admin.dao.RolePermDao;
import com.happyProject.admin.model.RolePerm;

@Repository
public class RolePermDaoImpl implements RolePermDao{
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public RolePerm AddAnaUpdate(RolePerm t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(RolePerm t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(RolePerm t) {
		List<? extends RolePerm> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(RolePerm t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, RolePerm t) {
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
	public RolePerm findById(Object id, RolePerm t) {
		RolePerm rolePerm = mongoTemplate.findById(id, t.getClass());
		return rolePerm;
	}

	@Override
	public List<RolePerm> findByMap(Integer currentPage, Integer pageSize, Map<String, Object> map) {
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
		List<RolePerm> find = mongoTemplate.find(query, RolePerm.class);
		return find;
	}
	
	
	
}
