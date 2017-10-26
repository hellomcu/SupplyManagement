package com.supply.management.module.store.repository;

import java.util.List;

import com.supply.management.base.repository.Repository;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.StorePo;

public interface StoreRepository extends Repository
{
	int save(StorePo store);
	
	List<StorePo> findAll(PageInfo pageInfo);
	
	int delete(long id);
	
	int update(StorePo store);
}
