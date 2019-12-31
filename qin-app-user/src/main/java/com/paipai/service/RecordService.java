package com.paipai.service;

import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.paipai.entity.Commodity;
import com.paipai.entity.Record;
import com.paipai.entity.User;
import com.paipai.model.OrderModel;
import com.paipai.utils.MikeUtil;
import com.paipai.utils.UserUtil;

import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.entity.Example.Criteria;

import com.paipai.dao.CommodityMapper;
import com.paipai.dao.RecordMapper;
import com.paipai.dao.UserMapper;

@Transactional
@Service
public class RecordService {
	@Autowired
	private RecordMapper recordMapper;
	@Autowired
	private UserMapper userMapper;
	@Autowired
	private RecordService recordService;
	@Autowired
	private CommodityService commodityService;
	@Autowired
	private CommodityMapper commoditymapper;

	public RecordMapper getRecordMapper() {
		return recordMapper;
	}

	// 查询商品记录
	public List<Record> findRecordByPId(Integer cId) {
		Example exa = new Example(Record.class);
		Criteria cri = exa.createCriteria();
		cri.andCondition("c_id=", cId);
		cri.andCondition("ispai=", 1);
		List<Record> list = recordMapper.selectByExample(exa);
		if (list == null)
			return null;
		Collections.sort(list, new Comparator<Record>() {
			@Override
			public int compare(Record o1, Record o2) {
				return o2.getPaiPrice().compareTo(o1.getPaiPrice());
			}
		});
		return list;
	}

	// 查询商品记录,并且截取前三条,按照价格降序排列
	public List<Record> findRecordByPIdAndDesc(Integer cId) {

		return recordMapper.selectRecordByPIdAndDesc(cId);
	}

	// 查询报名的所有用户数量
	public Integer findEnrollCounts(Integer cId) {

		return recordMapper.selectEnrollCounts(cId);
	}

	// 查询所有的叫价次数
	public Integer findBidCounts(Integer cId) {
		Example exa = new Example(Record.class);
		Criteria cri = exa.createCriteria();
		cri.andCondition("c_id=", cId);
		cri.andCondition("ispai=", 1);
		return recordMapper.selectCountByExample(exa);
	}

	// 判断用户是否报名过该商品的竞价
	public boolean findRecordByUidAndCid(Integer cId, Integer uId) {
		List<Record> list = recordMapper.selectRecordByUidAndCid(cId, uId);
		if (list == null || list.isEmpty())
			return false;
		return true;
	}

	// 如果用户提交报名,1,检查用户余额是否足够;不够返回"账户余额不足,请充值...",从账户余额中扣款,并插入record表
	public String userEnroll(Integer cId, Integer uId, Double margin) {
		Double balance = userMapper.selectBalanceByUid(uId);
		boolean isEnroll = recordService.findRecordByUidAndCid(cId, uId);
		if (isEnroll)
			return "您已经报名了!";
		if (balance == null || balance < margin)
			return "账户余额不足,请充值...";
		// 扣款
		int row1 = userMapper.updateBalanceByUid(uId, balance - margin);
		if (row1 > 0) {
			// 插入报名记录
			byte ispai = 0;
			int row2 = recordMapper.insertEnrollRecord(uId, cId, margin, ispai);
			if (row2 > 0) {
				return "success";
			}
		} else {
			return "支付失败 !";
		}
		return "支付失败 !";
	}

	// 用户点击我要竞拍:为避免两人出同一个价格,此处加锁 1.判断商品的状态,一定要果为否,返回为正在拍的状态[1] 如"拍卖已结束!";
	// 2.判断此价格是否大于当前最高价,只有大于才能拍 ,否则返回"您的出价低于当前拍卖价格最高价格!";//3.插入拍卖记录,插入成功 提示竞拍成功 最后
	// 刷新页面
	public String biddingCommodity(Integer pId, Integer uId, Double biddingPrice) {
		Integer state = commodityService.findStateBy(pId);
		if (state != 1)
			return "拍卖已结束!";
		synchronized (RecordService.class) {// -----------------------------------------加锁防止两人出统一价格
			List<Record> listall = recordService.findRecordByPId(pId);
			if (listall != null && listall.size() > 0) {
				Record maxRecord = listall.get(0);// 出价 最高 的 记录
				if (biddingPrice <= maxRecord.getPaiPrice()) {
					return "您的出价低于当前拍卖价格最高价格!";
				}
			}
			if (commodityService.findStateBy(pId) != 1)
				return "拍卖已结束!";
			// 3.修改当前商品的最新价格
			int row1 = commoditymapper.updateLastpriceByPid(pId, biddingPrice);
			// 4.插入拍卖纪录
			if (row1 > 0) {
				byte ispai = 1;
				int row2 = recordMapper.insertEnrollRecord(uId, pId, biddingPrice, ispai);
				if (row2 > 0) {
					return "ok";
				} else {
					throw new RuntimeException();
				}
			}
		}
		return "出价失败,请重试!";
	}

