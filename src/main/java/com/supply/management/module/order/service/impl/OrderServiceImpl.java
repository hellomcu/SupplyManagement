package com.supply.management.module.order.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderPo;
import com.supply.exception.SupplyException;
import com.supply.management.module.order.repository.OrderRepository;
import com.supply.management.module.order.service.OrderService;

@Service
public class OrderServiceImpl implements OrderService
{
	private OrderRepository mOrderRepository;
	
	
	@Override
	public PageInfo<OrderPo> findOrders(PageInfo page, OrderStatus status)
	{
		PageInfo<OrderPo> result = new PageInfo<>();
		List<OrderPo> list = mOrderRepository.findAll(page, status);
		long count = mOrderRepository.count(status);
		result.setList(list);
		result.setTotalNum(count);
		result.setItemNum(page.getItemNum());
		result.setTotalPage(result.calcTotalPage());
		result.setCurrentPage(page.getCurrentPage());
		return result;
	}

	@Override
	public void updateOrderStatus(OrderStatus status, long id)
	{
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("order_status", status.ordinal());
		int effectedRows = mOrderRepository.update(fields, id);
		if (effectedRows != 1)
		{
			throw new SupplyException("更新订单状态失败,请稍后重试");
		}
		
	}
	
	
	@Resource(name="orderRepository")
	public void setProductRepository(OrderRepository orderRepository)
	{
		this.mOrderRepository = orderRepository;
	}


	
	
}
