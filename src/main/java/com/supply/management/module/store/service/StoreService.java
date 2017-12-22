package com.supply.management.module.store.service;

import com.supply.base.service.BaseService;
import com.supply.entity.PageInfo;
import com.supply.entity.po.StorePo;
import com.supply.entity.po.UserPo;

public interface StoreService extends BaseService
{
	void addStore(StorePo store, UserPo user);
	
	PageInfo<StorePo> findAllStore(PageInfo<Void> page);
	
	void deleteStore(long id);
	
	void updateStore(StorePo store, UserPo user);
}
