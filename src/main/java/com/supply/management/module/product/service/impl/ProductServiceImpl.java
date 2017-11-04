package com.supply.management.module.product.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supply.management.entity.PageInfo;
import com.supply.management.entity.po.ProductPo;

import com.supply.management.exception.SupplyException;
import com.supply.management.module.product.repository.ProductRepository;
import com.supply.management.module.product.service.ProductService;

@Service
public class ProductServiceImpl implements ProductService
{
	private ProductRepository mProductRepository;
	
	@Override
	public void addProduct(ProductPo product)
	{
		product.setTotalNum(product.getProductNum());
		int rows = mProductRepository.save(product);
		if (rows != 1)
		{
			throw new SupplyException("添加产品失败,请稍后重试");
		}
	
	}
	
	@Override
	public List<ProductPo> findProducts(PageInfo page)
	{
		return mProductRepository.findAll(page);
	}
	
	@Override
	public void deleteProduct(long id)
	{
		int rows = mProductRepository.delete(id);
		if (rows != 1)
		{
			throw new SupplyException("删除产品失败,请稍后重试");
		}
	}
	
	@Override
	public void updateProduct(ProductPo product)
	{
		Map<String, Object> fields = new HashMap<String, Object>();
		fields.put("product_name", product.getProductName());
		fields.put("total_num", product.getTotalNum());
		fields.put("product_num", product.getProductNum());
		fields.put("product_price", product.getProductPrice());
		fields.put("product_unit", product.getProductUnit());
		fields.put("product_place", product.getProductPlace());
		fields.put("product_date", product.getProductDate());
		fields.put("quality_guarantee_period", product.getQualityGuaranteePeriod());
		fields.put("description", product.getDescription());
		int effectedRows = mProductRepository.update(fields, product.getId());
		if (effectedRows != 1)
		{
			throw new SupplyException("更新产品失败,请稍后重试");
		}
	}
	
	
	@Resource(name="productRepository")
	public void setProductRepository(ProductRepository productRepository)
	{
		this.mProductRepository = productRepository;
	}

	


	
	
}
