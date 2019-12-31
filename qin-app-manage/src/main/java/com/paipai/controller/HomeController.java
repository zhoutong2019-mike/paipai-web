package com.paipai.controller;

import java.io.File;
import java.io.IOException;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.paipai.utils.BaseController;

@Controller
public class HomeController extends BaseController {

	@RequestMapping("/")
	public String home() {
		return "tony/home";
	}

	@RequestMapping("/openWork/{work}")
	public String openWork(@PathVariable("work") String work) {
		return "tony/" + work;
	}

	@RequestMapping({ "/success", "/info" })
	public String success() {
		return "tony/success";
	}

	@RequestMapping("/login")
	public String login() {
		System.out.println(123);
		return "tony/login";
	}

	@RequestMapping("/regist")
	public String regist() {
		return "tony/regist";
	}
	/// goCenter

	@RequestMapping("/goCenter")
	public String goCenter() {
		return "tony/usercenter";
	}

	@RequestMapping("/biddingroom")
	public String goRoom() {
		return "tony/biddingroom";
	}
	// callback

	@RequestMapping("/callback")
	public void callback(HttpServletRequest req, HttpServletResponse resp) {
		Cookie[] cookies = req.getCookies();
		String path = "/";
		for (Cookie cookie : cookies) {
			if (cookie.getName().equals("callbackpath")) {
				path = cookie.getValue();
				System.out.println(path);
				String[] split = path.split("/");
				path = split[1];
  
			}
		}
		try {
			resp.sendRedirect(path);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	//openOrderMsgPage
	@RequestMapping("/openOrderMsgPage")
	public String openOrderMsgPage(Integer orderId,Integer pId,Model model) {
		model.addAttribute("orderId", orderId);
		model.addAttribute("pId", pId);
		return "tony/ordermsg";
	}
	
	//order
	@RequestMapping("/order")
	public String goOrder(Integer orderId,Integer pId,Model model) {
		return "tony/order";
	}
	///openOrderMsgPage2
	@RequestMapping("/openOrderMsgPageTwo")
	public String openOrderMsgPageTwo(Integer orderId,Model model) {
		model.addAttribute("orderId", orderId);
		return "tony/ordermsgtwo";
	}
	
	@Value(value = "${paipai.uploadroot}")
	private String uploadroot ;
	//upload
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(MultipartFile file,HttpServletRequest req) throws IllegalStateException, IOException {
		String img = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5)+file.getOriginalFilename();
		String path = uploadroot+"\\"+img; 
		file.transferTo(new File(path));
		return img;
	}
	//uploadInfo
	
	@ResponseBody
	@RequestMapping("/uploadInfo")
	public String uploadInfo(MultipartFile file,HttpServletRequest req) throws IllegalStateException, IOException {
		String info = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5)+file.getOriginalFilename();
		String path = uploadroot+"\\"+info; 
		file.transferTo(new File(path));
		return info;
	}
	//openPaiRecord
	
	@RequestMapping("/openPaiRecord")
	public String openPaiRecord(Integer pId,Model model) {
		model.addAttribute("pId", pId);
		return "tony/pai_record";
	}
	//openbiddingRoom
	@RequestMapping("/openbiddingRoom")
	public String openbiddingRoom(Integer pId,Model model) {
		model.addAttribute("pId", pId);
		return "tony/bidding-room";
	}
	
	@RequestMapping("/test")
	public String test() {
		return "tony/test";
	}
}