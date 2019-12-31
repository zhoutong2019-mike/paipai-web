package com.paipai.dao;

import com.paipai.entity.Administrator;
import tk.mybatis.mapper.common.Mapper;

public interface AdministratorMapper extends Mapper<Administrator> {
	
	/**
	 * 	通过用户名查找用户
	 * @param username	用户名
	 * @return	管理员对象
	 */
	Administrator selectAdminByName(String username);
}