package com.supply.management.module.cart.repository;

import java.util.Map;

import com.supply.base.repository.Repository;

public interface CartRepository extends Repository
{

	int save(long userId, long productId, long productNum);

	Map<Object, Object> findByUserId(long userId);
	
	int update(long userId, long productId, long productNum);
	
	int remove(long userId);
	
	int remove(long userId, long productId);
}
