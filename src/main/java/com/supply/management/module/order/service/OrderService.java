package com.supply.management.module.order.service;

import java.util.List;
import java.util.Set;

import com.supply.base.service.BaseService;
import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderDetailPo;
import com.supply.entity.po.OrderPo;

public interface OrderService extends BaseService
{
	PageInfo<OrderPo> findOrders(PageInfo page, OrderStatus status);

	void updateOrderStatus(OrderStatus status, long id);
	
	void createOrder(OrderPo order, List<OrderDetailPo> detailParams);
	
	void createOrder(Set<Long> storeIds, List<OrderDetailPo> detailParams);
}
