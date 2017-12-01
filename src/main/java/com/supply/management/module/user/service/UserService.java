package com.supply.management.module.user.service;

import com.supply.base.service.BaseService;
import com.supply.entity.po.UserPo;

public interface UserService extends BaseService
{
	UserPo userLogin(String username, String password, int userType);
}
