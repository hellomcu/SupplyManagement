package com.supply.management.module.category.service;

import java.util.List;
import java.util.Map;

import com.supply.management.base.service.BaseService;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.CategoryPo;

public interface CategoryService extends BaseService
{
	void addCategory(CategoryPo category);
	
	void addCategory(CategoryPo category, CategoryPo childCategory);
	
	Map<CategoryPo, List<CategoryPo>> findAll(PageInfo page);
}
