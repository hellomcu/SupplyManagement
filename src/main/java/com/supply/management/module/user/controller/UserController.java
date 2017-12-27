package com.supply.management.module.user.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.UserPo;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.config.contants.ServerConfig;
import com.supply.management.entity.dto.UserLoginDto;
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

	@RequestMapping(method = RequestMethod.POST, value = "/user_login", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "用户登录", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> userLogin(@RequestBody UserLoginDto userLoginDto, HttpServletResponse response)
	{
		UserPo userPo = mUserService.userLogin(userLoginDto.getUsername(), userLoginDto.getPassword(),
				userLoginDto.getType());
		String jwt = JwtUtil.createJwt(userPo);

		// response.setHeader("Access-Control-Allow-Origin", "*");
		// response.setHeader("Access-Control-Allow-Methods", "*");
		// response.setHeader("Access-Control-Max-Age", "3600");
		// response.setHeader("Access-Control-Allow-Headers",
		// "DNT,X-Mx-ReqToken,Keep-Alive,User-Agent,X-Requested-With,If-Modified-Since,Cache-Control,Content-Type,Authorization,SessionToken");
		// // 允许跨域请求中携带cookie
		// response.setHeader("Access-Control-Allow-Credentials", "true");

		response.addHeader("Set-Cookie", ServerConfig.TOKEN_HEADER + "=" + jwt + "; Path=/; HttpOnly");
		response.addHeader("Set-Cookie", "username=" + userPo.getUsername() + "; Path=/");
		return getResponse();
	}

}
