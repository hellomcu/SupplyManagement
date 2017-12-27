package com.supply.management.module.product.controller;

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
import com.supply.entity.PageInfo;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.ProductPo;
import com.supply.entity.po.UserPo;
import com.supply.exception.SupplyException;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.entity.dto.AddProductDto;
import com.supply.management.entity.dto.ProductDto;
import com.supply.management.entity.dto.UpdateProductDto;
import com.supply.management.module.product.service.ProductService;
import com.supply.management.util.WrappedBeanCopier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "产品相关")
@RestController
@RequestMapping("/admin/product")
public class ProductController extends BaseController
{

	private ProductService mProductService;

	@Autowired
	public ProductController(ProductService productService)
	{
		this.mProductService = productService;
	}

	
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "PUT", value = "添加产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> addProduct(@RequestBody AddProductDto addProductDto, HttpServletRequest request)
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
		ProductPo product = WrappedBeanCopier.copyProperties(addProductDto, ProductPo.class);
		product.setUserId(loginUser.getId());
		mProductService.addProduct(product);
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取所有产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<PageInfo<ProductDto>> findAllProducts( @RequestParam("page") long page, @RequestParam("num") int num, HttpServletRequest request)
	{
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<PageInfo<ProductDto>> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<PageInfo<ProductDto>> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		PageInfo<Void> pageInfo = new PageInfo<Void>();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);
		PageInfo<ProductPo> orderPos = mProductService.findProducts(pageInfo);
		PageInfo<ProductDto> result = new PageInfo<ProductDto>();
		result.setCurrentPage(orderPos.getCurrentPage());
		result.setTotalNum(orderPos.getTotalNum());
		result.setTotalPage(orderPos.getTotalPage());
		result.setItemNum(orderPos.getItemNum());
		result.setList(WrappedBeanCopier.copyPropertiesOfList(orderPos.getList(), ProductDto.class));
		return getResponse(result);
	}
	
	
	@ApiOperation(httpMethod = "DELETE", value = "根据id删除产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.DELETE,  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> deleteStore(@RequestParam("id") long id, HttpServletRequest request)
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
		mProductService.deleteProduct(id);
		return getResponse();
	}
	
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "更新产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> updateStore(@RequestBody UpdateProductDto updateProductDto, HttpServletRequest request)
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
		
		if (updateProductDto.getProductNum() > updateProductDto.getTotalNum())
		{
			throw new SupplyException("产品当前数量不能大于总数量");
		}
	
		ProductPo product = WrappedBeanCopier.copyProperties(updateProductDto, ProductPo.class);
		mProductService.updateProduct(product);
		return getResponse();
	}
}
