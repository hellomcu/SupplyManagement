package com.supply.management.entity.dto;

import java.math.BigDecimal;
import java.sql.Timestamp;

import com.supply.management.config.contants.OrderStatus;
import com.supply.management.entity.base.BaseDto;

public class OrderDto extends BaseDto
{


	/**
	 * 
	 */
	private static final long serialVersionUID = 2564014545994044699L;
	private long id;
	private long storeId;
	private BigDecimal totalPrice;
	private int productNum;
	private String receivingAddress;
	private String contacts;
	private String contactWay;
	private OrderStatus orderStatus;
	private String orderRemark;
	private String storeName;
	private Timestamp createTime;
	
	public long getStoreId()
	{
		return storeId;
	}
	public void setStoreId(long storeId)
	{
		this.storeId = storeId;
	}
	public BigDecimal getTotalPrice()
	{
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice)
	{
		this.totalPrice = totalPrice;
	}
	public int getProductNum()
	{
		return productNum;
	}
	public void setProductNum(int productNum)
	{
		this.productNum = productNum;
	}
	public String getReceivingAddress()
	{
		return receivingAddress;
	}
	public void setReceivingAddress(String receivingAddress)
	{
		this.receivingAddress = receivingAddress;
	}
	public String getContacts()
	{
		return contacts;
	}
	public void setContacts(String contacts)
	{
		this.contacts = contacts;
	}
	public String getContactWay()
	{
		return contactWay;
	}
	public void setContactWay(String contactWay)
	{
		this.contactWay = contactWay;
	}
	public OrderStatus getOrderStatus()
	{
		return orderStatus;
	}
	public void setOrderStatus(OrderStatus orderStatus)
	{
		this.orderStatus = orderStatus;
	}
	public String getOrderRemark()
	{
		return orderRemark;
	}
	public void setOrderRemark(String orderRemark)
	{
		this.orderRemark = orderRemark;
	}
	public String getStoreName()
	{
		return storeName;
	}
	public void setStoreName(String storeName)
	{
		this.storeName = storeName;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public Timestamp getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}
	

}
