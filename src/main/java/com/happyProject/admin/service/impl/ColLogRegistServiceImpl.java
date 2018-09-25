package com.happyProject.admin.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.ColLogRegistDaoImpl;
import com.happyProject.admin.model.ColLogRegist;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.service.ColLogRegistService;

@Repository
public class ColLogRegistServiceImpl implements ColLogRegistService{
	
	@Resource
	private ColLogRegistDaoImpl colLogRegistDao;
	
	@Override
	public ColLogRegist AddAnaUpdate(ColLogRegist t) {
		return colLogRegistDao.AddAnaUpdate(t);
	}

	@Override
	public void remove(ColLogRegist t) {
		colLogRegistDao.remove(t);
	}

	@Override
	public List<? extends Object> getAll(ColLogRegist t) {
		return colLogRegistDao.getAll(t);
	}

	@Override
	public int getCount(ColLogRegist t) {
		return colLogRegistDao.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, ColLogRegist t) {
		colLogRegistDao.updateById(id, map, t);
	}

	@Override
	public ColLogRegist findById(Object id, ColLogRegist t) {
		return colLogRegistDao.findById(id, t);
	}

	@Override
	public OrderWeekNumber getOrderNumber(Date time, Integer start, Integer last) {
		return colLogRegistDao.getOrderNumber(time, start, last);
	}

}
