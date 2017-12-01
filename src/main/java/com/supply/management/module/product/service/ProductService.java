package com.supply.management.module.product.service;

import java.util.List;

import com.supply.base.service.BaseService;
import com.supply.entity.PageInfo;
import com.supply.entity.po.ProductPo;

public interface ProductService extends BaseService
{
	void addProduct(ProductPo product);
	
	List<ProductPo> findProducts(PageInfo page);
	
	void deleteProduct(long id);
	
	void updateProduct(ProductPo product);
}
