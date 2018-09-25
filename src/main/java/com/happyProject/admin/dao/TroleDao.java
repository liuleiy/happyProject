package com.happyProject.admin.dao;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.model.Trole;

public interface TroleDao extends BaseDao<Trole>{
	PageBean<Trole> findByDataAndCount(Integer currentPage, Integer pageSize);
}
