package com.landmarkshops.cashbackengine.cashbackengine.config;

import java.net.UnknownHostException;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.authentication.UserCredentials;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

import com.mongodb.Mongo;
import com.mongodb.MongoClient;


/**
 * @author Mohan Sharma Created on 13/07/18.
 */
@Configuration
@EnableMongoRepositories(basePackages={"com.landmarkshops.cashbackengine.cashbackengine.persistence"})
public class CustomMongoDbConfiguration extends AbstractMongoConfiguration
{
	@Value("${spring.data.mongodb.hostname}")
	private String hostName;

	@Value("${spring.data.mongodb.port}")
	private int port;

	@Value("${spring.data.mongodb.username}")
	private String username;

	@Value("${spring.data.mongodb.password}")
	private String password;
	
	@Value("${spring.data.mongodb.database}")
	private String databaseName;


	@Bean
	public MongoDbFactory mongoDbFactory() throws Exception {
		UserCredentials userCredentials = new UserCredentials(username, password);
		return new SimpleMongoDbFactory(mongo(), databaseName, userCredentials);
	}

	@Bean
	public Mongo mongo() throws UnknownHostException
	{
		return new MongoClient(hostName, port);
	}

	@Bean
	public MongoTemplate mongoTemplate() throws Exception {
		return new MongoTemplate(mongoDbFactory());
	}

	@Override
	protected String getDatabaseName() {
		return databaseName;
	}

	@Override
	protected String getMappingBasePackage() {
		return "com.landmarkshops.cashbackengine.cashbackengine.domain.model";
	}
}