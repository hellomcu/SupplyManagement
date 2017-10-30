package com.supply.management.module.product.repository;

import java.util.List;
import java.util.Map;

import com.supply.management.base.repository.Repository;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.ProductPo;

public interface ProductRepository extends Repository
{
	int save(ProductPo product);
	
	List<ProductPo> findAll(PageInfo pageInfo);
	
	int delete(long id);
	
	int update(Map<String, Object> fields, long id);
}
