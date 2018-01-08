package com.supply.management.entity.dto;

import java.math.BigDecimal;

import com.supply.entity.base.BasePo;

public class RechargeDto extends BasePo
{


	
	/**
	 * 
	 */
	private static final long serialVersionUID = 8464028874170597669L;
	//private long userId;
	private long storeId;
	private BigDecimal amount;
	private long fromId;
	
	public long getStoreId()
	{
		return storeId;
	}
	public void setStoreId(long storeId)
	{
		this.storeId = storeId;
	}
	public BigDecimal getAmount()
	{
		return amount;
	}
	public void setAmount(BigDecimal amount)
	{
		this.amount = amount;
	}
	public long getFromId()
	{
		return fromId;
	}
	public void setFromId(long fromId)
	{
		this.fromId = fromId;
	}
	
	
}
