package com.supply.management.entity.dto;

import com.supply.management.entity.base.BaseDto;

public class AddStoreDto extends BaseDto
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1696295671283400201L;

	private String storeName;
	private String storePlace;
	private String contacts;
	private String description;
	private String username;
	private String password;
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
	public String getUsername()
	{
		return username;
	}
	public void setUsername(String username)
	{
		this.username = username;
	}
	public String getPassword()
	{
		return password;
	}
	public void setPassword(String password)
	{
		this.password = password;
	}
	
	
}
