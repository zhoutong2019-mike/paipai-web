package com.paipai.controller;

import java.io.IOException;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;

import com.alibaba.fastjson.JSON;
import com.paipai.utils.BaseController;

@Controller
public class WeatherController extends BaseController {
	@Autowired
	private RestTemplate rt;

	@RequestMapping("/showWeather")
	public void showWeather(String city, HttpServletResponse resp){
		System.out.println(city);
		// 通过城市访问天气接口

		String api = "http://v.juhe.cn/weather/index?cityname=" + city + "&key=bf8aff28ca8201930a90237fc9ad284f";
		Object object = rt.getForObject(api, Object.class);
		String content = JSON.toJSONString(object);
		try {
			resp.setCharacterEncoding("utf-8");
			resp.getWriter().write(content);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
