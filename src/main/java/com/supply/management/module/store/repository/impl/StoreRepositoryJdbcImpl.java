package com.supply.management.module.store.repository.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.StorePo;
import com.supply.management.module.store.repository.StoreRepository;
import com.supply.management.util.TimeUtil;

@Repository(value="storeRepository")
public class StoreRepositoryJdbcImpl implements StoreRepository
{

	private static final String SQL_SAVE = "INSERT INTO t_store (store_name, store_place, contacts, description, create_time) values(:store_name, :store_place, :contacts, :description, :create_time)";
	
	private static final String SQL_QUERY = "SELECT id, store_name, store_place, contacts, description, create_time FROM t_store WHERE status=0 ORDER BY create_time DESC LIMIT :start, :num";
	
	private static final String SQL_DELETE = "UPDATE t_store SET status = 1 WHERE id=:id";
	
	private static final String SQL_UPDATE = "UPDATE t_store SET store_name=:store_name, store_place=:store_place, contacts=:contacts, description=:description WHERE status=0 AND id=:id";
	
	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	
	@Autowired
	public StoreRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int save(StorePo store)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("store_name", store.getStoreName());
		paramSource.addValue("store_place", store.getStorePlace());
		paramSource.addValue("contacts", store.getContacts());
		paramSource.addValue("description", store.getDescription());
		paramSource.addValue("create_time", TimeUtil.getCurrentTimestamp());
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_SAVE, paramSource, keyHolder,
				new String[] { "id" });
		store.setId(keyHolder.getKey().longValue());

		return effectedRows;
	}

	@Override
	public List<StorePo> findAll(PageInfo pageInfo)
	{
		List<StorePo> stores = new ArrayList<StorePo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY, paramSource);
		while (rowSet.next())
		{
			StorePo store = new StorePo();
			store.setId(rowSet.getLong("id"));
			store.setStoreName(rowSet.getString("store_name"));
			store.setStorePlace(rowSet.getString("store_place"));
			store.setContacts(rowSet.getString("contacts"));
			store.setDescription(rowSet.getString("description"));
			store.setCreateTime(rowSet.getTimestamp("create_time"));
			stores.add(store);
		}
		return stores;
	}

	@Override
	public int delete(long id)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", id);
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_DELETE, paramSource);
		return effectedRows;
	}

	@Override
	public int update(StorePo store)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("store_name", store.getStoreName());
		paramSource.addValue("store_place", store.getStorePlace());
		paramSource.addValue("contacts", store.getContacts());
		paramSource.addValue("description", store.getDescription());
		paramSource.addValue("id", store.getId());
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_UPDATE, paramSource);
		return effectedRows;
	}
}
