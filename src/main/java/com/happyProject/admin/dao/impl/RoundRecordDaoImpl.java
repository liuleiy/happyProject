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

import com.happyProject.admin.dao.RoundRecordDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoundRecord;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class RoundRecordDaoImpl implements RoundRecordDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public RoundRecord AddAnaUpdate(RoundRecord t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(RoundRecord t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(RoundRecord t) {
		List<? extends RoundRecord> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(RoundRecord t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, RoundRecord t) {
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
	public PageBean<RoundRecord> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime,
			String roomid) {
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
		Criteria tCriatira = new Criteria();
		Criteria rCriatira = new Criteria();

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
		if (roomid != null && roomid != "") {
			tCriatira = Criteria.where("roomid").is(roomid);
		}

		criatira.andOperator(tCriatira, rCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, RoundRecord.class);
		if (currentPage != null && pageSize != null) {
			query.skip((currentPage - 1) * pageSize).limit(pageSize);
		}
		List<RoundRecord> find = mongoTemplate.find(query, RoundRecord.class);
		PageBean<RoundRecord> pb = new PageBean<RoundRecord>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;

	}

	@Override
	public RoundRecord findById(Object id, RoundRecord t) {
		RoundRecord roundRecord = mongoTemplate.findById(id, t.getClass());
		return roundRecord;
	}

	@Override
	public List<RoundRecord> getNewTimeUser() {
		Date toDay0 = DateFormat.getNextDay(
				new Date(), 0, "yyyy-MM-dd HH", false);
		Date nextDay0 = DateFormat.getNextDay(
				new Date(), 1, "yyyy-MM-dd HH", false);
		
		//Date toDay = DateFormat.fmtTimeZone(toDay0, -12, "yyyy-MM-dd HH", false);
		//Date nextDay = DateFormat.fmtTimeZone(nextDay0, -12, "yyyy-MM-dd HH", false);
		
		Query query = new Query();
		query.addCriteria(Criteria.where("ctime").gte(toDay0).lt(nextDay0));
		//query.with(new Sort(Direction.ASC, "userid"));//升序
		
		List<RoundRecord> find = mongoTemplate.find(query, RoundRecord.class);
		return find;
	}

}
