package com.paipai.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.paipai.entity.Record;
import com.paipai.model.OrderModel;
import com.paipai.service.RecordService;
import com.paipai.utils.BaseController;

@Controller
@RequestMapping("/paipai/Record")
public class RecordController extends BaseController {
	@Autowired
	private RecordService recordService;

	// 用户报名成功,从账户余额中扣款,并插入record表
	@RequestMapping("/insertEnrollMsg")
	@ResponseBody
	public String insertEnrollMsg(Integer pId, Double margin, HttpServletRequest req) {
		// 调用 给用户报名的业务逻辑方法
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		String result = recordService.userEnroll(pId, uId, margin);
		return result;
	}

	// biddingIt
	// 用户点击我要竞拍,将价格传入后端
	@RequestMapping("/biddingIt")
	@ResponseBody
	public String biddingIt(Integer pId, Double biddingPrice, Integer uId) {
		// 调用service层的竞拍流程
		return recordService.biddingCommodity(pId, uId, biddingPrice);
	}

	// showWalletData
	// 钱包页面刷新时候,获取钱包数据:余额和钱包变动记录
	@RequestMapping("/showWalletData")
	@ResponseBody
	public Map<String, Object> showWalletData(HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		return recordService.findWalletData(uId);
	}
	// paipai/Record/showPaiData

	@RequestMapping("/showPaiData")
	@ResponseBody
	public List<Map<String, Object>> showPaiData(HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 通过uid获取两个数据,一个是用户的拍卖记录 ispai为1 以及对应的商品信息
		List<Map<String, Object>> list = recordService.findPaiDataByUid(uId);
		return list;
	}

	@RequestMapping("/submitOrder")
	public void submitOrder(Integer pId) {
		// 此处调用提交订单的业务逻辑
		recordService.submitOrderByPid(pId);
	}
	//寻找当前用户所有订单
	@RequestMapping("/initOrder")
	@ResponseBody
	public List<Map<String, Object>> initOrder(HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 调用通过用户id 查询该用户的订单记录 和商品详情
		List<Map<String, Object>> list = recordService.findOrderAndCommodityByUid(uId);
		return list;
	}
	//寻找用户未付款订单ispay = 0
	@RequestMapping("/initnoPayOrder")
	@ResponseBody
	public List<Map<String, Object>> initnoPayOrder(HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 调用通过用户id 查询该用户的订单记录 和商品详情
		byte ispay = 0 ;//0表示未付款的记录
		List<Map<String, Object>> list = recordService.findOrderAndCommodityByUidAndIsPay(uId,ispay);
		return list;
	}
	
	//initSuccessOrder
	//用户成功付款的订单ispay = 1
	@RequestMapping("/initSuccessOrder")
	@ResponseBody
	public List<Map<String, Object>> initSuccessOrder(HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 调用通过用户id 查询该用户的订单记录 和商品详情
		byte ispay = 1 ;//1表示为已付款的记录
		List<Map<String, Object>> list = recordService.findOrderAndCommodityByUidAndIsPay(uId,ispay);
		return list;
	}
	
	//initCancelOrder
	//用户取消的订单 ispay = 2
	@RequestMapping("/initCancelOrder")
	@ResponseBody
	public List<Map<String, Object>> initCancelOrder(HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 调用通过用户id 查询该用户的订单记录 和商品详情
		byte ispay = 2 ;//1表示为已付款的记录
		List<Map<String, Object>> list = recordService.findOrderAndCommodityByUidAndIsPay(uId,ispay);
		return list;
	}
	
	///paipai/Record
	@RequestMapping("/closeOrder")
	@ResponseBody
	public String closeOrder(Integer pId ,HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 调用业务逻辑:查询并修改订单的状态
		return recordService.closeOrderByUidAndIspay(uId,pId);
	}
	
	//alipay
	
	@RequestMapping("/alipay")
	@ResponseBody
	public String alipay(OrderModel orderModel,HttpServletRequest req) {
		// 调用业务逻辑:查询并修改订单的状态
		
		return recordService.alipayByPidAndUid(orderModel);
	}
	
	@RequestMapping("/showOrderMsg")
	@ResponseBody
	public  Map<String,Object> showOrderMsg(Integer orderId ,Integer pId,HttpServletRequest req) {
		Integer uId = (Integer) req.getSession().getAttribute("loginid");
		// 查询并封装数据   需要用户的信息 + 商品的信息  
		return recordService.findOrderMsgByOrderIdUid(orderId,uId,pId);
	}
	//showOrderMsgTwo
	//查询订单信息
	@RequestMapping("/showOrderMsgTwo")
	@ResponseBody
	public  Record showOrderMsgTwo(Integer orderId ,HttpServletRequest req) {
		return recordService.findOrderMsgByOrderId(orderId);
	}
	//展示竞拍数据
	//showBiddingMsg
	@RequestMapping("/showBiddingMsg")
	@ResponseBody
	public  List<Record> showBiddingMsg(Integer pId ,HttpServletRequest req) {
		// 查询出价记录的所有记录
		List<Record> listall = recordService.findRecordByPId(pId);
		List<Record> subList = null;
		boolean flag = listall!=null&&listall.size()>=10;
		if(flag) {
			subList = listall.subList(0, 10);
		}
		return flag?subList:listall;//当查询数据大于10条的时候,展示截取的前十条, 反之展示原集合
	}
	///paipai/Record/findBiddingRecord
	//管理员查看单品的竞拍记录
	@RequestMapping("/findBiddingRecord")
	@ResponseBody
	public  Map<String,Object> findBiddingRecord(Integer pId ,HttpServletRequest req) {
		// 查询出价记录的所有记录
		List<Record> listall = recordService.findRecordByPId(pId);
		Map<String,Object> map = new HashMap<String,Object>(); 
		map.put("list", listall);
		System.out.println(map);
		return map;
	}

	
}