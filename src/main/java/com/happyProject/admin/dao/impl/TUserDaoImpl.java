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

import com.happyProject.admin.dao.TUserDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;

@Repository
public class TUserDaoImpl implements TUserDao{

	@Resource
	private MongoTemplate mongoTemplate;
	
	
	@Override
	public TUser AddAnaUpdate(TUser t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(TUser t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(TUser t) {
		List<? extends TUser> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(TUser t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, TUser t) {
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
	public TUser findById(Object id, TUser t) {
		TUser tUser = mongoTemplate.findById(id, t.getClass());
		return tUser;
	}

	@Override
	public PageBean<TUser> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime) {
		Criteria criatira = new Criteria();
		Criteria tCriatira = new Criteria();

		if (startTime != null && endTime != null) {
			Date start = new Date(startTime);
			Date end = new Date(endTime);
			tCriatira = Criteria.where("create_time").gte(start).lt(end);
		} else {
			if (startTime != null) {
				Date start = new Date(startTime);
				tCriatira = Criteria.where("create_time").gte(start);
			}
			if (endTime != null) {
				Date end = new Date(endTime);
				tCriatira = Criteria.where("create_time").lt(end);
			}
		}
		criatira.andOperator(tCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "create_ctime"));
		int count = (int) mongoTemplate.count(query, TUser.class);
		if (currentPage != null && pageSize != null) {
			query.skip((currentPage - 1) * pageSize).limit(pageSize);
		}
		List<TUser> find = mongoTemplate.find(query, TUser.class);
		PageBean<TUser> pb = new PageBean<TUser>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;

	}

	@Override
	public TUser login(String username, String password) {
		Criteria dateCriteria = new Criteria();
		// 判断密码的条件
		Criteria pWordCriteria = new Criteria();
		// 判断账号的条件
		Criteria uNameCriteria = Criteria.where("user_name").is(username);

		if (password != null) {// 无密码是，就只是根据账户判断
			pWordCriteria = Criteria.where("password").is(password);
		}
		dateCriteria.andOperator(uNameCriteria, pWordCriteria);
		Query query = new Query(dateCriteria);
		List<TUser> find = mongoTemplate.find(query, TUser.class);

		if (find.size() > 0) {
			return find.get(0);
		}
		return null;
	}

}
