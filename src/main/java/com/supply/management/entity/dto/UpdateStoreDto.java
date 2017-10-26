package com.supply.management.entity.dto;

import com.supply.management.entity.base.BaseDto;

public class UpdateStoreDto extends BaseDto
{

	


	/**
	 * 
	 */
	private static final long serialVersionUID = 7178853844919349139L;
	private long id;
	private String storeName;
	private String storePlace;
	private String contacts;
	private String description;

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
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
}
