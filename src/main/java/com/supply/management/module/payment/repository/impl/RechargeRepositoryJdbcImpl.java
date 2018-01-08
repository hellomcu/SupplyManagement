package com.supply.management.module.payment.repository.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;

import com.supply.entity.po.RechargePo;
import com.supply.management.module.payment.repository.RechargeRepository;
import com.supply.util.TimeUtil;


@Repository(value = "rechargeRepository")
public class RechargeRepositoryJdbcImpl implements RechargeRepository
{
	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	
	private static final String SQL_SAVE = "INSERT INTO t_recharge (user_id, store_id, amount, from_id, create_time) VALUES(:user_id, :store_id, :amount, :from_id, :create_time)";

	
	@Autowired
	public RechargeRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	


	@Override
	public int save(RechargePo rechargePo)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("user_id", rechargePo.getUserId());
		paramSource.addValue("store_id", rechargePo.getStoreId());
		paramSource.addValue("amount", rechargePo.getAmount());
		paramSource.addValue("from_id", rechargePo.getFromId());
		paramSource.addValue("create_time", TimeUtil.getCurrentTimestamp());
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_SAVE, paramSource);
		
		return effectedRows;
	}

	
}
