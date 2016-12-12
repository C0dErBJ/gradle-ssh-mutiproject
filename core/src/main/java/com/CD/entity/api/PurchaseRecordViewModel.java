package com.CD.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/11/17.
 */
@ApiModel(description = "客户购买记录")
public class PurchaseRecordViewModel {
    @ApiModelProperty(name = "购买记录id")
    public long id;
    @ApiModelProperty(name = "cid")
    public String cid;
    @ApiModelProperty(name = "客户订单id")
    public String coid;
    @ApiModelProperty(name = "客户id")
    public Long customerId;
    @ApiModelProperty(name = "购买时间")
    public String cotime;
    @ApiModelProperty(name = "购买数量")
    public Integer num;
    @ApiModelProperty(name = "价格")
    public Double price;
    @ApiModelProperty(name = "已提货数量")
    public Integer tokennum;
    @ApiModelProperty(name = "商品id")
    public Long pid;
    @ApiModelProperty(name = "商品名")
    public String productName;
    @ApiModelProperty(name = "商品类型")
    public String productType;
    @ApiModelProperty(name = "商品描述")
    public String productDescribe;
    @ApiModelProperty(name = "购买细节ID")
    public Long detailId;
    @ApiModelProperty(value = "产品规格")
    public String fmodel;
    @ApiModelProperty(value = "生产商")
    public String fsupplyname;
    @ApiModelProperty(value = "产地")
    public String fsourceaddress;
    @ApiModelProperty(value = "产地")
    public String fproductarea;
    @ApiModelProperty(value = "图片")
    public String fpic;

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

    public Long getCustomerId() {
        return customerId;
    }

    public void setCustomerId(Long customerId) {
        this.customerId = customerId;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Integer getTokennum() {
        return tokennum;
    }

    public void setTokennum(Integer tokennum) {
        this.tokennum = tokennum;
    }

    public String getProductName() {
        return productName;
    }

    public void setProductName(String productName) {
        this.productName = productName;
    }

    public String getProductType() {
        return productType;
    }

    public void setProductType(String productType) {
        this.productType = productType;
    }

    public String getProductDescribe() {
        return productDescribe;
    }

    public void setProductDescribe(String productDescribe) {
        this.productDescribe = productDescribe;
    }

    public String getCotime() {
        return cotime;
    }

    public void setCotime(String cotime) {
        this.cotime = cotime;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }

    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }
}
