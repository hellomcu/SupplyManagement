package com.supply.management.interceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.supply.exception.SupplyException;
import com.supply.management.auth.util.JwtUtil;

public class AuthInterceptorAdapter extends HandlerInterceptorAdapter
{

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception
	{
		if (isAuth(request))
		{
			return true;
		}
		throw new SupplyException("请先登录");
	}

	
	private boolean isAuth(HttpServletRequest request)
	{
		return JwtUtil.getJwtClaims(request) != null;
	}
}
