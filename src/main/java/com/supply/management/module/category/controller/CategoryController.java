package com.supply.management.module.category.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.supply.base.controller.BaseController;
import com.supply.contant.UserType;
import com.supply.entity.PageInfo;
import com.supply.entity.base.BaseResponse;
import com.supply.entity.po.CategoryPo;
import com.supply.entity.po.UserPo;
import com.supply.management.auth.util.JwtUtil;
import com.supply.management.entity.dto.AddAllCategoryDto;
import com.supply.management.entity.dto.AddCategoryDto;
import com.supply.management.entity.dto.AddCategoryResultDto;
import com.supply.management.entity.dto.CategoryDto;
import com.supply.management.module.category.service.CategoryService;
import com.supply.management.util.WrappedBeanCopier;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(tags = "分类相关")
@RestController
@RequestMapping("/admin/category")
public class CategoryController extends BaseController
{

	private CategoryService mCategoryService;

	@Autowired
	public CategoryController(CategoryService categoryService)
	{
		this.mCategoryService = categoryService;
	}

	
	
	@RequestMapping(method = RequestMethod.PUT, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "PUT", value = "添加一个分类(当parentId为0时,添加子分类)", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<AddCategoryResultDto> addCategory(@RequestBody AddCategoryDto addCategoryDto, HttpServletRequest request)
	{
		
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<AddCategoryResultDto> response = new BaseResponse<>();
			response.setMessage("请先登录");
			response.setCode(100);
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<AddCategoryResultDto> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		CategoryPo category = WrappedBeanCopier.copyProperties(addCategoryDto, CategoryPo.class);
		
		return getResponse(WrappedBeanCopier.copyProperties(mCategoryService.addCategory(category), AddCategoryResultDto.class));
	}
	
	
	@RequestMapping(method = RequestMethod.PUT, value="/all_category", consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@ApiOperation(httpMethod = "PUT", value = "添加一个父分类和一个子分类", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<Void> addAllCategory(@RequestBody AddAllCategoryDto addAllCategoryDto)
	{
		CategoryPo parent = new CategoryPo();
		parent.setCategoryName(addAllCategoryDto.getParentName());
		CategoryPo child = new CategoryPo();
		child.setCategoryName(addAllCategoryDto.getChildName());
		mCategoryService.addCategory(parent, child);
		return getResponse();
	}
	
	@ApiOperation(httpMethod = "GET", value = "获取所有分类", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	@RequestMapping(method = RequestMethod.GET, value="/categories", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
	public BaseResponse<List<CategoryDto>> findAllCategories( @RequestParam("page") long page, @RequestParam("num") int num, HttpServletRequest request)
	{
		UserPo loginUser = JwtUtil.getLoginUserFromJwt(request);
		if (loginUser == null)
		{
			BaseResponse<List<CategoryDto>> response = new BaseResponse<>();
			response.setCode(100);
			response.setMessage("请先登录");
			return response;
		}
		if (loginUser.getUserType().ordinal() != UserType.TYPE_ADMIN.ordinal())
		{
			BaseResponse<List<CategoryDto>> response = new BaseResponse<>();
			response.setMessage("您没有权限操作");
			return response;
		}
		
		PageInfo pageInfo = new PageInfo();
		pageInfo.setCurrentPage(page);
		pageInfo.setItemNum(num);

		Map<CategoryPo, List<CategoryPo>> map = mCategoryService.findAll(pageInfo);
		List<CategoryDto> result = new ArrayList<CategoryDto>();
		for (CategoryPo key : map.keySet())
		{
			CategoryDto parent = WrappedBeanCopier.copyProperties(key, CategoryDto.class);
			List<CategoryDto> children = WrappedBeanCopier.copyPropertiesOfList(map.get(key), CategoryDto.class);
			parent.setChildren(children);
			result.add(parent);
		}
	
		return getResponse(result);
	}
	
	
//	@ApiOperation(httpMethod = "DELETE", value = "根据id删除门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@RequestMapping(method = RequestMethod.DELETE, value="/stores", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public BaseResponse<Void> deleteStore( @RequestParam("id") long id)
//	{
//		//mStoreService.deleteStore(id);
//		return getResponse();
//	}
//	
//	
//	@RequestMapping(method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_UTF8_VALUE, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	@ApiOperation(httpMethod = "POST", value = "更新门店", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
//	public BaseResponse<Void> updateStore(@RequestBody UpdateStoreDto updateStoreDto)
//	{
//		StorePo store = new StorePo();
//		//UserPo user = new UserPo();
//		store.setId(updateStoreDto.getId());
//		store.setStoreName(updateStoreDto.getStoreName());
//		store.setStorePlace(updateStoreDto.getStorePlace());
//		store.setContacts(updateStoreDto.getContacts());
//		store.setDescription(updateStoreDto.getDescription());
//
//		//mStoreService.updateStore(store, null);
//		return getResponse();
//	}
}
