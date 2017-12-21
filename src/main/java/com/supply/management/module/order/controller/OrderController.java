package com.supply.management.module.order.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.contant.OrderStatus;
import com.supply.contant.UserType;
import com.supply.entity.PageInfo;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.OrderPo;
import com.supply.entity.po.UserPo;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.entity.dto.OrderDto;
import com.supply.management.module.order.service.OrderService;
import com.supply.management.util.WrappedBeanCopier;

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
	@RequestMapping(method = RequestMethod.GET, value = "/orders", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<PageInfo<OrderDto>> findAllOrders(@RequestParam("page") long page, @RequestParam("num") int num, HttpServletRequest request)
	{
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<PageInfo<OrderDto>> response = new BaseResponse<>();
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<PageInfo<OrderDto>> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		PageInfo<Void> pageInfo = new PageInfo<Void>();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		PageInfo<OrderPo> orderPos = mOrderService.findOrders(pageInfo);
		PageInfo<OrderDto> result = new PageInfo<OrderDto>();
		result.setCurrentPage(orderPos.getCurrentPage());
		result.setTotalNum(orderPos.getTotalNum());
		result.setTotalPage(orderPos.getTotalPage());
		result.setItemNum(orderPos.getItemNum());
		result.setList(WrappedBeanCopier.copyPropertiesOfList(orderPos.getList(), OrderDto.class));
		return getResponse(result);
	}

	@RequestMapping(method = RequestMethod.POST, value = "/status", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "更新订单状态", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> updateOrderStatus(@RequestParam("status") int status, @RequestParam("id") long id,
			HttpServletRequest request)
	{
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		if (status < 1 || status > 5)
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setMessage("订单状态不正确");
			return response;
		}
		mOrderService.updateOrderStatus(OrderStatus.values()[status], id);
		return getResponse();
	}
}
