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

import com.happyProject.admin.dao.CreateRoomRecordDao;
import com.happyProject.admin.model.CreateRoomRecord;
import com.happyProject.admin.model.PageBean;

@Repository
public class CreateRoomRecordDaoImpl implements CreateRoomRecordDao{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public CreateRoomRecord AddAnaUpdate(CreateRoomRecord t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(CreateRoomRecord t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(CreateRoomRecord t) {
		List<? extends CreateRoomRecord> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(CreateRoomRecord t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, CreateRoomRecord t) {
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
	public PageBean<CreateRoomRecord> findByDataAndCount(String code, Integer currentPage, Integer pageSize) {
		Criteria criatira = new Criteria();
		Criteria cCriatira = new Criteria();
		if (code != null && code != "") {
			cCriatira = Criteria.where("code").is(code);
		}
		criatira.andOperator(cCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, CreateRoomRecord.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<CreateRoomRecord> find = mongoTemplate.find(query, CreateRoomRecord.class);
		PageBean<CreateRoomRecord> pb = new PageBean<CreateRoomRecord>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	
	}

	@Override
	public CreateRoomRecord findById(Object id, CreateRoomRecord t) {
		CreateRoomRecord createRoomRecord = mongoTemplate.findById(id, t.getClass());
		return createRoomRecord;
	}

}
