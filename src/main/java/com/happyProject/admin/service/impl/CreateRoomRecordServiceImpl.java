package com.happyProject.admin.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.impl.CreateRoomRecordDaoImpl;
import com.happyProject.admin.model.CreateRoomRecord;
import com.happyProject.admin.model.PageBean;
import com.happyProject.admin.service.CreateRoomRecordService;

@Repository
public class CreateRoomRecordServiceImpl implements CreateRoomRecordService{
	
	@Resource
	private CreateRoomRecordDaoImpl createRoomRecordDaoImpl;
	
	@Override
	public CreateRoomRecord AddAnaUpdate(CreateRoomRecord t) {
		return createRoomRecordDaoImpl.AddAnaUpdate(t);
	}

	@Override
	public void remove(CreateRoomRecord t) {
		createRoomRecordDaoImpl.remove(t);
	}

	@Override
	public List<? extends Object> getAll(CreateRoomRecord t) {
		return createRoomRecordDaoImpl.getAll(t);
	}

	@Override
	public int getCount(CreateRoomRecord t) {
		return createRoomRecordDaoImpl.getCount(t);
	}

	@Override
	public void updateById(Object id, Map<String, Object> map, CreateRoomRecord t) {
		createRoomRecordDaoImpl.updateById(id, map, t);
	}

	@Override
	public PageBean<CreateRoomRecord> findByDataAndCount(String code, Integer currentPage, Integer pageSize) {
		return createRoomRecordDaoImpl.findByDataAndCount(code, currentPage, pageSize);
	}

	@Override
	public CreateRoomRecord findById(Object id, CreateRoomRecord t) {
		return createRoomRecordDaoImpl.findById(id, t);
	}

}
