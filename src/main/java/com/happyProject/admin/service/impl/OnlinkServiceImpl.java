package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.OnlinkDaoImpl;
import com.happyProject.admin.model.Onlink;
import com.happyProject.admin.model.OrderWeekNumber;
import com.happyProject.admin.service.OnlinkService;

@Repository
public class OnlinkServiceImpl implements OnlinkService{
	@Resource
	private OnlinkDaoImpl onlinkDaoImpl;

	@Override
	public Onlink AddAnaUpdate(Onlink t) {
		return onlinkDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(Onlink t) {
		onlinkDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(Onlink t) {
		return onlinkDaoImpl.getAll(t);
	}

	@Override
	public int getCount(Onlink t) {
		return onlinkDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, Onlink t) {
		onlinkDaoImpl.updateById(id, map, t);
	}

	@Override
	public Onlink findById(Object id, Onlink t) {
		return onlinkDaoImpl.findById(id, t);
	}

	@Override
	public List<OrderWeekNumber> findByCtime(Integer last, Integer end) {
		return onlinkDaoImpl.findByCtime(last, end);
	}
}
