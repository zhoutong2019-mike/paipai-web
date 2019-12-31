package com.paipai.model;

import java.io.Serializable;

public class OrderModel implements Serializable {
	private static final long serialVersionUID = 1L;
	private Integer pId;
	private Integer orderId;
	private Double orderCash; // 订单总额
	private Integer uId;
	private String addr;
	private String mobile;
	private Double lastPrice;
	private String username;
	private String remarks;
	

	public String getRemarks() {
		return remarks;
	}

	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}

	public Integer getpId() {
		return pId;
	}

	public void setpId(Integer pId) {
		this.pId = pId;
	}

	public Integer getOrderId() {
		return orderId;
	}

	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}

	public Double getOrderCash() {
		return orderCash;
	}

	public void setOrderCash(Double orderCash) {
		this.orderCash = orderCash;
	}

	public Integer getuId() {
		return uId;
	}

	public void setuId(Integer uId) {
		this.uId = uId;
	}

	public String getAddr() {
		return addr;
	}

	public void setAddr(String addr) {
		this.addr = addr;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public Double getLastPrice() {
		return lastPrice;
	}

	public void setLastPrice(Double lastPrice) {
		this.lastPrice = lastPrice;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "OrderModel [pId=" + pId + ", orderId=" + orderId + ", orderCash=" + orderCash + ", uId=" + uId
				+ ", addr=" + addr + ", mobile=" + mobile + ", lastPrice=" + lastPrice + ", username=" + username
				+ ", remarks=" + remarks + "]";
	}

}
