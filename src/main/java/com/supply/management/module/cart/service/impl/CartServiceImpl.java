package com.supply.management.module.cart.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supply.entity.po.CartPo;
import com.supply.exception.SupplyException;
import com.supply.management.module.cart.repository.CartRepository;
import com.supply.management.module.cart.service.CartService;
import com.supply.management.module.payment.repository.RechargeRepository;
import com.supply.management.module.store.repository.StoreRepository;
import com.supply.management.module.user.repository.UserRepository;

@Service
public class CartServiceImpl implements CartService
{
	private CartRepository mCartRepository;

	private RechargeRepository mRechargeRepository;

	private UserRepository mUserRepository;

	private StoreRepository mStoreRepository;

	@Override
	public void createCart(CartPo cartPo)
	{
		CartPo exist = mCartRepository.findByUserId(cartPo.getUserId());
		if (exist == null)
		{
			int rows = mCartRepository.save(cartPo);
			if (rows != 1)
			{
				throw new SupplyException("创建购物车失败");
			}
		}
		else
		{
			//exist
		}
	}

	@Override
	public CartPo findMyCart(long userId)
	{
		return mCartRepository.findByUserId(userId);
	}

	@Resource(name = "cartRepository")
	public void setCartRepository(CartRepository cartRepository)
	{
		this.mCartRepository = cartRepository;
	}

	@Resource(name = "rechargeRepository")
	public void setRechargeRepository(RechargeRepository rechargeRepository)
	{
		this.mRechargeRepository = rechargeRepository;
	}

	@Resource(name = "userRepository")
	public void setUserRepository(UserRepository userRepository)
	{
		this.mUserRepository = userRepository;
	}

	@Resource(name = "storeRepository")
	public void setStoreRepository(StoreRepository storeRepository)
	{
		this.mStoreRepository = storeRepository;
	}

}
