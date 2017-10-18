package com.supply.management.base.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supply.management.entity.base.BaseResponse;

@ControllerAdvice
public class BaseExceptionHandler
{
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseResponse<Void> onExceptionHandler(Exception e)
	{
		e.printStackTrace();
		BaseResponse<Void> response = new BaseResponse<Void>();
		response.setMessage(e.getMessage());
		return response;
	}
}
