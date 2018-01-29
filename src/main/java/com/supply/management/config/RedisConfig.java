package com.supply.management.config;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;

import com.supply.entity.po.CartPo;

import redis.clients.jedis.JedisPoolConfig;

@Configuration
public class RedisConfig
{
	
	@Bean
	public JedisPoolConfig jedisPoolConfig()
	{
		JedisPoolConfig jedisPoolConfig = new JedisPoolConfig();
		jedisPoolConfig.setMaxIdle(100);
		jedisPoolConfig.setMaxWaitMillis(1000);
		jedisPoolConfig.setTestOnBorrow(true);
	
		return jedisPoolConfig;
	}

	@Bean
	public JedisConnectionFactory jedisConnectionFactory(JedisPoolConfig jedisPoolConfig, int redisDatabase)
	{
		JedisConnectionFactory jedisConnectionFactory = new JedisConnectionFactory();
		jedisConnectionFactory.setHostName("139.196.166.177");
		jedisConnectionFactory.setPort(6379);
		jedisConnectionFactory.setTimeout(10000);
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
