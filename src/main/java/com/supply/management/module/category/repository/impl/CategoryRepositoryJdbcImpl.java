package com.supply.management.module.category.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.CategoryPo;
import com.supply.management.module.category.repository.CategoryRepository;
import com.supply.management.util.TimeUtil;

@Repository(value = "categoryRepository")
public class CategoryRepositoryJdbcImpl implements CategoryRepository
{
	private static final String SQL_SAVE = "INSERT INTO t_category (parent_id, category_name, create_time) VALUES (:parent_id, :category_name, :create_time)";

	// private static final String SQL_QUERY = "SELECT p.id parent_id,
	// p.category_name parent_name, c.id child_id, c.category_name child_name" +
	// " FROM t_category p LEFT JOIN t_category c ON p.id = c.parent_id AND
	// c.status = 0 WHERE p.parent_id IS NULL AND p.status = 0 LIMIT :start,
	// :num";

	private static final String SQL_QUERY = "SELECT p.id parent_id, p.category_name parent_name, c.id child_id, c.category_name child_name"
			+ " FROM t_category p" + " LEFT JOIN t_category c" + " ON p.id = c.parent_id AND c.status = 0"
			+ " WHERE p.parent_id IS NULL AND p.status = 0"
			+ " AND p.id IN (SELECT t.id FROM (SELECT id FROM t_category WHERE t_category.parent_id IS NULL AND t_category.status = 0 LIMIT :start, :num)AS t)";

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

	@Override
	public Map<CategoryPo, List<CategoryPo>> findAll(PageInfo page)
	{
		Map<CategoryPo, List<CategoryPo>> map = new HashMap<CategoryPo, List<CategoryPo>>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", page.getStartItemNum());
		paramSource.addValue("num", page.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY, paramSource);
		while (rowSet.next())
		{
			CategoryPo parent = new CategoryPo();
			long parentId = rowSet.getLong("parent_id");
			parent.setId(parentId);
			parent.setCategoryName(rowSet.getString("parent_name"));
			if (!map.containsKey(parent))
			{
				map.put(parent, null);
			}
			long childId = rowSet.getLong("child_id");
			if (childId != 0)
			{
				CategoryPo child = new CategoryPo();
				child.setId(childId);
				child.setParentId(parentId);
				child.setCategoryName(rowSet.getString("child_name"));
				List<CategoryPo> list = map.get(parent);
				if (list == null)
				{
					list = new ArrayList<CategoryPo>();
					map.put(parent, list);
				}
				list.add(child);
			}

		}
		return map;
	}

}
