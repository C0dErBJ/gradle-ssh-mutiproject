package com.CD.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/11/16.
 */
@ApiModel(description = "客户对象")
public class CustomerViewModel {

    @ApiModelProperty(value = "客户id")
    public long id;
    @ApiModelProperty(value = "公司id")
    public String cid;
    @ApiModelProperty(value = "公司")
    public String customer;
    @ApiModelProperty(value = "姓名")
    public String customername;
    @ApiModelProperty(value = "性别")
    public String customersex;
    @ApiModelProperty(value = "地址")
    public String customeraddress;
    @ApiModelProperty(value = "电话")
    public String customerphone;
    @ApiModelProperty(value = "是否vip")
    public Integer ifvip;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    public String getCustomersex() {
        return customersex;
    }

    public void setCustomersex(String customersex) {
        this.customersex = customersex;
    }

    public String getCustomeraddress() {
        return customeraddress;
    }

    public void setCustomeraddress(String customeraddress) {
        this.customeraddress = customeraddress;
    }

    public String getCustomerphone() {
        return customerphone;
    }

    public void setCustomerphone(String customerphone) {
        this.customerphone = customerphone;
    }

    public Integer getIfvip() {
        return ifvip;
    }

    public void setIfvip(Integer ifvip) {
        this.ifvip = ifvip;
    }
}
