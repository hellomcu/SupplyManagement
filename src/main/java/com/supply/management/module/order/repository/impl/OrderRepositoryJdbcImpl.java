package com.supply.management.module.order.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.management.config.contants.OrderStatus;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.OrderPo;
import com.supply.management.module.order.repository.OrderRepository;

@Repository(value="orderRepository")
public class OrderRepositoryJdbcImpl implements OrderRepository
{


	
	private static final String SQL_QUERY = "SELECT o.id order_id, o.store_id store_id, o.total_price total_price, o.product_num product_num, o.contacts contacts, o.order_status order_status, o.order_remark order_remark, o.create_time create_time," + 
			"s.store_name store_name" + 
			" FROM t_order o LEFT JOIN t_store s ON o.store_id = s.id AND s.status = 0 WHERE o.status = 0 ORDER BY o.create_time DESC LIMIT :start, :num";
	

	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	
	@Autowired
	public OrderRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}



	@Override
	public List<OrderPo> findAll(PageInfo pageInfo)
	{
		List<OrderPo> orders = new ArrayList<OrderPo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY, paramSource);
		while (rowSet.next())
		{
			OrderPo order = new OrderPo();
			order.setId(rowSet.getLong("order_id"));
			order.setStoreId(rowSet.getLong("store_id"));
			order.setTotalPrice(rowSet.getBigDecimal("total_price"));
			order.setProductNum(rowSet.getInt("product_num"));
			order.setContacts(rowSet.getString("contacts"));
			order.setOrderStatus(OrderStatus.values()[rowSet.getInt("order_status")]);
			order.setOrderRemark(rowSet.getString("order_remark"));
			order.setCreateTime(rowSet.getTimestamp("create_time"));
			order.setStoreName(rowSet.getString("store_name"));
			
			orders.add(order);
		}
		return orders;
	}

	@Override
	public int update(Map<String, Object> fields, long id)
	{
		StringBuffer sb = new StringBuffer("UPDATE t_order SET ");
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
