package com.supply.management.module.order.service;

import java.util.List;

import com.supply.management.base.service.BaseService;
import com.supply.management.config.contants.OrderStatus;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.OrderPo;

public interface OrderService extends BaseService
{
	List<OrderPo> findOrders(PageInfo page);

	void updateOrderStatus(OrderStatus status, long id);
}
