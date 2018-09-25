package com.happyProject.admin.service;

import com.happyProject.admin.base.service.BaseService;
import com.happyProject.admin.model.Goods;
import com.happyProject.admin.model.PageBean;

public interface GoodsService extends BaseService<Goods> {
	PageBean<Goods> findByDataAndCount(String name, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,Integer del);
}
