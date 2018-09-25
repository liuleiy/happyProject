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

import com.happyProject.admin.dao.RoomResultRecordDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.RoomResultRecord;
import com.happyProject.admin.utlis.DateFormat;

@Repository
public class RoomResultRecordDaoImpl implements RoomResultRecordDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public RoomResultRecord AddAnaUpdate(RoomResultRecord t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(RoomResultRecord t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(RoomResultRecord t) {
		List<? extends RoomResultRecord> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(RoomResultRecord t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, RoomResultRecord t) {
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
	public PageBean<RoomResultRecord> findByDataAndCount(String userid, Integer currentPage, Integer pageSize,
			Long startTime, Long endTime) {
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
		
		Criteria criatira = new Criteria();
		Criteria uCriatira = new Criteria();
		Criteria tCriatira = new Criteria();

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

		criatira.andOperator(uCriatira, tCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		int count = (int) mongoTemplate.count(query, RoomResultRecord.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<RoomResultRecord> find = mongoTemplate.find(query, RoomResultRecord.class);
		PageBean<RoomResultRecord> pb = new PageBean<RoomResultRecord>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;

	}

	@Override
	public RoomResultRecord findById(Object id, RoomResultRecord t) {
		RoomResultRecord roomResultRecord = mongoTemplate.findById(id, t.getClass());
		return roomResultRecord;
	}

	@Override
	public List<RoomResultRecord> getTimeRevenue(Integer state, Integer end, Date time, Long startTime, Long endTime) {
		Criteria ccriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		if (state != null && end != null) {
			Date toDay = DateFormat.getNextDay(DateFormat.setTimeZone(time, "yyyy-MM-dd"), end, "yyyy-MM-dd", true);

			Date yesterDay = DateFormat.getNextDay(DateFormat.setTimeZone(time, "yyyy-MM-dd"), state, "yyyy-MM-dd",
					true);
			ccriatira = Criteria.where("ctime").gte(yesterDay).lt(toDay);
		}
		
		if (startTime != null && endTime != null) {
//			LocalDateTime start = DateFormat.getDateTimeOfTimestamp(startTime);
//			LocalDateTime end = DateFormat.getDateTimeOfTimestamp(endTime);
			Date startDate = new Date(startTime);
			Date endDate = new Date(endTime);
			tCriatira = Criteria.where("ctime").gte(startDate).lt(endDate);
		} else {
			if (startTime != null) {
				//LocalDateTime start = DateFormat.getDateTimeOfTimestamp(startTime);
				Date startDate = new Date(startTime);
				tCriatira = Criteria.where("ctime").gte(startDate);
			}
			if (endTime != null) {
				//LocalDateTime end = DateFormat.getDateTimeOfTimestamp(endTime);
				Date endDate = new Date(endTime);
				tCriatira = Criteria.where("ctime").lt(endDate);
			}
		}
		
		Criteria criatira = new Criteria();
		criatira.andOperator(ccriatira,tCriatira);
		Query query = new Query(criatira);
		query.with(new Sort(Direction.DESC, "ctime"));
		List<RoomResultRecord> find = mongoTemplate.find(query, RoomResultRecord.class);
		return find;
	}

	@Override
	public RoomResultRecord findOne(String rid) {
		Query query = new Query(Criteria.where("roomid").is(rid));
		RoomResultRecord roomResultRecord = mongoTemplate.findOne(query , RoomResultRecord.class);
		return roomResultRecord;
	}

}
