package com.happyProject.admin.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.base.service.impl.BaseServiceImpl;
import com.happyProject.admin.dao.impl.GoodsDaoImpl;
import com.happyProject.admin.model.Goods;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.GoodsService;

@Repository
public class GoodsServiceImpl extends BaseServiceImpl<Goods> implements GoodsService {
	@Resource
	private GoodsDaoImpl goodsDaoImpl;

	@Override
	public PageBean<Goods> findByDataAndCount(String name, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,Integer del) {
		return goodsDaoImpl.findByDataAndCount(name, currentPage, pageSize, startTime, endTime, del);
	}
	

}
