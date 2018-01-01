package com.supply.management.module.product.service;

import com.supply.base.service.BaseService;
import com.supply.entity.PageInfo;
import com.supply.entity.po.ProductPo;

public interface ProductService extends BaseService
{
	void addProduct(ProductPo product);
	
	PageInfo<ProductPo> findProducts(PageInfo<Void> page, String productName);
	
	void deleteProduct(long id);
	
	void updateProduct(ProductPo product);
}
