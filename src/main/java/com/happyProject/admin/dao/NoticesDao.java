package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Notices;
import com.happyProject.admin.model.PageBean;

public interface NoticesDao extends BaseDao<Notices> {
	PageBean<Notices> findByDataAndCount(Integer rtype,Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);
}
