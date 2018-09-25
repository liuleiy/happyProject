package com.happyProject.admin.dao.impl;

import java.util.ArrayList;
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

import com.happyProject.admin.dao.OnlinkDao;
import com.happyProject.admin.model.Onlink;
import com.happyProject.admin.model.OrderWeekNumber;

@Repository
public class OnlinkDaoImpl implements OnlinkDao{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public Onlink AddAnaUpdate(Onlink t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(Onlink t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Onlink t) {
		List<? extends Onlink> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(Onlink t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Onlink t) {
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
	public Onlink findById(Object id, Onlink t) {
		Onlink onlink = mongoTemplate.findById(id, t.getClass());
		return onlink;
	}

	@Override
	public List<OrderWeekNumber> findByCtime(Integer last, Integer end) {
		Query query = new Query();
		query.with(new Sort(Direction.DESC, "ctime"));
		query.skip(last).limit(end);
		List<Onlink> findAll = mongoTemplate.find(query, Onlink.class);
		List<OrderWeekNumber> list = new ArrayList<OrderWeekNumber>();
		if (findAll != null && findAll.size() > 0) {
			int size = findAll.size()-1;
			for(;size>=0;size--){
				Onlink onlink = findAll.get(size);
				OrderWeekNumber weekNumber = new OrderWeekNumber();
				Integer number = onlink.getNumber();
				String time = onlink.getTime();
				weekNumber.setNumber(number);
				weekNumber.setWeek(time);
				list.add(weekNumber);
			}
		}
		return list;
	}

}
