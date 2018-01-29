package com.supply.management.module.cart.repository.impl;

import java.util.Map;

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
	public int save(long userId, long productId, long productNum)
	{
		// List<CartDetailPo> details = cartPo.getDetails();
		//
		// if (details != null)
		// {
		// int size = details.size();
		// if (size != 0)
		// {
		// for (CartDetailPo detailPo : details)
		// {
		// redisTemplate.opsForHash().put(Long.toString(cartPo.getUserId()),
		// detailPo.getProductId(),
		// detailPo);
		// }
		// return size;
		// }
		// }
		redisTemplate.opsForHash().put(new Long(userId), new Long(productId), new Long(productNum));
		return 1;
	}

	@Override
	public Map<Object, Object> findByUserId(long userId)
	{
		return redisTemplate.opsForHash().entries(new Long(userId));
	}

	@Override
	public int update(long userId, long productId, long productNum)
	{
		redisTemplate.opsForHash().put(new Long(userId), new Long(productId), new Long(productNum));
		return 1;
	}

	@Override
	public int remove(long userId)
	{
		redisTemplate.opsForHash().getOperations().delete(userId);
		return 1;
	}

	@Override
	public int remove(long userId, long productId)
	{
		if (redisTemplate.opsForHash().delete(userId, new Long[] { productId }) == 1L)
			return 1;
		return 0;
	}

	@Override
	public boolean has(long userId)
	{
		return redisTemplate.hasKey(new Long(userId));
	}

	@Override
	public long getProductNum(long userId, long productId)
	{
		Object object = redisTemplate.opsForHash().get(userId, new Long(productId));
		return object == null ? 0L : (Long)object;
	}

	
	
}
