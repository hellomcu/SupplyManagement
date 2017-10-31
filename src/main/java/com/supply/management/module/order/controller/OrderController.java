package com.supply.management.module.order.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.management.base.controller.BaseController;
import com.supply.management.beanutil.WrappedBeanCopier;
import com.supply.management.config.contants.OrderStatus;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.base.BaseResponse;
import com.supply.management.entity.dto.OrderDto;
import com.supply.management.entity.po.OrderPo;
import com.supply.management.module.order.service.OrderService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "订单相关")
@RestController
@RequestMapping("/admin/order")
public class OrderController extends BaseController
{

	private OrderService mOrderService;

	@Autowired
	public OrderController(OrderService orderService)
	{
		this.mOrderService = orderService;
	}

	
	@ApiOperation(httpMethod = "GET", value = "获取所有订单", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<List<OrderDto>> findAllStores( @RequestParam("page") long page, @RequestParam("num") int num)
	{
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		List<OrderPo> products = mOrderService.findOrders(pageInfo);
		return getResponse(WrappedBeanCopier.copyPropertiesOfList(products, OrderDto.class));
	}
	
	@RequestMapping(method = RequestMethod.POST, value="/status", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "更新订单状态", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> updateStore(@RequestParam("status") int status, @RequestParam("long") int id)
	{
	
		mOrderService.updateOrderStatus(OrderStatus.values()[status], id);;
		return getResponse();
	}
}
