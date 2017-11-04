package com.supply.management.entity.dto;

import java.math.BigDecimal;
import java.sql.Date;

import com.supply.management.entity.base.BaseDto;

public class ProductDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1019785473939640160L;
	private long id;
	private long categoryId;
	private long userId;
	private String productName;
	private int totalNum;
	private int productNum;
	private BigDecimal productPrice;
	private String productUnit;
	private String productPlace;
	private Date productDate;
	private String qualityGuaranteePeriod;
	private String description;
	private String categoryName;
	private long parentId;
	private String parentName;
	
	
	public long getCategoryId()
	{
		return categoryId;
	}
	public void setCategoryId(long categoryId)
	{
		this.categoryId = categoryId;
	}
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	public String getProductName()
	{
		return productName;
	}
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	public BigDecimal getProductPrice()
	{
		return productPrice;
	}
	public void setProductPrice(BigDecimal productPrice)
	{
		this.productPrice = productPrice;
	}
	public String getProductPlace()
	{
		return productPlace;
	}
	public void setProductPlace(String productPlace)
	{
		this.productPlace = productPlace;
	}
	public Date getProductDate()
	{
		return productDate;
	}
	public void setProductDate(Date productDate)
	{
		this.productDate = productDate;
	}
	public String getQualityGuaranteePeriod()
	{
		return qualityGuaranteePeriod;
	}
	public void setQualityGuaranteePeriod(String qualityGuaranteePeriod)
	{
		this.qualityGuaranteePeriod = qualityGuaranteePeriod;
	}
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getTotalNum()
	{
		return totalNum;
	}
	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
	}
	public int getProductNum()
	{
		return productNum;
	}
	public void setProductNum(int productNum)
	{
		this.productNum = productNum;
	}
	public String getCategoryName()
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
	public long getParentId()
	{
		return parentId;
	}
	public void setParentId(long parentId)
	{
		this.parentId = parentId;
	}
	public String getParentName()
	{
		return parentName;
	}
	public void setParentName(String parentName)
	{
		this.parentName = parentName;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getProductUnit()
	{
		return productUnit;
	}
	public void setProductUnit(String productUnit)
	{
		this.productUnit = productUnit;
	}
	
	
}
