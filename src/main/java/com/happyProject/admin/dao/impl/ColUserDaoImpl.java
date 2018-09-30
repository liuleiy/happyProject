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

import com.happyProject.admin.dao.ColUserDao;
import com.happyProject.admin.model.ColUser;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.utlis.DateFormat;
import com.happyProject.admin.utlis.StringUtils;

@Repository
public class ColUserDaoImpl implements ColUserDao {
	@Resource
	private MongoTemplate mongoTemplate;

	@Override
	public ColUser AddAnaUpdate(ColUser t) {
		mongoTemplate.save(t);
		return t;
	}

	@Override
	public void remove(ColUser t) {
		mongoTemplate.remove(t);
	}

	@Override
	public List<? extends Object> getAll(ColUser t) {
		List<? extends ColUser> findAll = mongoTemplate.findAll(t.getClass());
		return findAll;
	}

	@Override
	public int getCount(ColUser t) {
		int count = (int) mongoTemplate.count(new Query(), t.getClass());
		return count;
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, ColUser t) {
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
	public ColUser findById(Object id, ColUser t) {
		ColUser colUser = mongoTemplate.findById(id, t.getClass());
		return colUser;
	}

	@Override
	public PageBean<ColUser> findByDataAndCount(String iid, String nickname, Integer agent_level, Integer currentPage,
			Integer pageSize, Long startTime, Long endTime, String agent, String start_profit, String last_profit,
			String start_bring_profit, String last_bring_profit, Integer rtypeValue,String arrangement) {
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
		Criteria iCriatira = new Criteria();
		Criteria nCriatira = new Criteria();
		Criteria aCriatira = new Criteria();
		Criteria tCriatira = new Criteria();
		Criteria agentCriatira = new Criteria();
		Criteria bringCriatira = new Criteria();
		Criteria rtypeValueCriatira = new Criteria();
		Criteria profitCriteria = new Criteria();
		Criteria pCriteria = new Criteria();
		Criteria agCriatira = new Criteria();
		if (iid != null && iid != "") {
			iCriatira = Criteria.where("id").is(iid);
		}
		if (nickname != null && nickname != "") {
			nCriatira = Criteria.where("nickname").is(nickname);
		}
		if (agent_level != null) {
			aCriatira = Criteria.where("agent_state").is(1);
		}
		if (agent != null && !"".equals(agent)) {
			agentCriatira = Criteria.where("agent").is(agent);
		}

		if (start_profit != null && last_profit != null && start_profit != "" && last_profit != "") {
			boolean start = StringUtils.isNumeric(start_profit);
			boolean last = StringUtils.isNumeric(last_profit);
			if (start && last) {
				pCriteria = Criteria.where("profit").gte(Integer.parseInt(start_profit) * 100)
						.lt(Integer.parseInt(last_profit) * 100);
			} else {
				pCriteria = Criteria.where("profit").gte(start_profit).lt(last_profit);
			}
		} else {
			if (start_profit != null && start_profit != "") {
				boolean start = StringUtils.isNumeric(start_profit);

				if (start) {
					pCriteria = Criteria.where("profit").gte(Integer.parseInt(start_profit) * 100);
				} else {
					pCriteria = Criteria.where("profit").gte(start_profit);
				}

			}
			if (last_profit != null && last_profit != "") {
				boolean last = StringUtils.isNumeric(last_profit);
				if (last) {
					pCriteria = Criteria.where("profit").lt(Integer.parseInt(last_profit) * 100);
				} else {
					pCriteria = Criteria.where("profit").lt(last_profit);
				}

			}
		}
		// ---------------------------------------
		if (start_bring_profit != null && last_bring_profit != null && start_bring_profit != ""
				&& last_bring_profit != "") {
			boolean start = StringUtils.isNumeric(start_bring_profit);
			boolean last = StringUtils.isNumeric(last_bring_profit);
			if (start && last) {
				bringCriatira = Criteria.where("bring_profit").gte(Integer.parseInt(start_bring_profit) * 100)
						.lt(Integer.parseInt(last_bring_profit) * 100);
			} else {
				bringCriatira = Criteria.where("bring_profit").gte(start_bring_profit).lt(last_bring_profit);
			}
		} else {
			if (start_bring_profit != null && start_bring_profit != "") {
				boolean start = StringUtils.isNumeric(start_bring_profit);

				if (start) {
					bringCriatira = Criteria.where("bring_profit").gte(Integer.parseInt(start_bring_profit) * 100);
				} else {
					bringCriatira = Criteria.where("bring_profit").gte(start_bring_profit);
				}

			}
			if (last_bring_profit != null && last_bring_profit != "") {
				boolean last = StringUtils.isNumeric(last_bring_profit);
				if (last) {
					bringCriatira = Criteria.where("bring_profit").lt(Integer.parseInt(last_bring_profit) * 100);
				} else {
					bringCriatira = Criteria.where("bring_profit").lt(last_bring_profit);
				}

			}
		}
		// -------------------------------------------
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

		if (rtypeValue != null) {
			if (rtypeValue == 0) {
				profitCriteria = Criteria.where("profit_rate_sum").is(0);
				agCriatira = Criteria.where("agent").ne("");
			} else if (rtypeValue == 1) {
				profitCriteria = Criteria.where("profit_rate_sum").ne(0);
				agCriatira = Criteria.where("agent").ne("");
			} else if (rtypeValue == 2) {
				profitCriteria = Criteria.where("agent_level").is(1);
				agCriatira = Criteria.where("agent").is("");
			}
		}
		criatira.andOperator(iCriatira, tCriatira, nCriatira, aCriatira, agentCriatira, bringCriatira,
				rtypeValueCriatira, profitCriteria, pCriteria,agCriatira);
		Query query = new Query(criatira);
		if (arrangement != null) {
			if (arrangement.equals("time_asc")) {
				query.with(new Sort(Direction.ASC, "ctime"));
			} else if (arrangement.equals("time_desc")) {
				query.with(new Sort(Direction.DESC, "ctime"));
			} else if (arrangement.equals("profit_asc")) {
				query.with(new Sort(Direction.ASC, "profit"));
			} else if (arrangement.equals("profit_desc")) {
				query.with(new Sort(Direction.DESC, "profit"));
			} else if (arrangement.equals("bring_profit_asc")) {
				query.with(new Sort(Direction.ASC, "bring_profit"));
			} else if (arrangement.equals("bring_profit_desc")) {
				query.with(new Sort(Direction.DESC, "bring_profit"));
			}else if("".equals(arrangement)) {
				query.with(new Sort(Direction.DESC, "ctime"));
			}
		}else {
			query.with(new Sort(Direction.DESC, "ctime"));
		}
		int count = (int) mongoTemplate.count(query, ColUser.class);
		query.skip((currentPage - 1) * pageSize).limit(pageSize);
		List<ColUser> find = mongoTemplate.find(query, ColUser.class);
		PageBean<ColUser> pb = new PageBean<ColUser>();
		pb.setData(find);
		pb.setAllRow(count);
		return pb;
	}

	@Override
	public Integer findByStatusCount(Integer status, Date time, Integer start, Integer last) {
		Criteria criatira = new Criteria();
		if (status != null) {
			criatira.andOperator(Criteria.where("result").is(status));
		}
		Query query = new Query(criatira);
		if (time != null) {
			Date lastDay = DateFormat.getNextDay(DateFormat.setTimeZone(time, "yyyy-MM-dd"), last, "yyyy-MM-dd", true);

			Date startDay = DateFormat.getNextDay(DateFormat.setTimeZone(time, "yyyy-MM-dd"), start, "yyyy-MM-dd",
					true);

			query.addCriteria(Criteria.where("ctime").gte(startDay).lt(lastDay));
		}
		Integer count = (int) mongoTemplate.count(query, ColUser.class);

		return count;
	}

	@Override
	public Integer getAgentNumber(Integer state, Integer end, Integer agent_state, Integer dayMonth) {
		Criteria criatira = new Criteria();
		Criteria tcriatira = new Criteria();
		Criteria acriatira = new Criteria();

		if (state != null && end != null) {
			Date time = new Date();
			Date yearMonth = null;
			Date nextYearMonth = null;

			if (dayMonth == 0) {// 0,天
				yearMonth = DateFormat.getNextDay(DateFormat.setTimeZone(time, "yyyy-MM-dd"), state, "yyyy-MM-dd",
						true);

				nextYearMonth = DateFormat.getNextDay(DateFormat.setTimeZone(time, "yyyy-MM-dd"), end, "yyyy-MM-dd",
						true);
			} else if (dayMonth == 1) {// 1,月
				yearMonth = DateFormat.getYearMonth(DateFormat.setTimeZone(new Date(), "yyyy-MM"), state);

				nextYearMonth = DateFormat.getYearMonth(DateFormat.setTimeZone(new Date(), "yyyy-MM"), end);
			}
			tcriatira = Criteria.where("agent_join_time").gte(yearMonth).lt(nextYearMonth);
		}

		if (agent_state != null) {
			acriatira = Criteria.where("agent_state").is(agent_state);
		}

		criatira.andOperator(acriatira, tcriatira);
		Query query = new Query(criatira);
		/*
		 * query.addCriteria(Criteria.where("ctime").gte(yearMonth) .lt(nextYearMonth));
		 */

		Integer count = (int) mongoTemplate.count(query, ColUser.class);

		return count;
	}

	@Override
	public Integer getDayAgentNumber(Integer state, Integer end) {
		Date toDay = DateFormat.getNextDay(DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), state, "yyyy-MM-dd", true);
		Date nextDay = DateFormat.getNextDay(DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), end, "yyyy-MM-dd", true);
		Query query = new Query();
		query.addCriteria(Criteria.where("ctime").gte(toDay).lt(nextDay));
		Integer count = (int) mongoTemplate.count(query, ColUser.class);
		return count;
	}

	@Override
	public Integer getYesterdayAgentNumber(Integer state, Integer end) {
		Date toDay = DateFormat.getNextDay(DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), end, "yyyy-MM-dd", true);
		Date oldDay = DateFormat.getNextDay(DateFormat.setTimeZone(new Date(), "yyyy-MM-dd"), state, "yyyy-MM-dd",
				true);
		Query query = new Query();
		query.addCriteria(Criteria.where("ctime").gte(oldDay).lt(toDay));
		Integer count = (int) mongoTemplate.count(query, ColUser.class);
		return count;
	}

	@Override
	public Integer getThisMonthAgentNumber() {
		Date yearMonth = DateFormat.getYearMonth(DateFormat.setTimeZone(new Date(), "yyyy-MM"), 0);

		Date nextYearMonth = DateFormat.getYearMonth(DateFormat.setTimeZone(new Date(), "yyyy-MM"), 1);
		Query query = new Query();
		query.addCriteria(Criteria.where("ctime").gte(yearMonth).lt(nextYearMonth));
		Integer count = (int) mongoTemplate.count(query, ColUser.class);
		return count;
	}

	@Override
	public Integer count(ColUser colUser, Map<String, Object> map) {
		Criteria criatira = new Criteria();
		if (map != null && map.size() != 0) {
			for (String in : map.keySet()) {
				// map.keySet()返回的是所有key的值
				Object str = map.get(in);// 得到每个key多对用value的值
				criatira.andOperator(Criteria.where(in).is(str));
			}
		}
		Query query = new Query(criatira);

		int count = (int) mongoTemplate.count(query, colUser.getClass());
		return count;
	}

	@Override
	public List<ColUser> getAgent(String agent_state) {
		Criteria agentCriatira = new Criteria();
		if (agent_state != null) {
			agentCriatira = Criteria.where("agent_state").is(1);
		}

		Criteria criteria = new Criteria();
		criteria.andOperator(agentCriatira);
		Query query = new Query(criteria);
		List<ColUser> find = mongoTemplate.find(query, ColUser.class);
		return find;
	}

	@Override
	public List<ColUser> findAngentAll(Integer agent_level) {
		Criteria criatira = Criteria.where("agent_state").is(agent_level);
		Query query = new Query(criatira);
		List<ColUser> find = mongoTemplate.find(query, ColUser.class);
		return find;
	}

}
