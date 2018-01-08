package com.supply.management.module.payment.service.impl;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supply.entity.po.RechargePo;
import com.supply.exception.SupplyException;
import com.supply.management.module.payment.repository.RechargeRepository;
import com.supply.management.module.payment.service.RechargeService;
import com.supply.management.module.store.repository.StoreRepository;
import com.supply.management.module.user.repository.UserRepository;

@Service
public class RechargeServiceImpl implements RechargeService
{
	private RechargeRepository mRechargeRepository;

	private UserRepository mUserRepository;
	
	private StoreRepository mStoreRepository;

	@Transactional
	@Override
	public void recharge(RechargePo rechargePo)
	{
		int rows = mRechargeRepository.save(rechargePo);
		if (rows != 1)
		{
			throw new SupplyException("充值失败,请稍后重试");
		}
//		rows = mUserRepository.addBalance(rechargePo.getAmount(), rechargePo.getUserId());
//		if (rows != 1)
//		{
//			throw new SupplyException("充值失败,请稍后重试");
//		}
		rows = mStoreRepository.addBalance(rechargePo.getAmount(), rechargePo.getStoreId());
		if (rows != 1)
		{
			throw new SupplyException("充值失败,请稍后重试");
		}
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

	@Resource(name="storeRepository")
	public void setStoreRepository(StoreRepository storeRepository)
	{
		this.mStoreRepository = storeRepository;
	}


	
}
