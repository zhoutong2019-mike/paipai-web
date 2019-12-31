package com.paipai.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paipai.entity.Commodity;
import com.paipai.entity.Record;
import com.paipai.service.CommodityService;
import com.paipai.service.RecordService;
import com.paipai.utils.BaseController;


@Controller
@RequestMapping("/paipai/Commodity")
public class CommodityController extends BaseController {
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private RecordService recordService;
	
	
	// 1.关键字全局查询
	@RequestMapping("/findAll")
	@ResponseBody
	@Cacheable(cacheNames = "cache01")
	public Map<String, Object> findAll(String keyword, Integer pageNum, Integer pageSize) {
		Map<String, Object> map = commodityService.findAllByKw(keyword, pageNum, pageSize);
		return map;
	}

	// 2.寻找奢侈品
	@RequestMapping("/findLuxury")
	@ResponseBody
	public Map<String, Object> findLuxury(Integer pageNum, Integer pageSize) {
		Map<String, Object> map = commodityService.findAllByType(1, pageNum, pageSize);
		return map;
	}

	// 3.寻找艺术品
	@RequestMapping("/findArtwork")
	@ResponseBody
	public Map<String, Object> findArtwork(Integer pageNum, Integer pageSize) {
		Map<String, Object> map = commodityService.findAllByType(2, pageNum, pageSize);
		return map;
	}

	// 4.寻找和田玉!
	@RequestMapping("/findHetian")
	@ResponseBody
	public Map<String, Object> findHetian(Integer pageNum, Integer pageSize) {
		Map<String, Object> map = commodityService.findAllByType(4, pageNum, pageSize);
		return map;
	}

	// 5寻找紫砂壶!
	@RequestMapping("/findCeramics")
	@ResponseBody
	public Map<String, Object> findCeramics(Integer pageNum, Integer pageSize) {
		Map<String, Object> map = commodityService.findAllByType(3, pageNum, pageSize);
		return map;
	}

	// 6. 寻找商品详情所需model
	/// description
	@RequestMapping("/description")
	public String description(Integer pId, Model model,HttpServletRequest req) {
		// 通过商品id查询两张表 一个是单个商品表commodity,一个是出价记录表list
		Commodity comm = commodityService.findCommByPId(pId);
		// 查询出价记录的所有记录
		List<Record> listall = recordService.findRecordByPId(pId);
		//查询该商品的拍卖状态 
		Integer state = commodityService.findStateBy(pId);
		//查询该用户是否有报名
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		boolean  isEnroll = recordService.findRecordByUidAndCid(pId,uId);
		// 查询出价记录的前三条记录,按照顺序排列
		List<Record> listpart = recordService.findRecordByPIdAndDesc(pId);
		// 查询所有报名出价的用户数量
		Integer enrollCounts = recordService.findEnrollCounts(pId);
		// 查询所有出价的记录
		Integer bidCounts = recordService.findBidCounts(pId);
		List<Record> subList2 = null;
		Record maxRecord = null;
		//預防空指針或者索引越界
		if (listall != null && listall.size() > 0) {
			subList2 = listall.subList(1, listall.size());
			maxRecord = listall.get(0);// 出价 最高 的 记录
		}
		List<Record> subList = null;
		if (listpart != null && listpart.size() > 0) {
			subList = listpart.subList(1, listpart.size());
		}
		Long surplusTime = null;
		if(comm!=null&&comm.getPublishTime()!=null) {
			Date publishTime = comm.getPublishTime();//发布时间
			//默认每个发布的拍卖品有三天72小时的时间换算为秒:72*60*60
			//计算截止到现在,还剩余的时间: 现在的时间戳-发布的时间戳/1000(秒)/
			Long totalTime = (long) (72*60*60) ;
			long time1 = publishTime.getTime();
			long time2 = new Date().getTime();
			surplusTime =totalTime-(time2-time1)/1000;
		}
		model.addAttribute("comm", comm);
		model.addAttribute("listall", subList2);
		model.addAttribute("listpart", subList);
		model.addAttribute("enrollCounts", enrollCounts);
		model.addAttribute("bidCounts", bidCounts);
		model.addAttribute("maxRecord", maxRecord);
		model.addAttribute("surplusTime",surplusTime);
		model.addAttribute("isEnroll", isEnroll);
		model.addAttribute("state",state);
		return "/mike/description";
	}
	
