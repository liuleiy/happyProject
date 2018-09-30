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

import com.happyProject.admin.dao.LogCoinDao;
import com.happyProject.admin.model.LogCoin;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class LogCoinDaoImpl implements LogCoinDao {
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public LogCoin AddAnaUpdate(LogCoin t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(LogCoin t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(LogCoin t) {
		List<? extends LogCoin> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(LogCoin t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, LogCoin t) {
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
	public PageBean<LogCoin> findByDataAndCount(String userid, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime, Integer type) {
		/*SimpleDateFormat s = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
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
		}*/
		Criteria criatira = new Criteria();
		Criteria uCriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		Criteria typeCriatira = new Criteria();
		if (userid != null && userid != "") {
			uCriatira = Criteria.where("userid").is(userid);
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
		if(type!=null) {
			typeCriatira = Criteria.where("type").is(type);
		}
		criatira.andOperator(uCriatira, tCriatira,typeCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, LogCoin.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<LogCoin> find = mongoTemplate.find(query, LogCoin.class);
		PageBean<LogCoin> pb = new PageBean<LogCoin>();
		
		
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

	@Override
	public LogCoin findById(Object id, LogCoin t) {
		LogCoin logCoin = mongoTemplate.findById(id, t.getClass());
		return logCoin;
	}

	@Override
	public List<LogCoin> getTimePump(Integer state, Integer end, Integer type, Date time) {
		Criteria ccriatira = new Criteria();
		Criteria tcriatira = new Criteria();
		if (state != null && end != null) {
			Date toDay = DateFormat.getNextDay(
					DateFormat.setTimeZone(time, "yyyy-MM-dd"), end,
					"yyyy-MM-dd", true);

			Date yesterDay = DateFormat.getNextDay(
					DateFormat.setTimeZone(time, "yyyy-MM-dd"), state,
					"yyyy-MM-dd", true);
			ccriatira = Criteria.where("ctime").gte(yesterDay).lt(toDay);
		}
		if (type != null) {
			tcriatira = Criteria.where("type").is(type);
		}
		Criteria criatira = new Criteria();
		criatira.andOperator(ccriatira, tcriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		List<LogCoin> find = mongoTemplate.find(query, LogCoin.class);

		return find;
	}

}
