package com.paipai.service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import com.paipai.entity.User;

import com.paipai.dao.RecordMapper;
import com.paipai.dao.UserMapper;

@Transactional
@Service
public class UserService {
	@Autowired
	private UserMapper userMapper;

	@Autowired
	private RecordMapper recordMapper;

	public UserMapper getUserMapper() {
		return userMapper;
	}

	// 1.判断用户是否存在
	public User userIsExist(String username) {
		return userMapper.selectUserByName(username);
	}

	// 2.判断用户密码是否正确
	public boolean pwIsRight(String username, String password) {
		User user = userMapper.selectUserByName(username);
		if (user != null && user.getPassword().equals(password))
			return true;
		return false;
	}

	// 2.插入用户
	public void insertUser(User user) {
		user.setCreateTime(new Date());
		int row = userMapper.insert(user);
		if (row < 1)
			throw new RuntimeException();
	}

	// 3.查找用户信息
	public User findUserByName(String username) {
		return userMapper.selectUserByName(username);
	}

	// 4.更新用户信息
	public boolean updateUserByUser(User user) {
		int row = userMapper.updateByUSER(user);
		return row > 0;
	}

	// 5. 充值流程:(1) 更改用户余额 (2) 插入充值记录
	public String rechargeByUid(Integer uId, Double rechargemMoney) {
		Double balance = userMapper.selectBalanceByUid(uId);
		int row = userMapper.updateBalanceByUid(uId, balance + rechargemMoney);
		if (row > 0) {
			byte ispai = 2;// 2代表充值记录
			int row2 = recordMapper.insertEnrollRecord(uId, null, rechargemMoney, ispai);
			if (row2 > 0)
				return "yes";
		}
		return "充值失败,请重试!";
	}

	public List<User> findUsers() {

		return userMapper.selectAll();
	}

	// 通过用户id更改用户状态
	public String updateUserStateS(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null)
			return "该用户不存在 ! ";
		Byte state = user.getState();
		if (state != 1)// 只有状态为1的时候才可以修改状态
			return "该用户已经处于冻结状态 ! ";
		int row = userMapper.updateUserStateById(id);
		if (row > 0)
			return "ok";

		return "冻结失败! ";
	}

	// 通过用户id更改用户状态 1-->0
	public String updateUserStateToOneS(Integer id) {
		User user = userMapper.selectByPrimaryKey(id);
		if (user == null)
			return "该用户不存在 ! ";
		Byte state = user.getState();
		if (state != 0)// 只有状态为0的时候才可以解锁
			return "该用户已经处于解锁状态 ! ";
		int row = userMapper.updateUserStateToOneById(id);
		if (row > 0)
			return "ok";

		return "解锁失败! ";

	}

	public Map<String, Object> findManyUserByName(String username) {
		List<User> list = userMapper.selectUserByNameAndLike(username);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		return map;
	}

	public Map<String, Object> findManyUserByTime(String startDay, String endDay) {
		List<User> list = userMapper.selectUserByCreateTime(startDay,endDay);
		HashMap<String, Object> map = new HashMap<String, Object>();
		map.put("list", list);
		System.out.println(map);
		return map;
	}

}