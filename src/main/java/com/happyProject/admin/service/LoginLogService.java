package com.happyProject.admin.service;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.LoginLog;
import com.happyProject.admin.model.PageBean;

public interface LoginLogService extends BaseDao<LoginLog>{
	public Integer getTotal(String userid);
	
	PageBean<LoginLog> findByDataAndCount(Integer currentPage, Integer pageSize,String userid);

}
