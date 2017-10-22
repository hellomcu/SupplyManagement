package com.supply.management.entity.dto;

import com.supply.management.entity.base.BaseDto;

public class UserLoginDto extends BaseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3431275049029314524L;
	private String username;
	private String password;
	private int type;
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
	public int getType()
	{
		return type;
	}
	public void setType(int type)
	{
		this.type = type;
	}
	
	
	
}
