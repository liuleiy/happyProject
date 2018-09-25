package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.LuckyDaoImpl;
import com.happyProject.admin.model.Lucky;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.LuckyService;

@Repository
public class LuckyServiceImpl implements LuckyService{
	@Resource
	private LuckyDaoImpl luckyDao;

	@Override
	public Lucky AddAnaUpdate(Lucky t) {
		luckyDao.AddAnaUpdate(t);
		return t;
	}

	@Override
	public void remove(Lucky t) {
		luckyDao.remove(t);
		
	}

	@Override
	public List<? extends Object> getAll(Lucky t) {
		return luckyDao.getAll(t);
	}

	@Override
	public int getCount(Lucky t) {
		return luckyDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Lucky t) {
		luckyDao.updateById(id, map, t);
	}

	@Override
	public PageBean<Lucky> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime, Long endTime) {
		return luckyDao.findByDataAndCount(currentPage, pageSize, startTime, endTime);
	}

	@Override
	public Lucky findById(Object id, Lucky t) {
		return luckyDao.findById(id, t);
	}

	@Override
	public Lucky isLuckid(String fieldName, Object value) {
		return luckyDao.isLuckid(fieldName, value);
	}
	
}
