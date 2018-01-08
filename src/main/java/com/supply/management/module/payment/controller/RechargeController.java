package com.supply.management.module.payment.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.contant.UserType;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.RechargePo;
import com.supply.entity.po.UserPo;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.config.contants.ServerConfig;
import com.supply.management.entity.dto.RechargeDto;
import com.supply.management.module.payment.service.RechargeService;
import com.supply.management.util.WrappedBeanCopier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "支付相关")
@RestController
@RequestMapping("/admin/payment")
public class RechargeController extends BaseController
{

	private RechargeService mRechargeService;

	@Autowired
	public RechargeController(RechargeService rechargeService)
	{
		this.mRechargeService = rechargeService;
	}

	@RequestMapping(method = RequestMethod.POST, value = "/recharge", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "充值", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> recharge(@RequestBody RechargeDto rechargeDto, HttpServletRequest request)
	{
		
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<Void> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		rechargeDto.setFromId(loginUser.getId());
		RechargePo rechargePo = WrappedBeanCopier.copyProperties(rechargeDto, RechargePo.class);
		mRechargeService.recharge(rechargePo);
		return getResponse();
	}
	
	
}
