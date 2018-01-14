package com.supply.management.module.order.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.support.StaticApplicationContext;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.contant.OrderStatus;
import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderDetailPo;
import com.supply.entity.po.OrderPo;
import com.supply.management.module.order.repository.OrderRepository;
import com.supply.management.util.TimeUtil;

@Repository(value = "orderRepository")
public class OrderRepositoryJdbcImpl implements OrderRepository
{

	private static final String SQL_QUERY = "SELECT o.id order_id, o.store_id store_id, o.total_price total_price, o.product_num product_num, o.contacts contacts, o.order_status order_status, o.order_remark order_remark, o.create_time create_time,"
			+ "s.store_name store_name"
			+ " FROM t_order o LEFT JOIN t_store s ON o.store_id = s.id AND s.status = 0 WHERE o.status = 0 ORDER BY o.create_time DESC LIMIT :start, :num";

	private static final String SQL_COUNT = "SELECT COUNT(o.id)" + " FROM t_order o WHERE o.status = 0";

	private static final String SQL_INSERT = "INSERT INTO t_order(store_id, total_price, product_num, total_num, receiver, receiving_address, contacts, order_status, order_remark, create_time)"
			+ " VALUES(:store_id, :total_price, :product_num, :total_num, :receiver, :receiving_address, :contacts, :order_status, :order_remark, :create_time)";

	private static final String SQL_DETAIL_INSERT = "INSERT INTO t_order_detail (order_id, product_id, product_name, product_num, unit_price, product_unit, create_time)"
			+ " VALUES(:order_id, :product_id, :product_name, :product_num, :unit_price, :product_unit, :create_time)";

	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	@Autowired
	public OrderRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public List<OrderPo> findAll(PageInfo pageInfo, OrderStatus status)
	{
		String statusCondition = "";
		int statusVal = status.ordinal();
		if (statusVal != 0)
		{
			statusCondition = " AND o.order_status = " + statusVal;
		}

		String sql = "SELECT o.id order_id, o.store_id store_id, o.total_price total_price, o.product_num product_num, o.contacts contacts, o.order_status order_status, o.order_remark order_remark, o.create_time create_time,"
				+ "s.store_name store_name"
				+ " FROM t_order o LEFT JOIN t_store s ON o.store_id = s.id AND o.status = 0 WHERE s.status = 0"
				+ statusCondition + " ORDER BY o.create_time DESC LIMIT :start, :num";

		List<OrderPo> orders = new ArrayList<OrderPo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(sql, paramSource);
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
	public long count(OrderStatus status)
	{
		String statusCondition = "";
		int statusVal = status.ordinal();
		if (statusVal != 0)
		{
			statusCondition = " AND o.order_status = " + statusVal;
		}
		String sql = "SELECT COUNT(o.id) FROM t_order o WHERE o.status = 0" + statusCondition;
		return this.mNamedParameterJdbcTemplate.queryForObject(sql, new HashMap<>(), Long.class);
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

	@Override
	public int save(OrderPo order)
	{
		KeyHolder keyHolder = new GeneratedKeyHolder();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("store_id", order.getStoreId());
		paramSource.addValue("total_price", order.getTotalPrice());
		paramSource.addValue("product_num", order.getProductNum());
		paramSource.addValue("total_num", order.getTotalNum());
		paramSource.addValue("receiver", order.getReceiver());
		paramSource.addValue("receiving_address", order.getReceivingAddress());
		paramSource.addValue("contacts", order.getContacts());
		paramSource.addValue("order_status", order.getOrderStatus().ordinal());
		paramSource.addValue("order_remark", order.getOrderRemark());
		paramSource.addValue("create_time", TimeUtil.getCurrentTimestamp());

		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_INSERT, paramSource, keyHolder,
				new String[] { "id" });
	
		order.setId(keyHolder.getKey().longValue());
		return effectedRows;
	}

	@Override
	public int[] saveDetails(long orderId, List<OrderDetailPo> details)
	{
		int size = details.size();
		Map<String, Object>[] batchArgs = new Map[size];
		for (int i = 0; i < size; i++)
		{
			OrderDetailPo detailPo = details.get(i);
			Map<String, Object> map = new HashMap<>();
			map.put("order_id", orderId);
			map.put("product_id", detailPo.getProductId());
			map.put("product_name", detailPo.getProductName());
			map.put("product_num", detailPo.getProductNum());
			map.put("unit_price", detailPo.getUnitPrice());
			map.put("product_unit", detailPo.getProductUnit());
			map.put("create_time", TimeUtil.getCurrentTimestamp());
			batchArgs[i] = map;
		}
		return mNamedParameterJdbcTemplate.batchUpdate(SQL_DETAIL_INSERT, batchArgs);
	}
}
