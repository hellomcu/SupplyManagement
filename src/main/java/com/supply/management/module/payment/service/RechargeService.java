package com.supply.management.module.payment.service;

import com.supply.base.service.BaseService;
import com.supply.entity.po.RechargePo;


public interface RechargeService extends BaseService
{
	void recharge(RechargePo rechargePo);
}
