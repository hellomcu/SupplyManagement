package com.supply.management.base.controller;

import java.io.Serializable;
import java.util.List;

import com.supply.management.entity.base.BaseResponse;

public abstract class BaseController
{

	
	protected <T extends Serializable> BaseResponse<T> getResponse(T t)
	{
		BaseResponse<T> response = new BaseResponse<T>();
		response.setData(t);
		response.setShowMessage(false);
		response.setCode(1);
		return response;
	}
	
	protected <T extends Serializable> BaseResponse<List<T>> getResponse(List<T> t)
	{
		BaseResponse<List<T>> response = new BaseResponse<List<T>>();
		response.setData(t);
		response.setShowMessage(false);
		response.setCode(1);
		return response;
	}

	
	protected BaseResponse<Void> getResponse()
	{
		BaseResponse<Void> response = new BaseResponse<Void>();
		response.setShowMessage(false);
		response.setCode(1);
		return response;
	}
}
