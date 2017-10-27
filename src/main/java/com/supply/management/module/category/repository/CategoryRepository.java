package com.supply.management.module.category.repository;

import java.util.List;
import java.util.Map;

import com.supply.management.base.repository.Repository;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.CategoryPo;

public interface CategoryRepository extends Repository
{
	int save(CategoryPo category);
	
	Map<CategoryPo, List<CategoryPo>> findAll(PageInfo page);
}
