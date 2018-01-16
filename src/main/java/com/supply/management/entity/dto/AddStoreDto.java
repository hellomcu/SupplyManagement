package com.supply.management.entity.dto;

import org.hibernate.validator.constraints.Length;

import com.supply.entity.base.BaseDto;

public class AddStoreDto extends BaseDto
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = -1696295671283400201L;

	@Length(min=2, message="门店名称至少为2个字")
	private String storeName;
	@Length(min=2, message="门店地址至少为2个字")
	private String storePlace;
	@Length(min=2, message="联系人至少为2个字")
	private String contacts;
	@Length(min=11, message="联系方式至少为11位")
	private String contactWay;
	private String description;
	@Length(min=3, message="帐号至少为3位")
	private String username;
	@Length(min=6, message="秘密至少为6位")
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
	public String getContactWay()
	{
		return contactWay;
	}
	public void setContactWay(String contactWay)
	{
		this.contactWay = contactWay;
	}
	
	
}
