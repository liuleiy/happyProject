package com.happyProject.admin.mongo;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.core.convert.DefaultDbRefResolver;
import org.springframework.data.mongodb.core.convert.DefaultMongoTypeMapper;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.happyProject.admin.base.dao.impl.MongoBaseDaoImpl;
import com.mongodb.MongoClient;

@Configuration
@EnableMongoRepositories(basePackages = { "com.happyProject.admin.model",
		"com.happyProject.admin.dao" }, mongoTemplateRef = "diseaseMongoTemplate", repositoryBaseClass = MongoBaseDaoImpl.class)
public class MongodbConfig {
	
//	 	@Autowired
//	    private ConfigProperties configProperties;

	@Value("${spring.datasource.mongo.url}")
	private String mongoUri;

	@Value("${spring.data.mongodb.database}")
	private String dbName;
	    
	@Bean(name = "diseaseMongoTemplate")
	public MongoTemplate diseaseMongoTemplate() throws Exception {
		MappingMongoConverter converter = new MappingMongoConverter(new DefaultDbRefResolver(mongodbFactory()),
				new MongoMappingContext());

		converter.setTypeMapper(new DefaultMongoTypeMapper(null));

		return new MongoTemplate(mongodbFactory(), converter);
	}

	@Bean
	public MongoDbFactory mongodbFactory() throws Exception {
		//MongoClientURI mongoClientUri = new MongoClientURI(mongoUri);
		return new SimpleMongoDbFactory(new MongoClient(mongoUri), dbName);
	}

}
