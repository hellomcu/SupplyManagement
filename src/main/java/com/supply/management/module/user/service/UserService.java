package com.supply.management.module.user.service;

import com.supply.management.base.service.BaseService;
import com.supply.management.entity.po.UserPo;

public interface UserService extends BaseService
{
	UserPo userLogin(String username, String password, int userType);
}
