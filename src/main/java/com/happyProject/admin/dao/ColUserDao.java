package com.happyProject.admin.dao;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.ColUser;
import com.happyProject.admin.model.PageBean;

public interface ColUserDao extends BaseDao<ColUser> {

	PageBean<ColUser> findByDataAndCount(String iid, String nickname, Integer agent_level, Integer currentPage,
			Integer pageSize, Long startTime, Long endTime,String agent,String start_profit,String last_profit,String start_bring_profit,
			String last_bring_profit,Integer rtypeValue,String arrangement);
	Integer findByStatusCount(Integer status, Date time, Integer start,
			Integer last);
	Integer getAgentNumber(Integer state, Integer end,
			Integer agent_state, Integer dayMonth);
	Integer getDayAgentNumber(Integer state, Integer end);

	Integer getYesterdayAgentNumber(Integer state, Integer end);

	Integer getThisMonthAgentNumber();
	
	Integer count(ColUser colUser, Map<String, Object> map);
	List<ColUser> getAgent(String agent_state);
	List<ColUser> findAngentAll(Integer agent_level);
}
