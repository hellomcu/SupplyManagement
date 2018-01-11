package com.supply.management.module.cart.service;

import com.supply.base.service.BaseService;
import com.supply.entity.po.CartPo;


public interface CartService extends BaseService
{
	void addCart(CartPo cartPo);
	
	CartPo findMyCart(long userId);
	
	void updateCart(long userId, long productId, long productNum);
	
	void deleteCart(long userId, long productId);
	
	void clearCart(long userId);
}
