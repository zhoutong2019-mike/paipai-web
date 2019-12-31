package com.paipai.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Record implements Serializable {
    @Id
    @GeneratedValue(generator = "JDBC")
    private Integer id;

    /**
     * 商品id
     */
    @Column(name = "c_id")
    private Integer cId;

    /**
     * 用户id
     */
    @Column(name = "u_id")
    private Integer uId;

    @Column(name = "pai_time")
    private Date paiTime;

    /**
     * 拍卖的价格
     */
    @Column(name = "pai_price")
    private Double paiPrice;

    /**
     * 0表示报名  1表示出价 2表示充值 3表示退款 4表示最终拍出记录
     */
    private Byte ispai;

    /**
     * 0表示已经拍得未付款  1表示已付款 2表示已取消
     */
    private Byte ispay;

    @Column(name = "pay_time")
    private Date payTime;

    /**
     * 支付金额
     */
    @Column(name = "pay_cash")
    private Double payCash;

    @Column(name = "mail_addr")
    private String mailAddr;

    @Column(name = "mail_name")
    private String mailName;

    @Column(name = "mail_mobile")
    private String mailMobile;

    @Column(name = "mail_remarks")
    private String mailRemarks;

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
     * 获取商品id
     *
     * @return c_id - 商品id
     */
    public Integer getcId() {
        return cId;
    }

    /**
     * 设置商品id
     *
     * @param cId 商品id
     */
    public void setcId(Integer cId) {
        this.cId = cId;
    }

    /**
     * 获取用户id
     *
     * @return u_id - 用户id
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * 设置用户id
     *
     * @param uId 用户id
     */
    public void setuId(Integer uId) {
        this.uId = uId;
    }

    /**
     * @return pai_time
     */
    public Date getPaiTime() {
        return paiTime;
    }

    /**
     * @param paiTime
     */
    public void setPaiTime(Date paiTime) {
        this.paiTime = paiTime;
    }

    /**
     * 获取拍卖的价格
     *
     * @return pai_price - 拍卖的价格
     */
    public Double getPaiPrice() {
        return paiPrice;
    }

    /**
     * 设置拍卖的价格
     *
     * @param paiPrice 拍卖的价格
     */
    public void setPaiPrice(Double paiPrice) {
        this.paiPrice = paiPrice;
    }

    /**
     * 获取0表示报名  1表示出价 2表示充值 3表示退款 4表示最终拍出记录
     *
     * @return ispai - 0表示报名  1表示出价 2表示充值 3表示退款 4表示最终拍出记录
     */
    public Byte getIspai() {
        return ispai;
    }

    /**
     * 设置0表示报名  1表示出价 2表示充值 3表示退款 4表示最终拍出记录
     *
     * @param ispai 0表示报名  1表示出价 2表示充值 3表示退款 4表示最终拍出记录
     */
    public void setIspai(Byte ispai) {
        this.ispai = ispai;
    }

    /**
     * 获取0表示已经拍得未付款  1表示已付款 2表示已取消
     *
     * @return ispay - 0表示已经拍得未付款  1表示已付款 2表示已取消
     */
    public Byte getIspay() {
        return ispay;
    }

    /**
     * 设置0表示已经拍得未付款  1表示已付款 2表示已取消
     *
     * @param ispay 0表示已经拍得未付款  1表示已付款 2表示已取消
     */
    public void setIspay(Byte ispay) {
        this.ispay = ispay;
    }

    /**
     * @return pay_time
     */
    public Date getPayTime() {
        return payTime;
    }

    /**
     * @param payTime
     */
    public void setPayTime(Date payTime) {
        this.payTime = payTime;
    }

    /**
     * 获取支付金额
     *
     * @return pay_cash - 支付金额
     */
    public Double getPayCash() {
        return payCash;
    }

    /**
     * 设置支付金额
     *
     * @param payCash 支付金额
     */
    public void setPayCash(Double payCash) {
        this.payCash = payCash;
    }

    /**
     * @return mail_addr
     */
    public String getMailAddr() {
        return mailAddr;
    }

    /**
     * @param mailAddr
     */
    public void setMailAddr(String mailAddr) {
        this.mailAddr = mailAddr;
    }

    /**
     * @return mail_name
     */
    public String getMailName() {
        return mailName;
    }

    /**
     * @param mailName
     */
    public void setMailName(String mailName) {
        this.mailName = mailName;
    }

    /**
     * @return mail_mobile
     */
    public String getMailMobile() {
        return mailMobile;
    }

    /**
     * @param mailMobile
     */
    public void setMailMobile(String mailMobile) {
        this.mailMobile = mailMobile;
    }

    /**
     * @return mail_remarks
     */
    public String getMailRemarks() {
        return mailRemarks;
    }

    /**
     * @param mailRemarks
     */
    public void setMailRemarks(String mailRemarks) {
        this.mailRemarks = mailRemarks;
    }
}