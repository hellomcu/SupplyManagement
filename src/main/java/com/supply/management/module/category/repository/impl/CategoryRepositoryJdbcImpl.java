package com.supply.management.module.category.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.supply.management.entity.po.CategoryPo;
import com.supply.management.module.category.repository.CategoryRepository;
import com.supply.management.util.TimeUtil;

@Repository(value = "categoryRepository")
public class CategoryRepositoryJdbcImpl implements CategoryRepository
{
	private static final String SQL_SAVE = "INSERT INTO t_category (parent_id, category_name, create_time) VALUES (:parent_id, :category_name, :create_time)";

	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	@Autowired
	public CategoryRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int save(CategoryPo category)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		long parentId = category.getParentId();
		if (parentId == 0)
		{
			paramSource.addValue("parent_id", null);
		}
		else
		{
			paramSource.addValue("parent_id", parentId);
		}
	
		paramSource.addValue("category_name", category.getCategoryName());
		paramSource.addValue("create_time", TimeUtil.getCurrentTimestamp());
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_SAVE, paramSource, keyHolder,
				new String[] { "id" });
		category.setId(keyHolder.getKey().longValue());
		return effectedRows;
	}

}
