package com.supply.management.module.store.repository;

import java.util.List;

import com.supply.base.repository.Repository;
import com.supply.entity.PageInfo;
import com.supply.entity.po.StorePo;

public interface StoreRepository extends Repository
{
	int save(StorePo store);
	
	List<StorePo> findAll(PageInfo pageInfo);
	
	int delete(long id);
	
	int update(StorePo store);
}
