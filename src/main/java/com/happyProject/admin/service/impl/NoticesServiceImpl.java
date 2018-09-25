package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.NoticesDaoImpl;
import com.happyProject.admin.model.Notices;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.NoticesService;
@Repository
public class NoticesServiceImpl implements NoticesService{
	@Resource
	private NoticesDaoImpl noticesDao;
	@Override
	public Notices AddAnaUpdate(Notices t) {
		return noticesDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(Notices t) {
		noticesDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Notices t) {
		return noticesDao.getAll(t);
	}

	@Override
	public int getCount(Notices t) {
		return noticesDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Notices t) {
		noticesDao.updateById(id, map, t);
	}

	@Override
	public PageBean<Notices> findByDataAndCount(Integer rtype, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime) {
		return noticesDao.findByDataAndCount(rtype, currentPage, pageSize, startTime, endTime);
	}

	@Override
	public Notices findById(Object id, Notices t) {
		return noticesDao.findById(id, t);
	}

}
