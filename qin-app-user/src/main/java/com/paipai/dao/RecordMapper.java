package com.paipai.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.paipai.entity.Record;
import com.paipai.model.OrderModel;

import tk.mybatis.mapper.common.Mapper;

public interface RecordMapper extends Mapper<Record> {
	/**
	 * 	查询商品记录,并且截取前三条,按照价格降序排列
	 * @param cId 商品id
	 * @return  记录的集合
	 */
	List<Record> selectRecordByPIdAndDesc(Integer cId);
	
	/**
	 * 查询报名的所有用户数量
	 * @param cId  商品id
	 * @return	用户数目
	 */
	Integer selectEnrollCounts(Integer cId);
	
	/**
	 * 	查询 cid 和 uid 的拍卖记录
	 * @param cId  商品id
	 * @param uId  用户id 
	 * @return  记录对象
	 */
	List<Record> selectRecordByUidAndCid(@Param("cId")Integer cId, @Param("uId")Integer uId);
	
	/**
	 * 	插入报名记录
	 * @param uId	用户id
	 * @param cId	商品id
	 * @param margin 保证金
	 * @return	受影响行数
	 */
	int insertEnrollRecord(@Param("uId")Integer uId, @Param("cId") Integer cId, @Param("margin")Double margin,@Param("ispai")Byte ispai);
	
	/**
	 * 	根据用户名查询用户的资金记录,并按照按照时间排序
	 * @param uId  用户id 
	 * @return	返回记录的list集合
	 */
	List<Record> selectRecordByUid(Integer uId);
	
	/**
	 * 	根据用户id 查询用户的拍卖记录 
	 * @param uId 用户id 
	 * @return  每条记录封装到map中, 多个map放在list集合里面
	 */
	List<Map<String, Object>> selectPaiDataByUid(Integer uId);
	
	/**
	 * 	通过商品id和ispai=0 查找交过保证金的id
	 * @param pId	商品id
	 * @return	返回用户id的集合
	 */
	List<Integer> selectUidsByPid(Integer pId);
	
	/**
	 * 	方法重载,插入最终拍卖成功的记录
	 * @param finalPaiUid 最终获胜者
	 * @param pId	商品id
	 * @param maxPrice 最大价格
	 * @param ispai	 该记录类型是4 成功拍出记录
	 * @param finalPai 是否是最终拍卖成功记录
	 * @return
	 */
	int insertEnrollRecord(Integer finalPaiUid, Integer pId, Double maxPrice, byte ispai, byte finalPai);
	
	/**
	 * 	通过用户名查找用户的订单记录
	 * @param uId  用户id 
	 * @return  返回list集合   每个map存放一条数据
	 */
	List<Map<String, Object>> selectOrderAndCommodityByUid(Integer uId);
	
	/**
	 * 	通过用户名和付款状态ispay查找用户的订单记录
	 * @param uId 用户id
	 * @param ispay  付款状态
	 * @return 返回  map集合 ,每个map对应一条数据记录
	 */
	List<Map<String, Object>> selectOrderAndCommodityByUidAndIsPay(@Param("uId")Integer uId,@Param("ispay") byte ispay);
	
	/**
	 * 
	 * 	通过用户名和商品id去查找该订单的状态
	 * @param uId 用户id	
	 * @param pId	商品id
	 * @return	返回订单状态ispay状态
	 */
	Byte selectIspayByUidAndPid(@Param("uId")Integer uId,@Param("pId") Integer pId);
	
	/**
	 * 	通过用户名和商品id 去修改订单的状态
	 * @param uId	用户id
	 * @param pId	商品id
	 * @param newIspay 	要修改的状态
	 * @return	受影响行数
	 */
	int updateIspayByUidAndPid(@Param("uId")Integer uId, @Param("pId")Integer pId, @Param("newIspay")byte newIspay);
	/**
	 * 	通订单 id 去修改订单的状态  包括支付的时间记录 
	 * @param orderId 订单id
	 * @param newIspay 	要修改的状态
	 * @return	受影响行数
	 */
	int updateIspayAndPaytimeByUidAndPid( @Param("orderId")Integer orderId, @Param("newIspay")byte newIspay);
	
	/**
	 * 	通过订单模型更新最终的订单数据
	 * @param orderModel 订单模型
	 * @return 受影响行数
	 */
	int updateOrderFinalMsg(OrderModel orderModel);
	
	
}