package com.supply.management.module.cart.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import com.supply.entity.po.CartPo;
import com.supply.management.module.cart.repository.CartRepository;

@Repository(value = "cartRepository")
public class CartRepositoryRedisImpl implements CartRepository
{
	@Autowired
	private RedisTemplate<Long, CartPo> redisTemplate;

	@Override
	public int save(CartPo cartPo)
	{
		redisTemplate.opsForValue().set(cartPo.getUserId(), cartPo);
		return 1;
	}

	@Override
	public CartPo findByUserId(long userId)
	{
		return redisTemplate.opsForValue().get(userId);
	}

	
	
}