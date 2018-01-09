package com.supply.management.module.cart.service;

import com.supply.base.service.BaseService;
import com.supply.entity.po.CartPo;


public interface CartService extends BaseService
{
	void createCart(CartPo cartPo);
	
	CartPo findMyCart(long userId);
}
