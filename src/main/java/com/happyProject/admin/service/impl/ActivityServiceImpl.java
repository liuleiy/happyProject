package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.ActivityDaoImpl;
import com.happyProject.admin.model.Activity;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.ActivityService;

@Repository
public class ActivityServiceImpl implements ActivityService{
	@Resource
	private ActivityDaoImpl activityDao;
	@Override
	public Activity AddAnaUpdate(Activity t) {
		return activityDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(Activity t) {
		activityDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Activity t) {
		return activityDao.getAll(t);
	}

	@Override
	public int getCount(Activity t) {
		return activityDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Activity t) {
		activityDao.updateById(id, map, t);
	}

	@Override
	public PageBean<Activity> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime) {
		return activityDao.findByDataAndCount(currentPage, pageSize, startTime, endTime);
	}

	@Override
	public Activity findById(Object id, Activity t) {
		return activityDao.findById(id, t);
	}

}
