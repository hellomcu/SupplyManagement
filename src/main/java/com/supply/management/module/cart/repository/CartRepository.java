package com.supply.management.module.cart.repository;

import com.supply.base.repository.Repository;
import com.supply.entity.po.CartPo;

public interface CartRepository extends Repository
{

	int save(CartPo cartPo);

	CartPo findByUserId(long userId);
	
	int update(long userId);
}
