//package com.supply.management;
//
//import org.junit.Test;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.redis.connection.RedisConnectionFactory;
//import org.springframework.data.redis.connection.jedis.JedisConnectionFactory;
//import org.springframework.data.redis.core.RedisTemplate;
//
//import com.supply.entity.po.CartPo;
//import com.supply.management.util.Md5;
//
//import junit.framework.TestCase;
//
//public class MyTest extends TestCase
//{
//	@Autowired
//	private RedisTemplate<Long, CartPo> redisTemplate;
//
//	@Test
//	public void testMd5()
//	{
//		System.out.println(Md5.getMD5Str("admin123456"));
//	}
//
//	@Test
//	public void testJedis()
//	{
////		Jedis jedis = new Jedis("139.196.166.177", 6379);
////		jedis.rename("singleJedis11111111", "hello jedis!111111");
////		System.out.println(jedis.get("1"));
////		jedis.close();
//		redisTemplate = new RedisTemplate<>();
//		JedisConnectionFactory redisConnectionFactory = new JedisConnectionFactory();
//		redisConnectionFactory.setHostName("139.196.166.177");
//		redisConnectionFactory.setPort(6379);
//		redisTemplate.setConnectionFactory(redisConnectionFactory);
//		redisTemplate.opsForHash().put(new Long(1), new Long(1), new Long(1));
//
//	}
//}
