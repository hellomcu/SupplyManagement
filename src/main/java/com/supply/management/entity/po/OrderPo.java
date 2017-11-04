package com.supply.management.entity.po;

import java.math.BigDecimal;

import com.supply.management.config.contants.OrderStatus;
import com.supply.management.entity.base.BasePo;

public class OrderPo extends BasePo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4518621883899524021L;
	
	
	private long storeId;
	private BigDecimal totalPrice;
	private int productNum;
	private String receivingAddress;
	private String contacts;
	private String contactWay;
	private OrderStatus orderStatus;
	private String orderRemark;
	private String storeName;
	
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
	

}
