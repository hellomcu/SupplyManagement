package com.supply.management.entity.po;

import com.supply.management.entity.base.BasePo;

public class StorePo extends BasePo
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -932709462221273061L;
	
	private long id;
	private String storeName;
	private String storePlace;
	private String contacts;
	private String contactWay;
	private String description;
	private int storeType;
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
	public String getContactWay()
	{
		return contactWay;
	}
	public void setContactWay(String contactWay)
	{
		this.contactWay = contactWay;
	}
	
	public String getDescription()
	{
		return description;
	}
	public void setDescription(String description)
	{
		this.description = description;
	}
	public int getStoreType()
	{
		return storeType;
	}
	public void setStoreType(int storeType)
	{
		this.storeType = storeType;
	}
	
	
	
}
