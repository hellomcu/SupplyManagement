package com.supply.management.db;

import java.io.InputStream;
import java.util.Properties;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;

@Configuration
@PropertySource("classpath:db.properties")
public class DBConfig
{
	
	
	@Profile("prod")
	@Bean(name="dataSource", initMethod="init", destroyMethod="close")
	public DataSource dataSourceForProd() throws Exception
	{
		Properties properties = new Properties();
		InputStream in = DBConfig.class.getClassLoader().getResourceAsStream("db.properties");
		properties.load(in);
		DataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

		return dataSource;
	}
	
	
	@Profile("dev")
	@Bean(name="dataSource", initMethod="init", destroyMethod="close")
	public DataSource dataSourceForDev() throws Exception
	{
		Properties properties = new Properties();
		InputStream in = DBConfig.class.getClassLoader().getResourceAsStream("db-dev.properties");
		properties.load(in);
		DataSource dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);

		return dataSource;
	}
}
