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

import com.happyProject.admin.dao.LoginLogDao;
import com.happyProject.admin.model.LoginLog;
import com.happyProject.admin.model.PageBean;

@Repository
public class LoginLogDaoImpl implements LoginLogDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public LoginLog AddAnaUpdate(LoginLog t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(LoginLog t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(LoginLog t) {
		List<? extends LoginLog> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(LoginLog t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, LoginLog t) {
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
	public LoginLog findById(Object id, LoginLog t) {
		LoginLog loginLog = mongoTemplate.findById(id, t.getClass());
		return loginLog;
	}

	@Override
	public Integer getTotal(String userid) {
		Query query = new Query(Criteria.where("userid").is(userid));
		int count = (int) mongoTemplate.count(query, LoginLog.class);
		return count;
	}

	@Override
	public PageBean<LoginLog> findByDataAndCount(Integer currentPage, Integer pageSize, String userid) {
		Criteria criatira = new Criteria();
		Criteria uCriatira = new Criteria();
		if (userid != null && userid != "") {
			uCriatira = Criteria.where("userid").is(userid);
		}

		criatira.andOperator(uCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, LoginLog.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<LoginLog> find = mongoTemplate.find(query, LoginLog.class);
		PageBean<LoginLog> pb = new PageBean<LoginLog>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}
}
