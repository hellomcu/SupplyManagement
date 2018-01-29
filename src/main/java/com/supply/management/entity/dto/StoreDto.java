package com.supply.management.entity.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.supply.entity.base.BaseDto;

public class StoreDto extends BaseDto
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -6736714595992932663L;
	private long id;
	private String storeName;
	private String storePlace;
	private String contacts;
	private String contactWay;
	private BigDecimal balance;
	private String description;
	private Timestamp createTime;
	private BigDecimal orderTotalPrice;
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getStoreName()
	{
		return storeName;
	}
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	public String getStorePlace()
	{
		return storePlace;
	}
	public void setStorePlace(String storePlace)
	{
		this.storePlace = storePlace;
	}
	public String getContacts()
	{
		return contacts;
	}
	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}

	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}

	public Timestamp getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}
	public BigDecimal getBalance()
	{
		return balance;
	}
	public void setBalance(BigDecimal balance)
	{
		this.balance = balance;
	}
	public String getContactWay()
	{
		return contactWay;
	}
	public void setContactWay(String contactWay)
	{
		this.contactWay = contactWay;
	}
	public BigDecimal getOrderTotalPrice()
	{
		return orderTotalPrice;
	}
	public void setOrderTotalPrice(BigDecimal orderTotalPrice)
	{
		this.orderTotalPrice = orderTotalPrice;
	}
	
	
	
}
