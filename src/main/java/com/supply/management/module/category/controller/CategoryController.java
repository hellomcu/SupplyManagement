package com.supply.management.module.category.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
import com.supply.management.entity.dto.AddAllCategoryDto;
import com.supply.management.entity.dto.AddCategoryDto;
import com.supply.management.entity.dto.CategoryDto;
import com.supply.management.entity.po.CategoryPo;
import com.supply.management.module.category.service.CategoryService;

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
	public BaseResponse<Void> addCategory(@RequestBody AddCategoryDto addCategoryDto)
	{
		CategoryPo category = WrappedBeanCopier.copyProperties(addCategoryDto, CategoryPo.class);
		mCategoryService.addCategory(category);
		return getResponse();
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
	public BaseResponse<List<CategoryDto>> findAllCategories( @RequestParam("page") long page, @RequestParam("num") int num)
	{
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
