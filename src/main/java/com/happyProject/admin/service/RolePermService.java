package com.happyProject.admin.service;

import java.util.List;
import java.util.Map;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.RolePerm;

public interface RolePermService extends BaseDao<RolePerm>{
	List<RolePerm> findByMap(Integer currentPage, Integer pageSize,
			Map<String, Object> map);
	
}
