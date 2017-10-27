package com.supply.management.entity.dto;

import java.util.List;

import com.supply.management.entity.base.BaseDto;


public class CategoryDto extends BaseDto
{


	/**
	 * 
	 */
	private static final long serialVersionUID = -4211377812414986728L;
	private long id;
	private String categoryName;
	private List<CategoryDto> children;
	
	public String getCategoryName()
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public List<CategoryDto> getChildren()
	{
		return children;
	}
	public void setChildren(List<CategoryDto> children)
	{
		this.children = children;
	}
	
	
	
}
