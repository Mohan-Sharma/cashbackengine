package com.landmarkshops.cashbackengine.cashbackengine.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.domain.AuditorAware;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.mongodb.config.EnableMongoAuditing;

/**
 * @author Mohan Sharma Created on 12/07/18.
 */
@Configuration
@EnableMongoAuditing(auditorAwareRef = "auditorAware")
@EnableJpaAuditing(auditorAwareRef = "auditorAware")
public class AuditingConfig
{
	@Bean
	public AuditorAware<String> auditorAware() {
		return new AuditorAwareImpl();
	}
}
