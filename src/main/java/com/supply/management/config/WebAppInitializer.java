package com.supply.management.config;

import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletRegistration;

import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer
{
	@Override
	protected WebApplicationContext createServletApplicationContext()
	{
		XmlWebApplicationContext ctx = new XmlWebApplicationContext();
		ctx.setConfigLocation("classpath:spring-mvc.xml");
		return ctx;
	}
	

	
	@Override
	protected void customizeRegistration(ServletRegistration.Dynamic registration)
	{
		registration.setInitParameter("spring.profiles.active", "prod");
		registration.setMultipartConfig(getMultipartConfigElement());
	}


	@Override
	protected String[] getServletMappings()
	{
		return new String[] { "/" };
	}

	@Override
	protected Class<?>[] getServletConfigClasses()
	{
		return new Class<?>[] { WebConfig.class };
	}

	
	private MultipartConfigElement getMultipartConfigElement()
	{
		MultipartConfigElement multipartConfigElement = new MultipartConfigElement(LOCATION, MAX_FILE_SIZE,
				MAX_REQUEST_SIZE, FILE_SIZE_THRESHOLD);
		return multipartConfigElement;
	}

	private static final String LOCATION = "/tmp"; // Temporary location
													// where files will be
													// stored

	private static final long MAX_FILE_SIZE = 5242880; // 5MB : Max file size.
														// Beyond that size
														// spring will throw
														// exception.
	private static final long MAX_REQUEST_SIZE = 20971520; // 20MB : Total
															// request size
															// containing Multi
															// part.

	private static final int FILE_SIZE_THRESHOLD = 0; // Size threshold after
														// which files will be
														// written to disk

	@Override
	protected Class<?>[] getRootConfigClasses()
	{
		return null;
	}
}
