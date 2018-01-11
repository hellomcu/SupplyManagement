package com.supply.management.module.cart.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.contant.UserType;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.CartDetailPo;
import com.supply.entity.po.CartPo;
import com.supply.entity.po.UserPo;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.entity.dto.AddCartDto;
import com.supply.management.entity.dto.CartDetailDto;
import com.supply.management.entity.dto.CartDto;
import com.supply.management.entity.dto.UpdateCartDto;
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
		List<CartDetailPo> details = WrappedBeanCopier.copyPropertiesOfList(addCartDto.getDetails(), CartDetailPo.class);
		CartPo cartPo = WrappedBeanCopier.copyProperties(addCartDto, CartPo.class);
		cartPo.setDetails(details);
		mCartService.addCart(cartPo);
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取我的购物车", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/my_cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<CartDto> getMyCart(HttpServletRequest request)
	{
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<CartDto> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<CartDto> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		
		return getResponse(WrappedBeanCopier.copyProperties(mCartService.findMyCart(loginUser.getId()), CartDto.class));
	}
	
	@ApiOperation(httpMethod = "DELETE", value = "删除购物车中商品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.DELETE, value="/cart_product", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> removeProductFromCart(@RequestParam("productId") Long productId, HttpServletRequest request)
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
		mCartService.deleteCart(loginUser.getId(), productId);
		return getResponse();
	}
	
	
	@ApiOperation(httpMethod = "DELETE", value = "清空购物车", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.DELETE, value="/cart", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> clearCart(HttpServletRequest request)
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
		mCartService.clearCart(loginUser.getId());
		return getResponse();
	}
	
	@RequestMapping(method = RequestMethod.POST, value = "/update_cart", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "更新购物车", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<CartDetailDto> updateCart(@RequestBody UpdateCartDto updateCartDto, HttpServletRequest request)
	{
		
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<CartDetailDto> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<CartDetailDto> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		
		return getResponse(WrappedBeanCopier.copyProperties(mCartService.updateCart(loginUser.getId(), updateCartDto.getProductId(), updateCartDto.getProductNum()), CartDetailDto.class));
	}
}
