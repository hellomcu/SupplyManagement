package com.supply.management.module.order.repository;

import java.util.List;
import java.util.Map;

import com.supply.management.base.repository.Repository;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.OrderPo;

public interface OrderRepository extends Repository
{
	
	List<OrderPo> findAll(PageInfo pageInfo);
	
	int update(Map<String, Object> fields, long id);
}
