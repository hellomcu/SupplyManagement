package com.supply.management.module.store.service;

import java.util.List;

import com.supply.management.base.service.BaseService;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.StorePo;
import com.supply.management.entity.po.UserPo;

public interface StoreService extends BaseService
{
	void addStore(StorePo store, UserPo user);
	
	List<StorePo> findAllStore(PageInfo page);
	
	void deleteStore(long id);
	
	void updateStore(StorePo store, UserPo user);
}
