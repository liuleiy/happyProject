package com.happyProject.admin.service;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.Trole;

public interface TroleService extends BaseDao<Trole>{
	PageBean<Trole> findByDataAndCount(Integer currentPage, Integer pageSize);
}
