package com.supply.management.module.product.repository.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.entity.PageInfo;
import com.supply.entity.po.OrderDetailPo;
import com.supply.entity.po.ProductPo;
import com.supply.management.module.product.repository.ProductRepository;
import com.supply.management.util.TimeUtil;

@Repository(value = "productRepository")
public class ProductRepositoryJdbcImpl implements ProductRepository
{

	private static final String SQL_SAVE = "INSERT INTO t_product (category_id, user_id, product_name, total_num, product_num, product_price, sale_price, product_unit, product_place, product_date, quality_guarantee_period, description, create_time) values(:category_id, :user_id, :product_name, :total_num, :product_num, :product_price, :sale_price, :product_unit, :product_place, :product_date, :quality_guarantee_period, :description, :create_time)";

	private static final String SQL_QUERY = "SELECT p.id product_id, p.product_name product_name, p.total_num total_num,"
			+ "p.product_num product_num, p.product_price product_price, p.sale_price sale_price, p.product_unit, p.product_place product_place, p.description description, p.create_time create_time,"
			+ "c.id category_id,c.category_name category_name," + "c2.id parent_id, c2.category_name parent_name"
			+ " FROM t_product p LEFT JOIN t_category c ON p.category_id = c.id"
			+ " LEFT JOIN t_category c2 ON c2.id = c.parent_id"
			+ " WHERE p.status = 0 AND c.status = 0 AND c2.status = 0 AND p.product_name LIKE '%' :product_name '%'"
			+ " ORDER BY p.create_time DESC LIMIT :start, :num";

	private static final String SQL_COUNT = "SELECT COUNT(id) FROM t_product WHERE status = 0 AND product_name LIKE '%' :product_name '%'";

	private static final String SQL_DELETE = "UPDATE t_product SET status = 1 WHERE id=:id";

	// private static final String SQL_UPDATE = "UPDATE t_store SET
	// store_name=:store_name, store_place=:store_place, contacts=:contacts,
	// description=:description WHERE status=0 AND id=:id";

	private static final String SQL_UPDATE_NUM = "UPDATE t_product set product_num=:product_num WHERE id = :id AND status = 0";

	private static final String SQL_UPDATE = "UPDATE t_product set product_name=:product_name, total_num=total_num+:product_num-product_num, product_num=:product_num, product_price=:product_price, sale_price=:sale_price, product_unit=:product_unit,product_place=:product_place,description=:description WHERE id = :id AND status = 0";

	private NamedParameterJdbcTemplate mNamedParameterJdbcTemplate;

	@Autowired
	public ProductRepositoryJdbcImpl(
			@Qualifier(value = "namedParameterJdbcTemplate") NamedParameterJdbcTemplate namedParameterJdbcTemplate)
	{
		this.mNamedParameterJdbcTemplate = namedParameterJdbcTemplate;
	}

	@Override
	public int save(ProductPo product)
	{

		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("category_id", product.getCategoryId());
		paramSource.addValue("user_id", product.getUserId());
		paramSource.addValue("product_name", product.getProductName());
		paramSource.addValue("total_num", product.getTotalNum());
		paramSource.addValue("product_num", product.getProductNum());
		paramSource.addValue("product_price", product.getProductPrice());
		paramSource.addValue("sale_price", product.getSalePrice());
		paramSource.addValue("product_unit", product.getProductUnit());
		paramSource.addValue("product_place", product.getProductPlace());
		paramSource.addValue("product_date", product.getProductDate());
		paramSource.addValue("quality_guarantee_period", product.getQualityGuaranteePeriod());
		paramSource.addValue("description", product.getDescription());
		paramSource.addValue("create_time", TimeUtil.getCurrentTimestamp());

		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_SAVE, paramSource);

