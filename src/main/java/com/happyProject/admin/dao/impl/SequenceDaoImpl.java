package com.happyProject.admin.dao.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

import com.happyProject.admin.dao.SequenceDao;
import com.happyProject.admin.model.Sequence;

@Repository
public class SequenceDaoImpl implements SequenceDao{
	@Resource
	private MongoTemplate mongoTemplate;
	
	@Override
	public String nextId(String idValue) {
		List<Sequence> find = mongoTemplate.find(new Query(Criteria
				.where("id").is(idValue)) , Sequence.class);
		if(find != null && find.size() > 0){
			Sequence sequence = find.get(0);
			String value = sequence.getValue();
			int parseInt = Integer.parseInt(value)+1;
			String nextId = String.valueOf(parseInt);
			return nextId;
		}
		return null;
	}

	@Override
	public void nextOne(String idValue,String value) {
		Update update = new Update();
		update.set("value", value);
		mongoTemplate.updateMulti(new Query(Criteria
				.where("id").is(idValue)) , update, Sequence.class);
	}

}
