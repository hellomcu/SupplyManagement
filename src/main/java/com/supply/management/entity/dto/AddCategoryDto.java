package com.supply.management.entity.dto;

import com.supply.management.entity.base.BaseDto;

/**
 * 添加类别(当parentId=0时,为添加父类别)
 * @author ly
 *
 */
public class AddCategoryDto extends BaseDto
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 4707322148127758699L;
	
	private long parentId;
	private String categoryName;
	
	public long getParentId()
	{
		return parentId;
	}
	public void setParentId(long parentId)
	{
		this.parentId = parentId;
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
