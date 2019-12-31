package com.paipai.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paipai.entity.User;
import com.paipai.service.UserService;
import com.paipai.utils.BaseController;
import com.paipai.utils.MikeUtil;
import com.paipai.utils.UserUtil;

@Controller
@RequestMapping("/paipai/User")
public class UserController extends BaseController {
	@Autowired
	private UserService userService;

	@RequestMapping("/logining")
	@ResponseBody
	public String logining(String username, String password, HttpServletRequest req) {
		if (!MikeUtil.notNullOrEmpty(username))
			return "用户名为空  ! ";
		if (!MikeUtil.notNullOrEmpty(password))
			return "密码为空  ! ";
		User user = userService.userIsExist(username);
		if (userService.userIsExist(username) == null) // 判断用户是否存在,不存在返回用户不存在
			return "用户不存在";
		if (!userService.pwIsRight(username, password))// 哦按段密码是否正确,不正确返回密码不正确
			return "密码不正确 ! ";
		HttpSession session = req.getSession();
		session.setAttribute("loginmsg", username);
		session.setAttribute("loginid", user.getId());
		return "登录成功 ! ";// 全部过关,返回登录成功;并在session中赋值
	}

	@RequestMapping("/loginout")
	public void loginOut(HttpServletRequest req, HttpServletResponse resp) {
		HttpSession session = req.getSession();
		resp.setContentType("text/html;charset=utf-8");
		try {
			if (session == null || session.getAttribute("loginmsg") == null) {
				resp.getWriter().write("<script>alert('该用户未登录 ! 无法注销 ')</script>");
				resp.setHeader("refresh", "0;url=/");
			}
			session.removeAttribute("loginmsg");
			resp.getWriter().write("<script>layer.alert('注销成功! ')</script>");
			resp.setHeader("refresh", "0;url=/");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@RequestMapping("/isExist")
	@ResponseBody
	public String isExist(String username) {
		if (userService.userIsExist(username) != null)
			return "exist";
		return "others";
	}

	@RequestMapping("/goRegist")
	@ResponseBody
	public String goRegist(User user, String passwordTwo) {
		if (!UserUtil.checkUser(user.getUsername().trim()))
			return "用户名格式错误,4-16位字母、数字 、下划线";
		if (!UserUtil.checkPassword(user.getPassword().trim()))
			return "密码为空! ";
		if (!passwordTwo.equals(user.getPassword().trim()))
			return "两次密码不一致!";
		if (!UserUtil.checkMobile(user.getMobile().trim()))
			return "手机号格式错误 ! ";
//		if(!UserUtil.checkEmail(user.getEmail().trim()))
//			return "请输入正确邮箱 ! " ;
		// 全部判定成功,给以注册,为防止两个人同时注册一个账户
		if (userService.userIsExist(user.getUsername().trim()) != null)
			return "用户名已存在";
		synchronized (user.getUsername().trim().intern()) {
			if (userService.userIsExist(user.getUsername().trim()) != null)
				return "用户名已被占用";
			try {
				user.setUsername(user.getUsername().toLowerCase().trim());
				user.setPassword(user.getPassword().toLowerCase().trim());
				user.setMobile(user.getMobile().toLowerCase().trim());
				// user.setEmail(user.getEmail().toLowerCase().trim());
				user.setBalance((double) 0);
				byte state = 1;
				user.setState(state);
				userService.insertUser(user);
			} catch (Exception e) {
				return "网络状况,注册失败";
			}
		}
		return "ok";
	}

	@RequestMapping("/showcenter")
	@ResponseBody
	public User showCenter(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("loginmsg");
		if (!MikeUtil.notNullOrEmpty(username))
			return null;
		User user = userService.findUserByName(username);
		return user;
	}
	// updateMsg

	@RequestMapping("/updateMsg")
	@ResponseBody
	public String updateMsg(User user, HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("loginmsg");
		user.setUsername(username);
		boolean f = userService.updateUserByUser(user);
		return f ? "ok" : "other";
	}
	// recharge

	@RequestMapping("/recharge")
	@ResponseBody
	public String recharge(Double rechargemMoney, HttpServletRequest req) {
		if (rechargemMoney == null) {
			return "您输入的金额有误! ";
		}
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		String str = userService.rechargeByUid(uId, rechargemMoney);
		return str;
	}

	@RequestMapping("/findAllUser")
	@ResponseBody
	public Map<String, Object> findAllUser() {
		System.out.println("进入findAllUser()");
		Map<String, Object> map = new HashMap<String, Object>();
		List<User> list = userService.findUsers();
		map.put("list", list);
		System.out.println(map);
		return map;
	}
	
	//paipai/User/updateUserState
	@RequestMapping("/updateUserState")
	@ResponseBody
	public String updateUserState(Integer id) {
		return userService.updateUserStateS(id);
	}
	//updateUserStateToOne
	@RequestMapping("/updateUserStateToOne")
	@ResponseBody
	public String updateUserStateToOne(Integer id) {
		return userService.updateUserStateToOneS(id);
	}
	
	///paipai/User/findManyUserByName
	@RequestMapping("/findManyUserByName")
	@ResponseBody
	public Map<String,Object> findManyUserByName(String username) {
		return userService.findManyUserByName(username);
	}
	
	//findManyUserByTime
	
	@RequestMapping("/findManyUserByTime")
	@ResponseBody
	public Map<String,Object> findManyUserByTime(String startDay,String endDay) {
		System.out.println(startDay+"cake:"+endDay);
		return userService.findManyUserByTime(startDay,endDay);
	}
}