package com.supply.management.entity.dto;

import com.supply.entity.base.BaseDto;

public class AddCartDetailDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 8328159565435399163L;

	private long productId;
	private long productNum;
	
	public long getProductId()
	{
		return productId;
	}
	public void setProductId(long productId)
	{
		this.productId = productId;
	}
	
	public long getProductNum()
	{
		return productNum;
	}
	public void setProductNum(long productNum)
	{
		this.productNum = productNum;
	}
	
}
