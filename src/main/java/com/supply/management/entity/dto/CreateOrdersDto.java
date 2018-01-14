package com.supply.management.entity.dto;

import java.util.List;
import java.util.Set;

import javax.validation.Valid;
import javax.validation.constraints.Size;

import com.supply.entity.base.BaseDto;

public class CreateOrdersDto extends BaseDto
{

	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4181303023734751096L;
	
	@Size(min = 1, message = "请先选择门店")
	@Valid
	private  Set<Long> storeIds;
	@Size(min = 1, message = "请先添加商品")
	@Valid
	private List<CreateOrderProductDetailDto> details;
	
	public Set<Long> getStoreIds()
	{
		return storeIds;
	}
	public void setStoreIds(Set<Long> storeIds)
	{
		this.storeIds = storeIds;
	}
	public List<CreateOrderProductDetailDto> getDetails()
	{
		return details;
	}
	public void setDetails(List<CreateOrderProductDetailDto> details)
	{
		this.details = details;
	}
	
	
}
