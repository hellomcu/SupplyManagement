package com.supply.management.module.product.repository;

import java.util.List;
import java.util.Map;
import java.util.Set;

import com.supply.base.repository.Repository;
import com.supply.entity.PageInfo;
import com.supply.entity.po.ProductPo;

public interface ProductRepository extends Repository
{
	int save(ProductPo product);
	
	List<ProductPo> findAll(PageInfo pageInfo, String productName);
	
	long count(String productName);
	
	int delete(long id);
	
	int update(Map<String, Object> fields, long id);
	
	List<ProductPo> findByIds(Set<Long> ids);
	
	int[] updateNum(List<ProductPo> products);
	
	int update(ProductPo product);
}
