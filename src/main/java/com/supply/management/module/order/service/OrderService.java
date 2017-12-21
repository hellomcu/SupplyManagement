package com.supply.management.module.order.service;

import com.supply.base.service.BaseService;
import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderPo;

public interface OrderService extends BaseService
{
	PageInfo<OrderPo> findOrders(PageInfo page);

	void updateOrderStatus(OrderStatus status, long id);
}
