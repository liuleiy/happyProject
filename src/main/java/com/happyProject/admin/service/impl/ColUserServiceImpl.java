package com.happyProject.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.ColUserDaoImpl;
import com.happyProject.admin.model.ColUser;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.ColUserService;

@Repository
public class ColUserServiceImpl implements ColUserService {
	@Resource
	private ColUserDaoImpl colUserDao;
	
	@Override
	public ColUser AddAnaUpdate(ColUser t) {
		return colUserDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(ColUser t) {
		colUserDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(ColUser t) {
		return colUserDao.getAll(t);
	}

	@Override
	public int getCount(ColUser t) {
		return colUserDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, ColUser t) {
		colUserDao.updateById(id, map, t);
	}

	@Override
	public ColUser findById(Object id, ColUser t) {
		return colUserDao.findById(id, t);
	}

	@Override
	public PageBean<ColUser> findByDataAndCount(String iid, String nickname, Integer agent_level, Integer currentPage,
			Integer pageSize, Long startTime, Long endTime,String agent,String start_profit,String last_profit,String start_bring_profit,
			String last_bring_profit,Integer rtypeValue,String arrangement) {
		return colUserDao.findByDataAndCount(iid, nickname, agent_level, currentPage, pageSize, startTime, endTime,agent,start_profit, last_profit, start_bring_profit,
				last_bring_profit,rtypeValue,arrangement);
	}

	@Override
	public Integer findByStatusCount(Integer status, Date time, Integer start, Integer last) {
		return colUserDao.findByStatusCount(status, time, start, last);
	}

	@Override
	public Integer getAgentNumber(Integer state, Integer end, Integer agent_state, Integer dayMonth) {
		return colUserDao.getAgentNumber(state, end, agent_state, dayMonth);
	}

	@Override
	public Integer getDayAgentNumber(Integer state, Integer end) {
		return colUserDao.getDayAgentNumber(state, end);
	}

	@Override
	public Integer getYesterdayAgentNumber(Integer state, Integer end) {
		return colUserDao.getYesterdayAgentNumber(state, end);
	}

	@Override
	public Integer getThisMonthAgentNumber() {
		return colUserDao.getThisMonthAgentNumber();
	}

	@Override
	public Integer count(ColUser colUser, Map<String, Object> map) {
		return colUserDao.count(colUser, map);
	}

	@Override
	public List<ColUser> getAgent(String agent_state) {
		return colUserDao.getAgent(agent_state);
	}

	@Override
	public List<ColUser> findAngentAll(Integer agent_level) {
		return colUserDao.findAngentAll(agent_level);
	}

}
