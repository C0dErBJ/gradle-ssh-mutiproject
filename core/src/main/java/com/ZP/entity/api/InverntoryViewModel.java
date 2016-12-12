package com.ZP.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by GhostRain on 2016/11/16.
 */
@ApiModel(description = "库存对象")
public class InverntoryViewModel {
    @ApiModelProperty(value = "库存id")
    public Long id;
    @ApiModelProperty(value = "产品id")
    public Long pid;
    @ApiModelProperty(value = "经销商id")
    public String cid;
    @ApiModelProperty(value = "酒庄库存（暂不用）")
    public Integer num;
    @ApiModelProperty(value = "自有库存")
    public Integer salfnum;
    @ApiModelProperty(value = "商品名")
    public String productname;
    @ApiModelProperty(value = "商品价格")
    public Double price;
    @ApiModelProperty(value = "商品类型")
    public String type;
    @ApiModelProperty(value = "产品图片")
    public String pic;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    public Integer getSalfnum() {
        return salfnum;
    }

    public void setSalfnum(Integer salfnum) {
        this.salfnum = salfnum;
    }

    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
