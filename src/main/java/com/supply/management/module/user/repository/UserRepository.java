package com.supply.management.module.user.repository;

import com.supply.management.base.repository.Repository;
import com.supply.management.entity.po.UserPo;

public interface UserRepository extends Repository
{
	UserPo findOneByUsernameAndType(String username, int type);
	
	int save(UserPo user);
}
