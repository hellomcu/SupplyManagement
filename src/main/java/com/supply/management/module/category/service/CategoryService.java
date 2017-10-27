package com.supply.management.module.category.service;

import com.supply.management.base.service.BaseService;
import com.supply.management.entity.po.CategoryPo;

public interface CategoryService extends BaseService
{
	void addCategory(CategoryPo category);
	
	void addCategory(CategoryPo category, CategoryPo childCategory);
}