	//6.1 管理查看商品详情
	//paipai/Commodity/findDescription
	@RequestMapping("/findDescription")
	@ResponseBody
	public Map<String,Object> findDescription(Integer pId) {
		Map<String,Object> map = new HashMap<String,Object>();
		// 通过商品id查询两张表 一个是单个商品表commodity,一个是出价记录表list
		Commodity comm = commodityService.findCommByPId(pId);
		// 查询出价记录的所有记录
		List<Record> listall = recordService.findRecordByPId(pId);
		//查询该商品的拍卖状态 
		Integer state = commodityService.findStateBy(pId);
		// 查询出价记录的前三条记录,按照顺序排列
		List<Record> listpart = recordService.findRecordByPIdAndDesc(pId);
		// 查询所有报名出价的用户数量
		Integer enrollCounts = recordService.findEnrollCounts(pId);
		// 查询所有出价的记录
		Integer bidCounts = recordService.findBidCounts(pId);
		List<Record> subList2 = null;
		Record maxRecord = null;
		//預防空指針或者索引越界
		if (listall != null && listall.size() > 0) {
			subList2 = listall.subList(1, listall.size());
			maxRecord = listall.get(0);// 出价 最高 的 记录
		}
		List<Record> subList = null;
		if (listpart != null && listpart.size() > 0) {
			subList = listpart.subList(1, listpart.size());
		}
		Long surplusTime = null;
		if(comm!=null&&comm.getPublishTime()!=null) {
			Date publishTime = comm.getPublishTime();//发布时间
			//默认每个发布的拍卖品有三天72小时的时间换算为秒:72*60*60
			//计算截止到现在,还剩余的时间: 现在的时间戳-发布的时间戳/1000(秒)/
			Long totalTime = (long) (72*60*60) ;
			long time1 = publishTime.getTime();
			long time2 = new Date().getTime();
			surplusTime =totalTime-(time2-time1)/1000;
		}
		map.put("comm", comm);
		map.put("listall", subList2);
		map.put("listpart", subList);
		map.put("enrollCounts", enrollCounts);
		map.put("bidCounts", bidCounts);
		map.put("maxRecord", maxRecord);
		map.put("surplusTime",surplusTime);
		map.put("state",state);
		return map;
	}
	//7.保存卖家发布的商品信息conserveMsg
	@RequestMapping("/conserveMsg")
	@ResponseBody
	public String conserveMsg(Commodity commodity,HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("loginmsg");
		commodity.setSellerName(username);
		return commodityService.conserveMsg(commodity);
	}
	
	//pushThis 
	//8. 发布卖家发布的商品信息 pushThis

	@RequestMapping("/pushThis")
	@ResponseBody
	public String pushThis(Commodity commodity,HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("loginmsg");
		commodity.setSellerName(username);
		return commodityService.pushThis(commodity);
	}
	
	//initCommodity
	//9.寻找该卖家旗下的所有商品列表
	@RequestMapping("/initCommodity")
	@ResponseBody
	public List<Commodity> initCommodity(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("loginmsg");
		return commodityService.findCommodityByUname(username);
	}
	
	//pushComm
	//10. 发布卖家发布的商品信息 pushThis

	@RequestMapping("/pushComm")
	@ResponseBody
	public String pushComm(Integer pId) {
		return commodityService.pushComm(pId);
	}
	
	//initSuccessCommodity
	//11. 发布卖家发布的商品信息 pushThis

	@RequestMapping("/initSuccessCommodity")
	@ResponseBody
	public List<Commodity> initSuccessCommodity(HttpServletRequest req) {
		String username = (String) req.getSession().getAttribute("loginmsg");
		byte state = 3;//  3表示已结束已成交
		return commodityService.findCommodityBySellerAndState(username,state);
	}
	
	///paipai/Commodity/findAllCommiditys
	//12 . 管理员查看所有商品
	
	@RequestMapping("/findAllCommiditys")
	@ResponseBody
	public Map<String, Object> findAllCommiditys(Integer pageNum,Integer pageSize) {
		
		return commodityService.findAllCommodityS(pageNum,pageSize);
	}
	///paipai/Commodity/changeCommoState
	//13 . 管理员将正在拍卖的商品强制性下架
	@RequestMapping("/changeCommoState")
	@ResponseBody
	public String changeCommoState(Integer pId) {
		
		return commodityService.changeCommoStateByPid(pId);
	}
	
	//paipai/Commodity/findCommoByPushTime
	//14. 管理员通过发布时间筛选拍品
	@RequestMapping("/findCommoByPushTime")
	@ResponseBody
	public Map<String, Object> findCommoByPushTime(String startDay,String endDay) {
		
		return commodityService.findCommoByPushTimeServ(startDay,endDay);
	}
	
}