	// 3. 寻找并封装我的钱包中的数据 : 账户 余额 和 账号的金额变动
	public Map<String, Object> findWalletData(Integer uId) {
		HashMap<String, Object> map = new HashMap<String, Object>();
		Double balance = userMapper.selectBalanceByUid(uId);
		List<Record> list = recordMapper.selectRecordByUid(uId);
		map.put("balance", balance);
		map.put("list", list);
		return map;
	}

	// 4.通过uid获取两个数据,一个是用户的拍卖记录 ispai为1 以及对应的商品信息
	public List<Map<String, Object>> findPaiDataByUid(Integer uId) {
		// 多表联查
		return recordMapper.selectPaiDataByUid(uId);
	}

	// 5.提交订单的核心逻辑:
	// (1) 判断,如果没有人拍,此次拍卖作废,所有人的资金都退回,不生成订单;修改商品状态为; 3
	public void submitOrderByPid(Integer pId) {
		List<Record> list = recordMapper.selectRecordByPIdAndDesc(pId);
		Double margin = commoditymapper.selectMarginByPid(pId);

		List<Integer> listUids = recordMapper.selectUidsByPid(pId);// 查询交了保证金的用户
		// 1.情况一:无人竞拍!
		// 退款流程:1.查询交了保证金的用户;2.更新账户余额 ;3.插入退款数据
		if (list == null || list.isEmpty()) {

			System.out.println(pId + "商品无人竞拍,此次竞拍结束!");
			byte state1 = 2;
			int row = commoditymapper.updateStateByPid(pId, state1);
			System.out.println(row > 0 ? "状态修改成功" : "状态修改失败!");
			int rows = 0;
			byte ispai = 3;
			for (Integer uid : listUids) {
				// 给所有报名用户退款
				int row2 = userMapper.updateBalanceByUidAndMargin(uid, margin);// ;2.更新账户余额
				if (row2 > 0) {
					System.out.println(uid + "用户退款成功!");
					// 插入退款记录的数据
					int row3 = recordMapper.insertEnrollRecord(uid, pId, margin, ispai);// 3.插入退款数据
					if (row3 > 0)
						System.out.println(uid + "用户" + pId + "商品退款数据插入成功!");
					rows++;
				}
			}
			if (rows == listUids.size())
				System.out.println("全部退款成功!");
			return;
		}
		// 2.情况2: 有人竞拍时
		System.out.println(pId + "此次竞拍结束!");
		byte state2 = 3;
		int row = commoditymapper.updateStateByPid(pId, state2);
		System.out.println(row > 0 ? "状态修改成功" : "状态修改失败!");
		int rows = 0;

		// 1.产生竞拍成功用户
		Integer finalPaiUid = list.get(0).getuId();
		System.out.println(finalPaiUid);
		// 获得最高价格
		Double maxPrice = list.get(0).getPaiPrice();
		// 2.生成订单
		byte ispai = 4;
		int row3 = recordMapper.insertEnrollRecord(finalPaiUid, pId, maxPrice, ispai);
		if (row3 > 0)
			System.out.println(finalPaiUid + "用户成功拍到商品");
		for (Integer uid : listUids) {
			// 除了finalPaiUid
			System.out.println("uid" + uid + "finalPaiUid" + finalPaiUid);
			if (!uid.equals(finalPaiUid)) {
				// 给其他用户退款
				System.out.println(!uid.equals(finalPaiUid));
				int row2 = userMapper.updateBalanceByUidAndMargin(uid, margin);// ;2.更新账户余额
				if (row2 > 0) {
					System.out.println(uid + "用户退款成功!");
					// 插入退款记录的数据
					byte ispai2 = 3;
					int row4 = recordMapper.insertEnrollRecord(uid, pId, margin, ispai2);// 3.插入退款数据
					if (row4 > 0)
						System.out.println(uid + "用户" + pId + "商品退款数据插入成功!");
					rows++;
				}
			}

		}
		if (rows == listUids.size() - 1)
			System.out.println("全部退款成功!");
	}

	// 通过用户id 查询订单和商品的记录
	public List<Map<String, Object>> findOrderAndCommodityByUid(Integer uId) {

		return recordMapper.selectOrderAndCommodityByUid(uId);
	}

	// 通过用户id 查找未付款的记录
	public List<Map<String, Object>> findOrderAndCommodityByUidAndIsPay(Integer uId, byte ispay) {
		return recordMapper.selectOrderAndCommodityByUidAndIsPay(uId, ispay);
	}

