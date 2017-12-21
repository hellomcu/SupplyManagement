package com.supply.management.module.order.repository;

import java.util.List;
import java.util.Map;

import com.supply.base.repository.Repository;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderPo;

public interface OrderRepository extends Repository
{
	
	List<OrderPo> findAll(PageInfo pageInfo);
	
	long count();
	
	int update(Map<String, Object> fields, long id);
}
