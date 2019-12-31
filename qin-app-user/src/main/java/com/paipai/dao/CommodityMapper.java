package com.paipai.dao;

import java.util.List;
import org.apache.ibatis.annotations.Param;
import com.paipai.entity.Commodity;
import tk.mybatis.mapper.common.Mapper;

public interface CommodityMapper extends Mapper<Commodity> {
	
	/**
	 *   	通过关键字查找商品的方法
	 * @param keyword  商品的关键字
	 * @return  返回商品集合
	 */

	List<Commodity> selectByKw(String keyword);
			
	/**
	 * 	通过关键字查找商品的数量
	 * @param keyword   商品关键字
	 * @return	返回商品数目
	 */
	int selectCountByKw(String keyword);
	
	/**
	 * 	通过商品id 查询商品的状态
	 * @param cId 商品id 
	 * @return	返回商品的状态  0表示即将拍卖 1表示正在拍卖  2表示拍卖结束
	 */
	Integer selectStateByCId(Integer cId);
	
	/**
	 * 	通过商品id更新商品的最新价格
	 * @param pId	商品id
	 * @param biddingPrice  最新出价
	 * @return	受影响行数
	 */
	int updateLastpriceByPid(@Param("pId")Integer pId,@Param("biddingPrice") Double biddingPrice);
		
	/**
	 * 	通过商品id 更新商品的状态
	 * @param pId
	 * @return
	 */
	int updateStateByPid(@Param("pId")Integer pId,@Param("state")Byte state);
	
	/**
	 * 	通过商品id 查询商品的保证金
	 * @param pId 商品id
	 * @return	保证金金额
	 */
	Double selectMarginByPid(Integer pId);
	
	
	/**
	 * 	T通过商品id更新商品的状态 和发布时间now()
	 * @param pId
	 * @return
	 */
	int updateCommStateAndPushtime(Integer pId);
	
	/**
	 * 	通过ids 查找
	 * @param string  id的字符串
	 * @return  商品集合
	 */
	List<Commodity> selectByIds(@Param("ids")String ids);
	/**
	 * 	通过ids 查找数量
	 * @param keyword
	 * @return
	 */
	int selectCountByIds(@Param("ids")String ids);
	
	/**
	 * 	通过发布时间筛选用户 
	 * @param startDay  开始时间
	 * @param endDay 结束时间
	 * @return 拍品集合
	 */
	List<Commodity> selectCommoByPubilshTime(@Param("startDay")String startDay,@Param("endDay") String endDay);
	
	
}