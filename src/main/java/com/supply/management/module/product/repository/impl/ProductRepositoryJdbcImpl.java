package com.supply.management.module.product.repository.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.ProductPo;
import com.supply.management.module.product.repository.ProductRepository;
import com.supply.management.util.TimeUtil;

@Repository(value="productRepository")
public class ProductRepositoryJdbcImpl implements ProductRepository
{

	private static final String SQL_SAVE = "INSERT INTO t_product (category_id, user_id, product_name, total_num, product_num, product_price, product_unit, product_place, product_date, quality_guarantee_period, description, create_time) values(:category_id, :user_id, :product_name, :total_num, :product_num, :product_price, :product_unit, :product_place, :product_date, :quality_guarantee_period, :description, :create_time)";
	
	private static final String SQL_QUERY = "SELECT p.id product_id, p.product_name product_name, p.total_num total_num," + 
			"p.product_num product_num, p.product_price product_price, p.product_unit, p.product_place product_place, p.description description, p.create_time create_time," + 
			"c.id category_id,c.category_name category_name," + 
			"c2.id parent_id, c2.category_name parent_name" + 
			" FROM t_product p LEFT JOIN t_category c ON p.category_id = c.id" + 
			" LEFT JOIN t_category c2 ON c2.id = c.parent_id" + 
			" WHERE p.status = 0 AND c.status = 0  AND c2.status = 0" + 
			" ORDER BY p.create_time DESC LIMIT :start, :num";
	
	private static final String SQL_DELETE = "UPDATE t_product SET status = 1 WHERE id=:id";
	
	//private static final String SQL_UPDATE = "UPDATE t_store SET store_name=:store_name, store_place=:store_place, contacts=:contacts, description=:description WHERE status=0 AND id=:id";
	
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
	public List<ProductPo> findAll(PageInfo pageInfo)
	{
		List<ProductPo> products = new ArrayList<ProductPo>();
		MapSqlParameterSource paramSource = new MapSqlParameterSource();
		paramSource.addValue("start", pageInfo.getStartItemNum());
		paramSource.addValue("num", pageInfo.getItemNum());
		SqlRowSet rowSet = this.mNamedParameterJdbcTemplate.queryForRowSet(SQL_QUERY, paramSource);
		while (rowSet.next())
		{
			ProductPo product = new ProductPo();
			product.setId(rowSet.getLong("product_id"));
			product.setProductName(rowSet.getString("product_name"));
			product.setTotalNum(rowSet.getInt("total_num"));
			product.setProductNum(rowSet.getInt("product_num"));
			product.setProductPrice(rowSet.getBigDecimal("product_price"));
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
		sb = new StringBuffer(sb.substring(0, sb.lastIndexOf(",")));
		sb.append(" WHERE id=:id");
		int effectedRows = this.mNamedParameterJdbcTemplate.update(sb.toString(), paramSource);

		return effectedRows;
	}
}
