package com.supply.management.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.supply.entity.base.BaseResponse;


@ControllerAdvice
public class BaseExceptionHandler
{
	
	private static final Logger mLogger = LoggerFactory.getLogger(BaseExceptionHandler.class);
	
	
	@ExceptionHandler(Exception.class)
	@ResponseBody
	public BaseResponse<Void> onExceptionHandler(Exception e)
	{
		mLogger.error(e.getMessage(), e);
		BaseResponse<Void> response = new BaseResponse<Void>();
		response.setMessage(e.getMessage());
		
		return response;
	}
}
