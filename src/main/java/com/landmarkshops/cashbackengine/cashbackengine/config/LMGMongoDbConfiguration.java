package com.landmarkshops.cashbackengine.cashbackengine.config;

import java.util.Arrays;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

import com.mongodb.MongoClient;
import com.mongodb.MongoCredential;
import com.mongodb.ServerAddress;

@Configuration
public class LMGMongoDbConfiguration extends AbstractMongoConfiguration
{
	@Value("${db.hostname}")
	private String hostName;

	@Value("${db.port}")
	private int port;

	@Value("${db.username}")
	private String username;

	@Value("${db.password}")
	private String password;
	
	@Value("${db.name}")
	private String databaseName;

	@Override
	protected String getDatabaseName()
	{
		return databaseName;
	}


	@Override
	public MongoClient mongo()
	{
		ServerAddress address = new ServerAddress(hostName, port);
		MongoCredential credential = MongoCredential.createCredential(username, databaseName, password.toCharArray());
		return new MongoClient(address, Arrays.asList(credential));
	}
}