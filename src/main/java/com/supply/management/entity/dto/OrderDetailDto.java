package com.supply.management.entity.dto;

import java.math.BigDecimal;

import com.supply.entity.base.BaseDto;

public class OrderDetailDto extends BaseDto
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = 5819541219371788419L;
	/**
	 * 
	 */
	
	private long orderId;
	private long productId;
	private String productName;
	private int productNum;
	private BigDecimal unitPrice;
	private String productUnit;
	public long getOrderId()
	{
		return orderId;
	}
	public void setOrderId(long orderId)
	{
		this.orderId = orderId;
	}
	public long getProductId()
	{
		return productId;
	}
	public void setProductId(long productId)
	{
		this.productId = productId;
	}
	public String getProductName()
	{
		return productName;
	}
	public void setProductName(String productName)
	{
		this.productName = productName;
	}
	public int getProductNum()
	{
		return productNum;
	}
	public void setProductNum(int productNum)
	{
		this.productNum = productNum;
	}
	public BigDecimal getUnitPrice()
	{
		return unitPrice;
	}
	public void setUnitPrice(BigDecimal unitPrice)
	{
		this.unitPrice = unitPrice;
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