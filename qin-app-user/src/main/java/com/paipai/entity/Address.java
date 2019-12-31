package com.paipai.entity;

import java.io.Serializable;
import javax.persistence.*;

public class Address implements Serializable {
    @Id
    @Column(name = "a_id")
    private Integer aId;

    @Column(name = "u_id")
    private Integer uId;

    private String addr;

    @Column(name = "add_name")
    private String addName;

    @Column(name = "add_mobile")
    private String addMobile;

    private static final long serialVersionUID = 1L;

    /**
     * @return a_id
     */
    public Integer getaId() {
        return aId;
    }

    /**
     * @param aId
     */
    public void setaId(Integer aId) {
        this.aId = aId;
    }

    /**
     * @return u_id
     */
    public Integer getuId() {
        return uId;
    }

    /**
     * @param uId
     */
    public void setuId(Integer uId) {
        this.uId = uId;
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
     * @return add_name
     */
    public String getAddName() {
        return addName;
    }

    /**
     * @param addName
     */
    public void setAddName(String addName) {
        this.addName = addName;
    }

    /**
     * @return add_mobile
     */
    public String getAddMobile() {
        return addMobile;
    }

    /**
     * @param addMobile
     */
    public void setAddMobile(String addMobile) {
        this.addMobile = addMobile;
    }
}