package com.supply.management.module.payment.repository;

import com.supply.base.repository.Repository;
import com.supply.entity.po.RechargePo;

public interface RechargeRepository extends Repository
{

	int save(RechargePo rechargePo);

}
