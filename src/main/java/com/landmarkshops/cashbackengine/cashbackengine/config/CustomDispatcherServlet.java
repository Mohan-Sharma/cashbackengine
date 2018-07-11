package com.landmarkshops.cashbackengine.cashbackengine.config;

import org.springframework.boot.autoconfigure.web.DispatcherServletAutoConfiguration;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.servlet.DispatcherServlet;

/**
 * @author Mohan Sharma Created on 13/07/18.
 */
@Configuration
public class CustomDispatcherServlet
{
	@Bean
	public ServletRegistrationBean dispatcherServlet()
	{
		AnnotationConfigWebApplicationContext appContext = new AnnotationConfigWebApplicationContext();
		appContext.setConfigLocation("com.landmarkshops.cashbackengine.cashbackengine.config");
		DispatcherServlet dispatcherServlet = new DispatcherServlet();
		dispatcherServlet.setApplicationContext(appContext);
		ServletRegistrationBean registrationBean = new ServletRegistrationBean(dispatcherServlet, "/");
		registrationBean.setLoadOnStartup(1);
		registrationBean.setName(DispatcherServletAutoConfiguration.DEFAULT_DISPATCHER_SERVLET_REGISTRATION_BEAN_NAME);
		return registrationBean;
	}
}
