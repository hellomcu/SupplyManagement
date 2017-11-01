package com.supply.management.entity.dto;

import java.math.BigDecimal;
import java.sql.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.supply.management.entity.base.BaseDto;

public class UpdateProductDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -291408185877689715L;
	private long id;
	private String productName;
	private int totalNum;
	private int productNum;
	private BigDecimal productPrice;
	private String productUnit;
	private String productPlace;

	@JsonFormat(pattern = "yyyy-MM-dd")
	private Date productDate;
	private String qualityGuaranteePeriod;
	private String description;

	public long getId()
	{
		return id;
	}

	public void setId(long id)
	{
		this.id = id;
	}

	public int getTotalNum()
	{
		return totalNum;
	}

	public void setTotalNum(int totalNum)
	{
		this.totalNum = totalNum;
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

	public int getProductNum()
	{
		return productNum;
	}

	public void setProductNum(int productNum)
	{
		this.productNum = productNum;
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
