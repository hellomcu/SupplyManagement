package com.supply.management.module.order.service;

import java.util.List;

import com.supply.base.service.BaseService;
import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderPo;

public interface OrderService extends BaseService
{
	List<OrderPo> findOrders(PageInfo page);

	void updateOrderStatus(OrderStatus status, long id);
}
