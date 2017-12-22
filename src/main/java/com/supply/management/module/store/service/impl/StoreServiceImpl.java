package com.supply.management.module.store.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.supply.contant.UserType;
import com.supply.entity.PageInfo;
import com.supply.entity.po.StorePo;
import com.supply.entity.po.UserPo;
import com.supply.exception.SupplyException;
import com.supply.management.module.store.repository.StoreRepository;
import com.supply.management.module.store.service.StoreService;
import com.supply.management.module.user.repository.UserRepository;
import com.supply.management.util.Md5;
import com.supply.management.util.StringUtil;

@Service
public class StoreServiceImpl implements StoreService
{
	private StoreRepository mStoreRepository;
	private UserRepository mUserRepository;
	
	@Transactional
	@Override
	public void addStore(StorePo store, UserPo userPo)
	{
		int rows = mStoreRepository.save(store);
		if (rows != 1)
		{
			throw new SupplyException("添加门店失败,请稍后重试");
		}
		userPo.setStoreId(store.getId());
		String pwdMd5 = Md5.getMD5Str(userPo.getPassword());
		if (StringUtil.isEmpty(pwdMd5))
		{
			throw new SupplyException("登录密码不合法,请修改后重试");
		}
		userPo.setPassword(pwdMd5);
		userPo.setUserType(UserType.TYPE_STORE);
		rows = addStoreUser(userPo);
		if (rows != 1)
		{
			throw new SupplyException("分配门店帐号,请稍后重试");
		}
	}
	
	private int addStoreUser(UserPo user)
	{
		return mUserRepository.save(user);
	}
	

	@Override
	public PageInfo<StorePo> findAllStore(PageInfo<Void> page)
	{
		PageInfo<StorePo> result = new PageInfo<>();
		List<StorePo> list = mStoreRepository.findAll(page);
		long count = mStoreRepository.count();
		result.setList(list);
		result.setTotalNum(count);
		result.setItemNum(page.getItemNum());
		result.setTotalPage(result.calcTotalPage());
		result.setCurrentPage(page.getCurrentPage());
		return result;
	}
	
	@Override
	public void deleteStore(long id)
	{
		int rows = mStoreRepository.delete(id);
		if (rows != 1)
		{
			throw new SupplyException("删除门店失败,请稍后重试");
		}
	}
	
	
	//@Transactional
	@Override
	public void updateStore(StorePo store, UserPo user)
	{
		int rows = mStoreRepository.update(store);
		if (rows != 1)
		{
			throw new SupplyException("更新门店失败,请稍后重试");
		}
	}
	
	
	@Resource(name="storeRepository")
	public void setStoreRepository(StoreRepository storeRepository)
	{
		this.mStoreRepository = storeRepository;
	}

	@Resource(name="userRepository")
	public void setUserRepository(UserRepository userRepository)
	{
		this.mUserRepository = userRepository;
	}





	

	
}
