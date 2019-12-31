package com.paipai.service;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paipai.entity.Administrator;
import com.paipai.utils.MikeUtil;
import com.paipai.dao.AdministratorMapper;

@Transactional
@Service
public class AdministratorService {
	@Autowired
	private AdministratorMapper administratorMapper;

	public AdministratorMapper getAdministratorMapper() {
		return administratorMapper;
	}

	public String loginIn(String username, String password, HttpServletRequest req) {
		if (!MikeUtil.notNullOrEmpty(username))
			return "请输入用户名 !  ";
		if (!MikeUtil.notNullOrEmpty(password))
			return "请输入密码 ! ";
		// 1.判断用户名是否存在
		Administrator admin = administratorMapper.selectAdminByName(username);
		if (admin == null)
			return "管理员账户不存在 ! ";
		// 2.判断密码是否正确
		if (!admin.getPassword().equals(password))
			return " 密码不正确 ! ";
		// 3. 放入session域中
		req.getSession().setAttribute("loginid", admin.getId());
		req.getSession().setAttribute("loginname", admin.getName());
		return "ok";
	}


}