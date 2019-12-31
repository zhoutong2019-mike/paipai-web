package com.qin.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import com.qin.utils.BaseController;
@Controller
public class Foreground extends BaseController{
	
	@RequestMapping("/")
	public String toHome () {
		return "view/home";
	}
	
	
	
	
	
	
	

}
