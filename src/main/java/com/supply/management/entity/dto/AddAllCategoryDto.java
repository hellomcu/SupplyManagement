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
	

	private String childName;
	private String parentName;
	

	public String getParentName()
	{
		return parentName;
	}
	public void setParentName(String parentName)
	{
		this.parentName = parentName;
	}
	public String getChildName()
	{
		return childName;
	}
	public void setChildName(String childName)
	{
		this.childName = childName;
	}
	
}
