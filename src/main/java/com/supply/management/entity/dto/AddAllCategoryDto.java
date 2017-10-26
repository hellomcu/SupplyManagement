package com.supply.management.entity.dto;

import com.supply.management.entity.base.BaseDto;

/**
 * 添加父/子类别
 * @author ly
 *
 */
public class AddAllCategoryDto extends BaseDto
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -4856384357452193394L;
	
	
	private long id;
	private String categoryName;
	private String parentName;
	
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public String getParentName()
	{
		return parentName;
	}
	public void setParentName(String parentName)
	{
		this.parentName = parentName;
	}
	public String getCategoryName()
	{
		return categoryName;
	}
	public void setCategoryName(String categoryName)
	{
		this.categoryName = categoryName;
	}
	
}
