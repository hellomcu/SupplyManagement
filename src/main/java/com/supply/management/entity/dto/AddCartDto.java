package com.supply.management.entity.dto;

import java.util.List;

import com.supply.entity.base.BaseDto;

public class AddCartDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 3462591519498779795L;

	private long userId;
	private List<AddCartDetailDto> details;
	private String cartRemark;
	

	public String getCartRemark()
	{
		return cartRemark;
	}
	public void setCartRemark(String cartRemark)
	{
		this.cartRemark = cartRemark;
	}
	public List<AddCartDetailDto> getDetails()
	{
		return details;
	}
	public void setDetails(List<AddCartDetailDto> details)
	{
		this.details = details;
	}
	public long getUserId()
	{
		return userId;
	}
	public void setUserId(long userId)
	{
		this.userId = userId;
	}
	
}
