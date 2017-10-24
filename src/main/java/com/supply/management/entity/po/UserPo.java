package com.supply.management.entity.po;

import java.sql.Timestamp;

import com.supply.management.config.contants.UserType;
import com.supply.management.entity.base.BasePo;

public class UserPo extends BasePo
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 7182246487278999133L;

	private long storeId;
	private String username;
	private String password;
	private String headUrl;
	private String trueName;
	private String phone;
	private String email;
	private int sex;
	private boolean isOnline;
	private UserType userType;
	private String loginIp;
	private String loginMac;
	private Timestamp loginTime;
	public long getStoreId()
	{
		return storeId;
	}
	public void setStoreId(long storeId)
	{
		this.storeId = storeId;
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
	public String getHeadUrl()
	{
		return headUrl;
	}
	public void setHeadUrl(String headUrl)
	{
		this.headUrl = headUrl;
	}
	public String getTrueName()
	{
		return trueName;
	}
	public void setTrueName(String trueName)
	{
		this.trueName = trueName;
	}
	public String getPhone()
	{
		return phone;
	}
	public void setPhone(String phone)
	{
		this.phone = phone;
	}
	public String getEmail()
	{
		return email;
	}
	public void setEmail(String email)
	{
		this.email = email;
	}
	public int getSex()
	{
		return sex;
	}
	public void setSex(int sex)
	{
		this.sex = sex;
	}
	public boolean isOnline()
	{
		return isOnline;
	}
	public void setOnline(boolean isOnline)
	{
		this.isOnline = isOnline;
	}
	public UserType getUserType()
	{
		return userType;
	}
	public void setUserType(UserType userType)
	{
		this.userType = userType;
	}
	public String getLoginIp()
	{
		return loginIp;
	}
	public void setLoginIp(String loginIp)
	{
		this.loginIp = loginIp;
	}
	public String getLoginMac()
	{
		return loginMac;
	}
	public void setLoginMac(String loginMac)
	{
		this.loginMac = loginMac;
	}
	public Timestamp getLoginTime()
	{
		return loginTime;
	}
	public void setLoginTime(Timestamp loginTime)
	{
		this.loginTime = loginTime;
	}
	
	
	
	
}
