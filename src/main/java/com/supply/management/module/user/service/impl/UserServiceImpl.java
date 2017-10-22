package com.supply.management.module.user.service.impl;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.supply.management.entity.po.UserPo;
import com.supply.management.exception.SupplyException;
import com.supply.management.module.user.repository.UserRepository;
import com.supply.management.module.user.service.UserService;
import com.supply.management.util.Md5;

@Service
public class UserServiceImpl implements UserService
{
	private UserRepository mUserRepository;


	@Override
	public UserPo userLogin(String username, String password, int userType)
	{
		UserPo loginUser = mUserRepository.findOneByUsernameAndType(username, userType);
		if (loginUser == null)
		{
			throw new SupplyException("登录失败，此用户不存在");
		}
		String pwdMd5 = Md5.getMD5Str(password);
		if (pwdMd5 == null || !pwdMd5.equals(loginUser.getPassword()))
		{
			throw new SupplyException("登录失败，此用户名或密码不正确");
		}
		return loginUser;
	}


	@Resource(name = "userRepository")
	public void setUserRepository(UserRepository userRepository)
	{
		this.mUserRepository = userRepository;
	}

	
}
