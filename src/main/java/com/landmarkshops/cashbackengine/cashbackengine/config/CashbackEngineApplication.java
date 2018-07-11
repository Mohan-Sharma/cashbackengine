package com.landmarkshops.cashbackengine.cashbackengine.config;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.data.mongo.MongoDataAutoConfiguration;
import org.springframework.boot.autoconfigure.mongo.MongoAutoConfiguration;
import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication(exclude = {MongoAutoConfiguration.class, MongoDataAutoConfiguration.class, DispatcherServletAutoConfiguration.class})
@EnableTransactionManagement
@EnableWebMvc
@ComponentScan(basePackages = {"com.landmarkshops.cashbackengine.cashbackengine"})
public class CashbackEngineApplication
{
	public static void main(String[] args) {
		SpringApplication.run(CashbackEngineApplication.class, args);
	}
}
