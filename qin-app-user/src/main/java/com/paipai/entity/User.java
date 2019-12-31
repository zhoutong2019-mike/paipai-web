package com.paipai.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class User implements Serializable {
	@Id
	@GeneratedValue(generator = "JDBC")
	private Integer id;

	private String username;

	private String mobile;

	private String password;

	private String email;

	private String addr;

	/**
	 * 注册时间
	 */
	@Column(name = "create_time")
	private Date createTime;

	/**
	 * 账户余额
	 */
	private Double balance;

	/**
	 * 状态 状态1有效账户 状态0无效账户
	 */
	private Byte state;

	private static final long serialVersionUID = 1L;

	/**
	 * @return id
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * @param id
	 */
	public void setId(Integer id) {
		this.id = id;
	}

	/**
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * @return mobile
	 */
	public String getMobile() {
		return mobile;
	}

	/**
	 * @param mobile
	 */
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	/**
	 * @return password
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/**
	 * @return email
	 */
	public String getEmail() {
		return email;
	}

	/**
	 * @param email
	 */
	public void setEmail(String email) {
		this.email = email;
	}

	/**
	 * @return addr
	 */
	public String getAddr() {
		return addr;
	}

	/**
	 * @param addr
	 */
	public void setAddr(String addr) {
		this.addr = addr;
	}

	/**
	 * 获取注册时间
	 *
	 * @return create_time - 注册时间
	 */
	public Date getCreateTime() {
		return createTime;
	}

	/**
	 * 设置注册时间
	 *
	 * @param createTime 注册时间
	 */
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	/**
	 * 获取账户余额
	 *
	 * @return balance - 账户余额
	 */
	public Double getBalance() {
		return balance;
	}

	/**
	 * 设置账户余额
	 *
	 * @param balance 账户余额
	 */
	public void setBalance(Double balance) {
		this.balance = balance;
	}

	/**
	 * 获取状态 状态1有效账户 状态0无效账户
	 *
	 * @return state - 状态 状态1有效账户 状态0无效账户
	 */
	public Byte getState() {
		return state;
	}

	/**
	 * 设置状态 状态1有效账户 状态0无效账户
	 *
	 * @param state 状态 状态1有效账户 状态0无效账户
	 */
	public void setState(Byte state) {
		this.state = state;
	}

	@Override
	public String toString() {
		return "User [id=" + id + ", username=" + username + ", mobile=" + mobile + ", password=" + password
				+ ", email=" + email + ", addr=" + addr + ", createTime=" + createTime + ", balance=" + balance
				+ ", state=" + state + "]";
	}

}