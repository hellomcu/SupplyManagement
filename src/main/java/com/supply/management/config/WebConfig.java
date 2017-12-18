package com.supply.management.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

@Configuration
@EnableWebMvc
@ComponentScan(basePackages = {"com.e.management"})
public class WebConfig extends WebMvcConfigurerAdapter
{
	/**
	 * 配置JSP视图解析器
	 * @return
	 */
	@Bean
	public ViewResolver viewResolver() 
	{
		InternalResourceViewResolver resolver = new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".html");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	
	/**
	 * 配置静态资源的处理
	 */
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer)
	{
		configurer.enable();
	}
	
	/**
	 * 配置文件上传处理
	 * @return
	 */
	@Bean
	public StandardServletMultipartResolver resolver() {
		return new StandardServletMultipartResolver();
	}
	
//	@Bean
//	public ObjectMapper getObjectMapper()
//	{
//		ObjectMapper objectMapper = new ObjectMapper();
//		objectMapper.configure(SerializationFeature.WRITE_ENUMS_USING_INDEX, true);
//		return objectMapper;
//	}
}

