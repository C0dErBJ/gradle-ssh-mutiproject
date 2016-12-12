package com.CD.entity.api;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity.api
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:13
 * Description:
 */
@ApiModel(description = "采购订单")
public class OrderViewModel {
    @ApiModelProperty(value = "客户姓名")
    public String customName;
    @ApiModelProperty(value = "客户手机")
    public String phone;
    @ApiModelProperty(value = "省份")
    public String province;
    @ApiModelProperty(value = "城市")
    public String city;
    @ApiModelProperty(value = "区")
    public String area;
    @ApiModelProperty(value = "具体地址")
    public String address;
    @ApiModelProperty(value = "订单总价格")
    public Float orderAmount;
    @ApiModelProperty(value = "运费")
    public Float expressFee;
    @ApiModelProperty(value = "客户公司id")
    public String cid;
    @ApiModelProperty(value = "订单id")
    public Long id;
    @ApiModelProperty(value = "快递单号")
    public String expressId;
    @ApiModelProperty(value = "订单生成编号")
    public String orderGid;
    @ApiModelProperty(value = "订单日期")
    public Date time;

    @JsonFormat(pattern = "yyyy-MM-dd", timezone = "GMT+8")
    public Date getTime() {
        return time;
    }

    public void setTime(Date time) {
        this.time = time;
    }

    public String getExpressId() {
        return expressId;
    }

    public void setExpressId(String expressId) {
        this.expressId = expressId;
    }

    public String getOrderGid() {
        return orderGid;
    }

    public void setOrderGid(String orderGid) {
        this.orderGid = orderGid;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getCustomName() {
        return customName;
    }

    public void setCustomName(String customName) {
        this.customName = customName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Float getOrderAmount() {
        return orderAmount;
    }

    public void setOrderAmount(Float orderAmount) {
        this.orderAmount = orderAmount;
    }

    public Float getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Float expressFee) {
        this.expressFee = expressFee;
    }

    public List<ProductViewModel> getProducts() {
        return products;
    }

    public void setProducts(List<ProductViewModel> products) {
        this.products = products;
    }

    @ApiModelProperty(value = "产品信息")
    public List<ProductViewModel> products;
}
