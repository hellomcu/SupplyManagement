package com.supply.management.module.user.repository.impl;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.management.entity.po.UserPo;
import com.supply.management.module.user.repository.UserRepository;


@Repository(value = "userRepository")
public class UserRepositoryJdbcImpl implements UserRepository
{
	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	private static final String SQL_QUERY_BY_USERNAMAE_TYPE = "SELECT id, password, store_id, true_name, phone, email FROM t_user WHERE username=:username AND user_type = :user_type AND status=0";
	
	private static final String SQL_SAVE = "INSERT INTO t_user (store_id, username, password, user_type) VALUES(:store_id, :username, :password, :user_type)";

	
	@Autowired
	public UserRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}
	
	public UserPo findOneByUsernameAndType(String username, int type)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("username", username);
		paramSource.addValue("user_type", type);

		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY_BY_USERNAMAE_TYPE, paramSource);
		if (rowSet.next())
		{
			UserPo user = new UserPo();
			user.setId(rowSet.getLong("id"));
			user.setPassword(rowSet.getString("password"));
			user.setStoreId(rowSet.getLong("store_id"));
			user.setTrueName(rowSet.getString("true_name"));
			user.setPhone(rowSet.getString("phone"));
			user.setEmail(rowSet.getString("email"));
			return user;
		}
		return null;
	}

	@Override
	public int save(UserPo user)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("store_id", user.getStoreId());
		paramSource.addValue("username", user.getUsername());
		paramSource.addValue("password", user.getPassword());
		paramSource.addValue("user_type", user.getUserType().ordinal());
		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_SAVE, paramSource);
		
		return effectedRows;
	}

	@Override
	public int update(Map<String, Object> fields, long id)
	{
		StringBuffer sb = new StringBuffer("UPDATE t_user SET ");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", id);
		for (String key : fields.keySet())
		{
			paramSource.addValue(key, fields.get(key));
			sb.append(key).append("=:").append(key).append(",");
		}
		sb = new StringBuffer(sb.substring(0, sb.lastIndexOf(",")));
		sb.append(" WHERE id=:id");
		int effectedRows = this.mNamedParameterJdbcTemplate.update(sb.toString(), paramSource);

		return effectedRows;
	}
}
