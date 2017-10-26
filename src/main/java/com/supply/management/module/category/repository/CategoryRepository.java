package com.supply.management.module.category.repository;

import com.supply.management.base.repository.Repository;
import com.supply.management.entity.po.CategoryPo;

public interface CategoryRepository extends Repository
{
	int save(CategoryPo category);
}
