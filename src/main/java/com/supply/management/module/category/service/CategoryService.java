package com.supply.management.module.category.service;

import java.util.List;
import java.util.Map;

import com.supply.base.service.BaseService;
import com.supply.entity.PageInfo;
import com.supply.entity.po.CategoryPo;

public interface CategoryService extends BaseService
{
	void addCategory(CategoryPo category);
	
	void addCategory(CategoryPo category, CategoryPo childCategory);
	
	Map<CategoryPo, List<CategoryPo>> findAll(PageInfo page);
}
