package com.supply.management.module.user.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.supply.management.base.controller.BaseController;
import com.supply.management.entity.base.BaseResponse;
import com.supply.management.entity.dto.UserLoginDto;
import com.supply.management.entity.po.UserPo;
import com.supply.management.module.user.service.UserService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "用户相关")
@RestController
@RequestMapping("/user")
public class UserController extends BaseController
{

	private UserService mUserService;

	@Autowired
	public UserController(UserService userService)
	{
		this.mUserService = userService;
	}

	
	
	@RequestMapping(method = RequestMethod.POST, value="/user_login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "用户登录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletRequest request)
	{
		UserPo userPo = mUserService.userLogin(userLoginDto.getUsername(), userLoginDto.getPassword(), userLoginDto.getType());
		request.getSession().setAttribute("loginUser", userPo);
		return getResponse();
	}
	

}
