package com.paipai.controller;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paipai.service.AdminiServiceInterface;
import com.paipai.service.AdministratorService;
import com.paipai.utils.BaseController;

@Controller
@RequestMapping("/paipai/Administrator")
public class AdministratorController extends BaseController {
	@Autowired
	private AdministratorService administratorService;

	@Autowired
	private AdminiServiceInterface adminService;

	@RequestMapping("/loginIn")
	@ResponseBody
	public String login(String username, String password, HttpServletRequest req) {
		return administratorService.loginIn(username, password, req);
	}

	/// paipai/Administrator/loginout
	@RequestMapping("/loginout")
	@ResponseBody
	public String loginout(HttpServletRequest req) {
		HttpSession session = req.getSession(false);
		if (session == null || session.getAttribute("loginname") == null)
			return "当前未登录 ! 无需注销 ...";
		// 执行注销操作
		session.removeAttribute("loginname");
		session.removeAttribute("loginid");
		return "ok";
	}

	//paipai/Administrator/initUserMsg
	@GetMapping("/initUserMsg")
	@ResponseBody
	public Map<String, Object> initUserMsg() {
		System.out.println("进入initUserMsg()");
		return adminService.findAllUser();
	}
	
	//paipai/Administrator/closeAccount
	@GetMapping("/closeAccount")
	@ResponseBody
	public String closeAccount(Integer id) {
		return adminService.updateUserState(id);
	}
	//openAccount
	
	@GetMapping("/openAccount")
	@ResponseBody
	public String openAccount(Integer id) {
		return adminService.updateUserStateToOne(id);
	}
	
	//paipai/Administrator/findOne
	
	@GetMapping("/findManyUser")
	@ResponseBody
	public Map<String, Object> findManyUser(String username) {
		Map<String, Object> map = adminService.findManyUserByName(username);
		return map;
	}
	//findUserByTime
	@GetMapping("/findUserByTime")
	@ResponseBody
	public Map<String, Object> findUserByTime(Date startTime,Date endTime) {
		//将时间转换为字符串格式,并endtime+1day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDay = sdf.format(startTime);
		String endDay = sdf.format(calendar.getTime());
		
		Map<String, Object> map = adminService.findManyUserByTime(startDay,endDay);
		return map;
	}
	
	///paipai/Administrator/initCommodity
	@GetMapping("/initCommodity")
	@ResponseBody
	public Map<String, Object> initCommodity(Integer pageNum,Integer pageSize) {
		System.out.println("当前页数"+pageNum+"每页大小"+pageSize);
		Map<String, Object> map = adminService.findAllCommiditys(pageNum,pageSize);
		return map;
	}
	
	//showbiddingRecord 
	@GetMapping("/showbiddingRecord")
	@ResponseBody
	public Map<String, Object> showbiddingRecord(Integer pId) {
		Map<String, Object> map = adminService.findBiddingRecord(pId);
		return map;
	}
	
	//paipai/Administrator/stopPai
	
	@GetMapping("/stopPai")
	@ResponseBody
	public String stopPai(Integer pId) {
		String str = adminService.changeCommoState(pId);
		return str;
	}
	//paipai/Administrator/findCommodityByTime
	
	@GetMapping("/findCommodityByTime")
	@ResponseBody
	public  Map<String, Object> findCommodityByTime(Date startTime,Date endTime) {
		//将时间转换为字符串格式,并endtime+1day
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(endTime);
		calendar.add(Calendar.DAY_OF_MONTH, 1);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		String startDay = sdf.format(startTime);
		String endDay = sdf.format(calendar.getTime());
		return adminService.findCommoByPushTime(startDay,endDay);
	}
	//paipai/Administrator/description?pId='commo.pId
	
	@GetMapping("/description")
	public  String description(Integer pId,Model model) {
		System.out.println("water的description方法进入了");
		Map< String,Object> map =adminService.findDescription(pId);
		System.out.println(map);
		model.addAttribute("map", map);
		return "/tony/description";
	}
}