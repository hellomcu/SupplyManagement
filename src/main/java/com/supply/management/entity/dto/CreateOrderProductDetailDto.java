package com.supply.management.entity.dto;

import javax.validation.constraints.Min;

import com.supply.entity.base.BaseDto;

public class CreateOrderProductDetailDto extends BaseDto
{



	
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4158851269790614087L;

	/**
	 * 
	 */

	private long productId;
	
	@Min(value = 1, message = "请至少购买一件商品")
	private int productNum;

	public long getProductId()
	{
		return productId;
	}

	public void setProductId(long productId)
	{
		this.productId = productId;
	}

	public int getProductNum()
	{
		return productNum;
	}

	public void setProductNum(int productNum)
	{
		this.productNum = productNum;
	}
	
	
	
}
