package com.supply.management.module.cart.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.contant.UserType;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.CartPo;
import com.supply.entity.po.UserPo;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.entity.dto.AddCartDto;
import com.supply.management.module.cart.service.CartService;
import com.supply.management.util.WrappedBeanCopier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "购物车相关")
@RestController
@RequestMapping("/admin/cart")
public class CartController extends BaseController
{

	private CartService mCartService;

	@Autowired
	public CartController(CartService cartService)
	{
		this.mCartService = cartService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/add_cart", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "向购物车添加商品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> addCart(@RequestBody AddCartDto addCartDto, HttpServletRequest request)
	{
		
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		addCartDto.setUserId(loginUser.getId());
		CartPo cartPo = WrappedBeanCopier.copyProperties(addCartDto, CartPo.class);
		mCartService.createCart(cartPo);
		return getResponse();
	}
	
	
}
