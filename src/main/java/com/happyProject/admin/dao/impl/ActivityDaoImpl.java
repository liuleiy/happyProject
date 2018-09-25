package com.happyProject.admin.dao.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import com.happyProject.admin.dao.ActivityDao;
import com.happyProject.admin.model.Activity;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class ActivityDaoImpl implements ActivityDao{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public Activity AddAnaUpdate(Activity t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(Activity t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Activity t) {
		List<? extends Activity> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(Activity t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Activity t) {
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
	public PageBean<Activity> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime) {
		Criteria criatira = new Criteria();
		Criteria tCriatira = new Criteria();
		SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		if(startTime!=null) {
			Date state = new Date(startTime);
			String startStr = s.format(state);
			String getfmt = DateFormat.getfmt(startStr, -12, "yyyy-MM-dd HH:mm:ss", false);
			try {
				Date parse = s.parse(getfmt);
				Long time = parse.getTime();
				startTime = time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
		}
		
		if(endTime!=null) {
			Date end = new Date(endTime);
			String entStr = s.format(end);
			String getfmt = DateFormat.getfmt(entStr, -12, "yyyy-MM-dd HH:mm:ss", false);
			try {
				Date parse = s.parse(getfmt);
				Long time = parse.getTime();
				endTime = time;
			} catch (ParseException e) {
				e.printStackTrace();
			}
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
		
		criatira.andOperator(tCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, Activity.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<Activity> find = mongoTemplate.find(query, Activity.class);
		PageBean<Activity> pb = new PageBean<Activity>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

	@Override
	public Activity findById(Object id, Activity t) {
		Activity activity = mongoTemplate.findById(id, t.getClass());
		return activity;
	}

}
