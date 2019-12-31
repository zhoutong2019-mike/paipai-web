package com.paipai.dao;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.paipai.entity.User;
import tk.mybatis.mapper.common.Mapper;

public interface UserMapper extends Mapper<User> {

	/**
	 * 1. 通过用户名查询用户
	 * 
	 * @param username 用户名
	 * @return 用户对象
	 */
	User selectUserByName(String username);

	/**
	 * 2.通过用户更新用户信息
	 * 
	 * @param user 用户
	 * @return 受影响行数
	 */
	int updateByUSER(User user);

	/**
	 * 3.通过id 查询用户的账户余额
	 * 
	 * @param uId
	 * @return
	 */
	Double selectBalanceByUid(Integer uId);

	/**
	 * 4.修改账户余额
	 * 
	 * @param uId 用户名
	 * @param d
	 * @return 受影响行数
	 */
	int updateBalanceByUid(@Param("uId") Integer uId, @Param("newBalance") double newBalance);

	/**
	 * 5. 通过用户id和保证金来更新用户的账户余额
	 * 
	 * @param uid    用户id
	 * @param margin 保证金
	 * @return 受影响行数row
	 */
	int updateBalanceByUidAndMargin(@Param("uid") Integer uid, @Param("margin") Double margin);

	/**
	 * 通过用户名和增加的金额为用户转入资金
	 * 
	 * @param sellerName 卖家
	 * @param addCash    新增资金
	 * @return 受影响行数
	 */
	int updateBalanceByUnameAndMargin(@Param("sellerName") String sellerName, @Param("addCash") Double addCash);

	/**
	 * ``通过用户id更新用户的状态 1-> 0
	 * 
	 * @param id 用户id
	 * @return 返回受影响行数
	 */
	int updateUserStateById(Integer id);

	/**
	 * ``通过用户id更新用户的状态 0-->1
	 * 
	 * @param id 用户id
	 * @return 返回受影响行数
	 */
	int updateUserStateToOneById(Integer id);

	/**
	 * 通过用户名查询用户的集合
	 * 
	 * @param username 用户名
	 * @return 集合
	 */
	List<User> selectUserByNameAndLike(String username);

	/**
	 * 通过注册时间段查询用户的集合
	 * 
	 * @param startDay 注册起始时间
	 * @param endDay   注册结束时间
	 * @return 返回集合
	 */
	List<User> selectUserByCreateTime(@Param("startDay") String startDay, @Param("endDay") String endDay);

}