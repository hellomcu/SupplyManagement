package com.supply.management.entity.base;

public class BaseResponse<T> extends BaseEntity
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -547109556387874524L;
	private int code;
	private boolean showMessage = true;
	private String message;
	private T data;
	
	
	public int getCode()
	{
		return code;
	}
	public void setCode(int code)
	{
		this.code = code;
	}
	public boolean isShowMessage()
	{
		return showMessage;
	}
	public void setShowMessage(boolean showMessage)
	{
		this.showMessage = showMessage;
	}
	public String getMessage()
	{
		return message;
	}
	public void setMessage(String message)
	{
		this.message = message;
	}
	public T getData()
	{
		return data;
	}
	public void setData(T data)
	{
		this.data = data;
	}

}
