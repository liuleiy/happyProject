package com.happyProject.admin.base.service;

import java.util.List;
import java.util.Map;

public interface MongoBaseService<T> {
	// 增加与修改
	T AddAnaUpdate(T t);

	// 删除
	void remove(T t);

	// 查询全部
	List<? extends Object> getAll(T t);

	// 查看全部条数
	int getCount(T t);
	
	// 根据Id修改 map集合中key是字段，value是字段需要修改的值
	void updateById(Object id, Map<String, Object> map, T t);
}
