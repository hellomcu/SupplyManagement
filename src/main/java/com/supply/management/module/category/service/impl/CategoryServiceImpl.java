package com.supply.management.module.category.service.impl;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.CategoryPo;
import com.supply.management.exception.SupplyException;
import com.supply.management.module.category.repository.CategoryRepository;
import com.supply.management.module.category.service.CategoryService;

@Service
public class CategoryServiceImpl implements CategoryService
{

	private CategoryRepository mCategoryRepository;

	@Override
	public void addCategory(CategoryPo category)
	{
		mCategoryRepository.save(category);
	}

	@Transactional
	@Override
	public void addCategory(CategoryPo category, CategoryPo childCategory)
	{
		//先保存父分类
		int rows = mCategoryRepository.save(category);
		if (rows != 1)
		{
			throw new SupplyException("添加类别失败,请稍后重试");
		}

		childCategory.setParentId(category.getId());
		rows = mCategoryRepository.save(childCategory);
		if (rows != 1)
		{
			throw new SupplyException("添加类别失败,请稍后重试");
		}

	}

	@Override
	public Map<CategoryPo, List<CategoryPo>> findAll(PageInfo page)
	{
		return mCategoryRepository.findAll(page);
	}
	
	@Resource(name = "categoryRepository")
	public void setCategoryRepository(CategoryRepository categoryRepository)
	{
		this.mCategoryRepository = categoryRepository;
	}

	

}
