package com.supply.management.module.store.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.management.base.controller.BaseController;
import com.supply.management.beanutil.WrappedBeanCopier;
import com.supply.management.entity.PageInfo;
import com.supply.management.entity.base.BaseResponse;
import com.supply.management.entity.dto.AddStoreDto;
import com.supply.management.entity.dto.StoreDto;
import com.supply.management.entity.dto.UpdateStoreDto;
import com.supply.management.entity.po.StorePo;
import com.supply.management.entity.po.UserPo;
import com.supply.management.module.store.service.StoreService;

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
	public BaseResponse<Void> addStore(@RequestBody AddStoreDto addStoreDto)
	{
		StorePo store = new StorePo();
		UserPo user = new UserPo();
		store.setStoreName(addStoreDto.getStoreName());
		store.setStorePlace(addStoreDto.getStorePlace());
		store.setContacts(addStoreDto.getContacts());
		store.setDescription(addStoreDto.getDescription());
		user.setUsername(addStoreDto.getUsername());
		user.setPassword(addStoreDto.getPassword());
		mStoreService.addStore(store, user);
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取所有门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/stores", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<List<StoreDto>> findAllStores( @RequestParam("page") long page, @RequestParam("num") int num)
	{
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		List<StorePo> stores = mStoreService.findAllStore(pageInfo);
		return getResponse(WrappedBeanCopier.copyPropertiesOfList(stores, StoreDto.class));
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