	// 通过商品id 和用户id 查询并且修改订单状态的ispay=2
	public String closeOrderByUidAndIspay(Integer uId, Integer pId) {

		Byte ispay = recordMapper.selectIspayByUidAndPid(uId, pId);
		if (ispay == 1)
			return "订单已经支付!无法取消!";
		if (ispay == 2)
			return "订单已经取消,无需再次取消!";
		byte newIspay = 2;
		// 修改订单的ispay状态
		int row = recordMapper.updateIspayByUidAndPid(uId, pId, newIspay);
		if (row > 0)
			return "ok";
		return "订单取消失败!";
	}

	// 用户支付的业务逻辑, 先判断ispay的状态,当为0的时候才继续支付,修改订单状态为1
	public String alipayByPidAndUid(Integer pId, Integer uId, Integer orderId, Double orderCash) {
		Byte ispay = recordMapper.selectIspayByUidAndPid(uId, pId);
		if (ispay == 1)
			return "订单已经支付!请勿重复提交!";
		if (ispay == 2)
			return "订单已经过期,无法支付,请联系客服!";
		Double blance = userMapper.selectBalanceByUid(uId);
		if (blance < orderCash)
			return "账户余额不足,请先充值! ";
		Commodity commo = commoditymapper.selectByPrimaryKey(pId);
		Double lastPrice = 0D;
		if (commo != null) {
			lastPrice = commo.getLastPrice();
		}
		// 更新订单信息
		byte newIspay = 1;
		int row = recordMapper.updateIspayAndPaytimeByUidAndPid(uId, newIspay);
		// 把最的80%打入对应账户
		if (row > 0) {
			Commodity commodity = commoditymapper.selectByPrimaryKey(pId);
			String sellerName = "";
			if (commodity != null) {
				sellerName = commodity.getSellerName();
			}
			int row1 = userMapper.updateBalanceByUnameAndMargin(sellerName, lastPrice * 0.8);
			if (row1 > 0) {
				System.out.println("钱已经打入拍卖人账户...");
				return "ok";
			}
		}

		return "订单失败!";
	}

	// 查找订单表中所需要的信息,1:用户的基本信息 ,商品的基本信息
	public Map<String, Object> findOrderMsgByOrderIdUid(Integer orderId, Integer uId, Integer pId) {
		HashMap<String, Object> hashMap = new HashMap<String, Object>();
		// 1.通过用户名查找用户信息
		User user = userMapper.selectByPrimaryKey(uId);
		// 2.通过pid查找商品信息
		Commodity commodity = commoditymapper.selectByPrimaryKey(pId);
		// 封装
		hashMap.put("user", user);
		hashMap.put("commodity", commodity);
		hashMap.put("orderId", orderId);
		return hashMap;
	}
	//用户支付的业务逻辑, 先判断ispay的状态,当为0的时候才继续支付,修改订单状态为1
	public String alipayByPidAndUid(OrderModel orderModel) {
		if(orderModel==null)
			return "传入信息为空" ;
		if(!MikeUtil.notNullOrEmpty(orderModel.getUsername()))
			return "收货人姓名为空! ";
		if(!UserUtil.checkMobile(orderModel.getMobile()))
			return "手机号码格式不正确" ;
		if(!MikeUtil.notNullOrEmpty(orderModel.getAddr()))
			return "地信息为空,请输入地址 .. " ;
		Record record = recordMapper.selectByPrimaryKey(orderModel.getOrderId());
		if(record==null)
			return "订单不存在,请联系客服咨询 !" ;
		byte ispay = record.getIspay();
		if(ispay==1 )
			return "您已经支付过订单了 ! " ;
		if(ispay==2 )
			return "订单已失效,有疑问请联系客服MM  ! " ;
		double balance = userMapper.selectBalanceByUid(orderModel.getuId());
 		if(balance<orderModel.getOrderCash())
 			return "账户余额不足,请先充值 ! " ;
 		//开始扣款
 		int row0 = userMapper.updateBalanceByUid(orderModel.getuId(), balance-orderModel.getOrderCash());
 		if(row0>0) {
 			System.out.println("拍卖人扣款成功 ....");
 			Commodity commodity = commoditymapper.selectByPrimaryKey(orderModel.getpId());
			String sellerName = "";
			if (commodity != null) {
				sellerName = commodity.getSellerName();
			}
 			int row2= userMapper.updateBalanceByUnameAndMargin(sellerName, orderModel.getLastPrice() * 0.8);
			if (row2 > 0) {
				System.out.println("80%钱已经打入拍卖人账户...");
				// 更新订单信息
		 		int row1 = recordMapper.updateOrderFinalMsg(orderModel);
		 		if(row1>0) {//更新订单成功
		 			System.out.println("最终的 订单生成成功!");
		 			return "ok";
		 		}else {
		 			return "支付失败,请重试 !";
		 		}
				
			}else {
				return "支付失败,请重试!";
			}
 		}
 
		return "支付失败,请重试!";
	}
	
	//用户查看订单详情的时候,查询订单数据
	public Record findOrderMsgByOrderId(Integer orderId) {
		
		return recordMapper.selectByPrimaryKey(orderId);
	}
}