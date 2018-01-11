package com.supply.management.entity.dto;

import java.util.List;

import com.supply.entity.base.BaseDto;

public class UpdateCartDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3462591519498779795L;

	private long userId;
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
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	
}