		return effectedRows;
	}

	@Override
	public List<ProductPo> findAll(PageInfo pageInfo, String productName)
	{
		List<ProductPo> products = new ArrayList<ProductPo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		paramSource.addValue("product_name", productName);
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY, paramSource);
		while (rowSet.next())
		{
			ProductPo product = new ProductPo();
			product.setId(rowSet.getLong("product_id"));
			product.setProductName(rowSet.getString("product_name"));
			product.setTotalNum(rowSet.getInt("total_num"));
			product.setProductNum(rowSet.getInt("product_num"));
			product.setProductPrice(rowSet.getBigDecimal("product_price"));
			product.setSalePrice(rowSet.getBigDecimal("sale_price"));
			product.setProductUnit(rowSet.getString("product_unit"));
			product.setProductPlace(rowSet.getString("product_place"));
			product.setDescription(rowSet.getString("description"));
			product.setCreateTime(rowSet.getTimestamp("create_time"));
			product.setCategoryId(rowSet.getLong("category_id"));
			product.setCategoryName(rowSet.getString("category_name"));
			product.setParentId(rowSet.getLong("parent_id"));
			product.setParentName(rowSet.getString("parent_name"));

			products.add(product);
		}
		return products;
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
	public int update(Map<String, Object> fields, long id)
	{
		StringBuffer sb = new StringBuffer("UPDATE t_product SET ");
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", id);
		for (String key : fields.keySet())
		{
			paramSource.addValue(key, fields.get(key));
			sb.append(key).append("=:").append(key).append(",");
		}

		sb.append("total_num=total_num+:product_num");
		// sb = new StringBuffer(sb.substring(0, sb.lastIndexOf(",")));
		sb.append(" WHERE id=:id");
		int effectedRows = this.mNamedParameterJdbcTemplate.update(sb.toString(), paramSource);

		return effectedRows;
	}

	@Override
	public long count(String productName)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("product_name", productName);
		return this.mNamedParameterJdbcTemplate.queryForObject(SQL_COUNT, paramSource, Long.class);
	}

	@Override
	public List<ProductPo> findByIds(Set<Long> ids)
	{
		String sql = "SELECT id, product_name, product_num, product_price, sale_price, product_unit FROM t_product WHERE status = 0";
		List<ProductPo> products = new ArrayList<ProductPo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();

		if (ids == null || ids.isEmpty())
		{
			return products;
		}
		StringBuffer sBuffer = new StringBuffer(" AND id IN (");
		for (Long id : ids)
		{
			sBuffer.append(id).append(",");
		}
		sBuffer = new StringBuffer(sBuffer.substring(0, sBuffer.length() - 1));
		sBuffer.append(");");
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(sql + sBuffer.toString(), paramSource);
		while (rowSet.next())
		{
			ProductPo product = new ProductPo();
			product.setId(rowSet.getLong("id"));
			product.setProductName(rowSet.getString("product_name"));
			product.setProductNum(rowSet.getInt("product_num"));
			product.setProductPrice(rowSet.getBigDecimal("product_price"));
			product.setSalePrice(rowSet.getBigDecimal("sale_price"));
			product.setProductUnit(rowSet.getString("product_unit"));

			products.add(product);
		}
		return products;

	}

	@Override
	public int[] updateNum(List<ProductPo> products)
	{
		int size = products.size();
		Map<String, Object>[] batchArgs = new Map[size];
		for (int i = 0; i < size; i++)
		{
			ProductPo productPo = products.get(i);
			Map<String, Object> map = new HashMap<>();
			map.put("id", productPo.getId());
			map.put("product_num", productPo.getProductNum());

			batchArgs[i] = map;
		}

		return mNamedParameterJdbcTemplate.batchUpdate(SQL_UPDATE_NUM, batchArgs);
	}

	@Override
	public int update(ProductPo product)
	{
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("id", product.getId());
		paramSource.addValue("product_name", product.getProductName());
		paramSource.addValue("product_num", product.getProductNum());
		paramSource.addValue("product_price", product.getProductPrice());
		paramSource.addValue("sale_price", product.getSalePrice());
		paramSource.addValue("product_unit", product.getProductUnit());
		paramSource.addValue("product_place", product.getProductPlace());
		paramSource.addValue("description", product.getDescription());

		int effectedRows = this.mNamedParameterJdbcTemplate.update(SQL_UPDATE, paramSource);

		return effectedRows;
	}
}
