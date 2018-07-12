package com.landmarkshops.cashbackengine.cashbackengine.config;

import org.springframework.data.domain.AuditorAware;

/**
 * @author Mohan Sharma Created on 12/07/18.
 */
public class AuditorAwareImpl implements AuditorAware<String>
{
	@Override
	public String getCurrentAuditor()
	{
		return "admin";
	}
}
