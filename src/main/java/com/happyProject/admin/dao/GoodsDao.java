package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Goods;
import com.happyProject.admin.model.PageBean;

public interface GoodsDao extends BaseDao<Goods> {
	PageBean<Goods> findByDataAndCount(String name, Integer currentPage, Integer pageSize, Long startTime,
			Long endTime,Integer del);
}
