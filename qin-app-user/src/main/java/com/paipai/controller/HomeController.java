package com.paipai.controller;

import java.io.File;
import java.io.IOException;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.data.redis.core.SetOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

import com.huaban.analysis.jieba.JiebaSegmenter;
import com.huaban.analysis.jieba.JiebaSegmenter.SegMode;
import com.huaban.analysis.jieba.SegToken;
import com.paipai.entity.Commodity;
import com.paipai.service.CommodityService;
import com.paipai.utils.BaseController;

@Controller
public class HomeController extends BaseController {

	@RequestMapping("/")
	public String home() {
		return "mike/home";
	}

	@RequestMapping("/openWork/{work}")
	public String openWork(@PathVariable("work") String work) {
		return "mike/" + work;
	}

	@RequestMapping({ "/success", "/info" })
	public String success() {
		return "mike/success";
	}

	@RequestMapping("/login")
	public String login() {
		return "mike/login";
	}

	@RequestMapping("/regist")
	public String regist() {
		return "mike/regist";
	}
	/// goCenter

	@RequestMapping("/goCenter")
	public String goCenter() {
		return "mike/usercenter";
	}

	@RequestMapping("/biddingroom")
	public String goRoom() {
		return "mike/biddingroom";
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

	// openOrderMsgPage
	@RequestMapping("/openOrderMsgPage")
	public String openOrderMsgPage(Integer orderId, Integer pId, Model model) {
		model.addAttribute("orderId", orderId);
		model.addAttribute("pId", pId);
		return "mike/ordermsg";
	}

	// order
	@RequestMapping("/order")
	public String goOrder(Integer orderId, Integer pId, Model model) {
		return "mike/order";
	}

	/// openOrderMsgPage2
	@RequestMapping("/openOrderMsgPageTwo")
	public String openOrderMsgPageTwo(Integer orderId, Model model) {
		model.addAttribute("orderId", orderId);
		return "mike/ordermsgtwo";
	}

	@Value(value = "${paipai.uploadroot}")
	private String uploadroot;

	// upload
	@ResponseBody
	@RequestMapping("/upload")
	public String upload(MultipartFile file, HttpServletRequest req) throws IllegalStateException, IOException {
		String img = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5) + file.getOriginalFilename();
		String path = uploadroot + "\\" + img;
		file.transferTo(new File(path));
		return img;
	}
	// uploadInfo

	@ResponseBody
	@RequestMapping("/uploadInfo")
	public String uploadInfo(MultipartFile file, HttpServletRequest req) throws IllegalStateException, IOException {
		String info = UUID.randomUUID().toString().replaceAll("-", "").substring(0, 5) + file.getOriginalFilename();
		String path = uploadroot + "\\" + info;
		file.transferTo(new File(path));
		return info;
	}
	// openPaiRecord

	@RequestMapping("/openPaiRecord")
	public String openPaiRecord(Integer pId, Model model) {
		model.addAttribute("pId", pId);
		return "mike/pai_record";
	}

	@RequestMapping("/test")
	public String test() {
		return "mike/test";
	}

	/// clear_cache 清理缓存
	@CacheEvict(cacheNames = "cache01")
	@ResponseBody
	@RequestMapping("/clear_cache")
	public String clearCache() {
		return "clear success ! ";
	}
	@Autowired
	private JiebaSegmenter jseg;
	@Autowired
	private CommodityService commService;
	@Autowired
	private StringRedisTemplate strRedis;

	// build_cache
	@ResponseBody
	@RequestMapping("/build_cache")
	public String buildCache() {
		SetOperations<String, String> set = strRedis.opsForSet();
		List<Commodity> list = commService.findAll();
		System.out.println(list.size());
		for (Commodity commodity : list) {
			Integer id = commodity.getpId();
			String name = commodity.getpName();
			List<SegToken> list2 = jseg.process(name, SegMode.INDEX);
			for (SegToken segToken : list2) {
				if (segToken.word.length() < 2)
					continue;
				set.add(segToken.word, id + "");
			}
		}
		return "buildCache success ! ";
	}
}