package com.supply.management.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.supply.entity.po.CartPo;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
@PropertySource("classpath:redis.properties")
public class RedisConfig
{
	@Autowired
	private Environment env;
	
	
	@Bean
	public JedisPoolConfig jedisPoolConfig()
	{
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(env.getRequiredProperty("redis.maxIdel", Integer.class));
		jedisPoolConfig.setMaxWaitMillis(env.getRequiredProperty("redis.maxWait", Integer.class));
		jedisPoolConfig.setTestOnBorrow(env.getRequiredProperty("redis.testOnBorrow", Boolean.class));
	
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig, int redisDatabase)
	{
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName(env.getRequiredProperty("redis.host", String.class));
		jedisConnectionFactory.setPort(env.getRequiredProperty("redis.port", Integer.class));
		jedisConnectionFactory.setTimeout(env.getRequiredProperty("redis.timeout", Integer.class));
		jedisConnectionFactory.setDatabase(redisDatabase);
		return jedisConnectionFactory;
	}

	@Bean
	public RedisTemplate<Long, CartPo> redisTemplate(JedisConnectionFactory jedisConnectionFactory)
	{
		RedisTemplate<Long, CartPo> redisTemplate = new RedisTemplate<>();
		redisTemplate.setConnectionFactory(jedisConnectionFactory);
		return redisTemplate;
	}
	
	@Profile("prod")
	@Bean
	public int redisDatabaseForProd()
	{
		return 0;
	}
	
	@Profile("dev")
	@Bean
	public int redisDatabaseForDev()
	{
		return 1;
	}
	
}
