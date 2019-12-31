package com.paipai.entity;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

public class Commodity implements Serializable {
    @Id
    @Column(name = "p_id")
    private Integer pId;

    @Column(name = "p_name")
    private String pName;

    /**
     * 出价次数
     */
    @Column(name = "access_num")
    private Integer accessNum;

    private String img;

    /**
     * 1表示奢侈名品 2表示艺术精品 3 紫砂陶瓷 4和田玉
     */
    private Integer type;

    @Column(name = "first_price")
    private Double firstPrice;

    @Column(name = "last_price")
    private Double lastPrice;

    private Double margin;

    @Column(name = "price_step")
    private Integer priceStep;

    /**
     * 商品参数图片地址
     */
    private String info;

    /**
     * 商品状态0表示暂未发布 1表示正在发布 2表示已经结束未成交   3表示已结束已成交
     */
    private Integer state;

    @Column(name = "seller_name")
    private String sellerName;

    /**
     * 发布时间
     */
    @Column(name = "publish_time")
    private Date publishTime;

    private static final long serialVersionUID = 1L;

    /**
     * @return p_id
     */
    public Integer getpId() {
        return pId;
    }

    /**
     * @param pId
     */
    public void setpId(Integer pId) {
        this.pId = pId;
    }

    /**
     * @return p_name
     */
    public String getpName() {
        return pName;
    }

    /**
     * @param pName
     */
    public void setpName(String pName) {
        this.pName = pName;
    }

    /**
     * 获取出价次数
     *
     * @return access_num - 出价次数
     */
    public Integer getAccessNum() {
        return accessNum;
    }

    /**
     * 设置出价次数
     *
     * @param accessNum 出价次数
     */
    public void setAccessNum(Integer accessNum) {
        this.accessNum = accessNum;
    }

    /**
     * @return img
     */
    public String getImg() {
        return img;
    }

    /**
     * @param img
     */
    public void setImg(String img) {
        this.img = img;
    }

    /**
     * 获取1表示奢侈名品 2表示艺术精品 3 紫砂陶瓷 4和田玉
     *
     * @return type - 1表示奢侈名品 2表示艺术精品 3 紫砂陶瓷 4和田玉
     */
    public Integer getType() {
        return type;
    }

    /**
     * 设置1表示奢侈名品 2表示艺术精品 3 紫砂陶瓷 4和田玉
     *
     * @param type 1表示奢侈名品 2表示艺术精品 3 紫砂陶瓷 4和田玉
     */
    public void setType(Integer type) {
        this.type = type;
    }

    /**
     * @return first_price
     */
    public Double getFirstPrice() {
        return firstPrice;
    }

    /**
     * @param firstPrice
     */
    public void setFirstPrice(Double firstPrice) {
        this.firstPrice = firstPrice;
    }

    /**
     * @return last_price
     */
    public Double getLastPrice() {
        return lastPrice;
    }

    /**
     * @param lastPrice
     */
    public void setLastPrice(Double lastPrice) {
        this.lastPrice = lastPrice;
    }

    /**
     * @return margin
     */
    public Double getMargin() {
        return margin;
    }

    /**
     * @param margin
     */
    public void setMargin(Double margin) {
        this.margin = margin;
    }

    /**
     * @return price_step
     */
    public Integer getPriceStep() {
        return priceStep;
    }

    /**
     * @param priceStep
     */
    public void setPriceStep(Integer priceStep) {
        this.priceStep = priceStep;
    }

    /**
     * 获取商品参数图片地址
     *
     * @return info - 商品参数图片地址
     */
    public String getInfo() {
        return info;
    }

    /**
     * 设置商品参数图片地址
     *
     * @param info 商品参数图片地址
     */
    public void setInfo(String info) {
        this.info = info;
    }

    /**
     * 获取商品状态0表示暂未发布 1表示正在发布 2表示已经结束未成交   3表示已结束已成交
     *
     * @return state - 商品状态0表示暂未发布 1表示正在发布 2表示已经结束未成交   3表示已结束已成交
     */
    public Integer getState() {
        return state;
    }

    /**
     * 设置商品状态0表示暂未发布 1表示正在发布 2表示已经结束未成交   3表示已结束已成交
     *
     * @param state 商品状态0表示暂未发布 1表示正在发布 2表示已经结束未成交   3表示已结束已成交
     */
    public void setState(Integer state) {
        this.state = state;
    }

    /**
     * @return seller_name
     */
    public String getSellerName() {
        return sellerName;
    }

    /**
     * @param sellerName
     */
    public void setSellerName(String sellerName) {
        this.sellerName = sellerName;
    }

    /**
     * 获取发布时间
     *
     * @return publish_time - 发布时间
     */
    public Date getPublishTime() {
        return publishTime;
    }

    /**
     * 设置发布时间
     *
     * @param publishTime 发布时间
     */
    public void setPublishTime(Date publishTime) {
        this.publishTime = publishTime;
    }
}