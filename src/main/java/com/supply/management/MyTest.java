package com.supply.management;

import org.junit.Test;

import com.supply.management.util.Md5;

import junit.framework.TestCase;
import redis.clients.jedis.Jedis;

public class MyTest extends TestCase
{

	@Test
	public void testMd5()
	{
		System.out.println(Md5.getMD5Str("admin123456"));
	}

	@Test
	public void testJedis()
	{
		Jedis jedis = new Jedis("139.196.166.177", 6379);
		jedis.rename("singleJedis11111111", "hello jedis!111111");
		System.out.println(jedis.get("1"));
		jedis.close();

	}
}
