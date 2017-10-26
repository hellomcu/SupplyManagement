package com.supply.management.entity.po;

import com.supply.management.entity.base.BasePo;


public class CategoryPo extends BasePo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6963110855333912167L;
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
