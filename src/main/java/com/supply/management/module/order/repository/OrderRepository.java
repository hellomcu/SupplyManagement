package com.supply.management.module.order.repository;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.supply.base.repository.Repository;
import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderDetailPo;
import com.supply.entity.po.OrderPo;

public interface OrderRepository extends Repository
{
	
	List<OrderPo> findAll(PageInfo pageInfo, OrderStatus status);
	
	long count(OrderStatus status);
	
	int update(Map<String, Object> fields, long id);
	
	int save(OrderPo order);
	
	int[] saveDetails(long orderId,List<OrderDetailPo> details);
}
