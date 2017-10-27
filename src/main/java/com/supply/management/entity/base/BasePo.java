package com.supply.management.entity.base;

import java.sql.Timestamp;

public abstract class BasePo extends BaseEntity
{

	

	/**
	 * 
	 */
	private static final long serialVersionUID = 4898488677844303181L;
	protected long id;
	protected String remark;
	protected int status;
	protected Timestamp createTime;
	protected Timestamp updateTime;
	
	
	public long getId()
	{
		return id;
	}
	public void setId(long id)
	{
		this.id = id;
	}
	public int getStatus()
	{
		return status;
	}
	public void setStatus(int status)
	{
		this.status = status;
	}
	public Timestamp getCreateTime()
	{
		return createTime;
	}
	public void setCreateTime(Timestamp createTime)
	{
		this.createTime = createTime;
	}
	public Timestamp getUpdateTime()
	{
		return updateTime;
	}
	public void setUpdateTime(Timestamp updateTime)
	{
		this.updateTime = updateTime;
	}
	public String getRemark()
	{
		return remark;
	}
	public void setRemark(String remark)
	{
		this.remark = remark;
	}
	@Override
	public int hashCode()
	{
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (id ^ (id >>> 32));
		return result;
	}
	@Override
	public boolean equals(Object obj)
	{
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		BasePo other = (BasePo) obj;
		if (id != other.id)
			return false;
		return true;
	}
	
	/**
     * 返回易读的时间格式
     * @return
     */
//    public String getReadableTime()
//    {
//        long now = System.currentTimeMillis();
//        //发布时间距现在的时间差
//        long difference = now - createTime.getTime();
//        if (difference < 60000)         //1分钟内
//            return "刚刚";
//        if (difference < 3600000)      //1小时内
//            return difference / 60000 + "分钟前";
//        if (difference < 86400000)      //1天内
//            return difference / 3600000 + "小时前";
//        return createTime.toString();
//    }
	
	
}
