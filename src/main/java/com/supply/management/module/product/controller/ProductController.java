package com.supply.management.module.product.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.management.base.controller.BaseController;
import com.supply.management.beanutil.WrappedBeanCopier;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.base.BaseResponse;
import com.supply.management.entity.dto.AddProductDto;
import com.supply.management.entity.dto.ProductDto;
import com.supply.management.entity.dto.UpdateProductDto;
import com.supply.management.entity.po.ProductPo;
import com.supply.management.exception.SupplyException;
import com.supply.management.module.product.service.ProductService;

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
	public BaseResponse<Void> addProduct(@RequestBody AddProductDto addProductDto)
	{
		mProductService.addProduct(WrappedBeanCopier.copyProperties(addProductDto, ProductPo.class));
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取所有产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/products", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<List<ProductDto>> findAllStores( @RequestParam("page") long page, @RequestParam("num") int num)
	{
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		List<ProductPo> products = mProductService.findProducts(pageInfo);
		return getResponse(WrappedBeanCopier.copyPropertiesOfList(products, ProductDto.class));
	}
	
	
	@ApiOperation(httpMethod = "DELETE", value = "根据id删除产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.DELETE,  produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> deleteStore(@RequestParam("id") long id)
	{
		mProductService.deleteProduct(id);
		return getResponse();
	}
	
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "更新产品", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> updateStore(@RequestBody UpdateProductDto updateProductDto)
	{
		if (updateProductDto.getProductNum() > updateProductDto.getTotalNum())
		{
			throw new SupplyException("产品当前数量不能大于总数量");
		}
	
		ProductPo product = WrappedBeanCopier.copyProperties(updateProductDto, ProductPo.class);
		mProductService.updateProduct(product);
		return getResponse();
	}
}
