package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.TUser;

public interface TUserDao extends BaseDao<TUser>{
	PageBean<TUser> findByDataAndCount(Integer currentPage, Integer pageSize, Long startTime,
			Long endTime);
	TUser login(String username,String password);
}
