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

import com.happyProject.admin.dao.LabelDao;
import com.happyProject.admin.model.Label;
import com.happyProject.admin.model.PageBean;

@Repository
public class LabelDaoImpl implements LabelDao{
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public Label AddAnaUpdate(Label t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(Label t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Label t) {
		List<? extends Label> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(Label t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Label t) {
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
	public Label findById(Object id, Label t) {
		Label label = mongoTemplate.findById(id, t.getClass());
		return label;
	}

	@Override
	public PageBean<Label> findByDataAndCount(Integer currentPage, Integer pageSize) {
		Criteria criatira = new Criteria();
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, Label.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<Label> find = mongoTemplate.find(query, Label.class);
		PageBean<Label> pb = new PageBean<Label>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

}
