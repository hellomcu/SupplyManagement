package com.supply.management.module.store.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.contant.UserType;
import com.supply.entity.PageInfo;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.StorePo;
import com.supply.entity.po.UserPo;
import com.supply.exception.SupplyException;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.entity.dto.AddStoreDto;
import com.supply.management.entity.dto.StoreDto;
import com.supply.management.entity.dto.UpdateStoreDto;
import com.supply.management.module.store.service.StoreService;
import com.supply.management.util.WrappedBeanCopier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "门店相关")
@RestController
@RequestMapping("/admin/store")
public class StoreController extends BaseController
{

	private StoreService mStoreService;

	@Autowired
	public StoreController(StoreService storeService)
	{
		this.mStoreService = storeService;
	}

	
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "PUT", value = "添加门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> addStore(@RequestBody @Valid AddStoreDto addStoreDto, BindingResult result, HttpServletRequest request)
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
		if (result.hasErrors())
		{
			throw new SupplyException(result.getFieldError().getDefaultMessage());
		}
		StorePo store = new StorePo();
		UserPo user = new UserPo();
		store.setStoreName(addStoreDto.getStoreName());
		store.setStorePlace(addStoreDto.getStorePlace());
		store.setContactWay(addStoreDto.getContactWay());
		store.setContacts(addStoreDto.getContacts());
		store.setDescription(addStoreDto.getDescription());
		user.setUsername(addStoreDto.getUsername());
		user.setPassword(addStoreDto.getPassword());
		mStoreService.addStore(store, user);
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取所有门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/stores", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<PageInfo<StoreDto>> findAllStores( @RequestParam("page") long page, @RequestParam("num") int num, HttpServletRequest request)
	{
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<PageInfo<StoreDto>> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<PageInfo<StoreDto>> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		PageInfo<Void> pageInfo = new PageInfo<Void>();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		PageInfo<StorePo> orderPos = mStoreService.findAllStore(pageInfo);
		PageInfo<StoreDto> result = new PageInfo<StoreDto>();
		result.setCurrentPage(orderPos.getCurrentPage());
		result.setTotalNum(orderPos.getTotalNum());
		result.setTotalPage(orderPos.getTotalPage());
		result.setItemNum(orderPos.getItemNum());
		result.setList(WrappedBeanCopier.copyPropertiesOfList(orderPos.getList(), StoreDto.class));
		return getResponse(result);
	}
	
	
	@ApiOperation(httpMethod = "DELETE", value = "根据id删除门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.DELETE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> deleteStore( @RequestParam("id") long id)
	{
		mStoreService.deleteStore(id);
		return getResponse();
	}
	
	
	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "POST", value = "更新门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> updateStore(@RequestBody UpdateStoreDto updateStoreDto)
	{
		StorePo store = new StorePo();
		//UserPo user = new UserPo();
		store.setId(updateStoreDto.getId());
		store.setStoreName(updateStoreDto.getStoreName());
		store.setStorePlace(updateStoreDto.getStorePlace());
		store.setContacts(updateStoreDto.getContacts());
		store.setDescription(updateStoreDto.getDescription());

		mStoreService.updateStore(store, null);
		return getResponse();
	}
}

