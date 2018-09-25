package com.happyProject.admin.dao;

import java.util.List;

import com.happyProject.admin.base.dao.BaseDao;
import com.happyProject.admin.model.Onlink;
import com.happyProject.admin.model.OrderWeekNumber;

public interface OnlinkDao extends BaseDao<Onlink> {
	List<OrderWeekNumber> findByCtime(Integer last, Integer end);
}
