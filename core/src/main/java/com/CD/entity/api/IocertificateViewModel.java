package com.CD.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Administrator on 2016/11/18.
 */
@ApiModel(description = "进出货凭证")
public class IocertificateViewModel {
    @ApiModelProperty(value = "凭证id")
    public long id;
    @ApiModelProperty(value = "收/发")
    public int inorout;
    @ApiModelProperty(value = "凭证类型(0采购/1客户订单/2管理员修改)")
    public int type;
    @ApiModelProperty(value = "凭证日期")
    public String dotime;
    @ApiModelProperty(value = "物流编号")
    public String logisticsid;
    @ApiModelProperty(value = "公司Id")
    public String cid;
    @ApiModelProperty(value = "采购单id")
    public Long oid;
    @ApiModelProperty(value = "客户订单id")
    public Long coid;
    @ApiModelProperty(value = "产品id")
    public Long pid;
    @ApiModelProperty(value = "客户id")
    public Long custid;
    @ApiModelProperty(value = "备注")
    public String comments;
    @ApiModelProperty(value = "提货数量")
    public int num;
    @ApiModelProperty(value = "购买细节id")
    public Long detailId;


    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getInorout() {
        return inorout;
    }

    public void setInorout(int inorout) {
        this.inorout = inorout;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getDotime() {
        return dotime;
    }

    public void setDotime(String dotime) {
        this.dotime = dotime;
    }

    public String getLogisticsid() {
        return logisticsid;
    }

    public void setLogisticsid(String logisticsid) {
        this.logisticsid = logisticsid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    public Long getCoid() {
        return coid;
    }

    public void setCoid(Long coid) {
        this.coid = coid;
    }

    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Long getDetailId() {
        return detailId;
    }

    public void setDetailId(Long detailId) {
        this.detailId = detailId;
    }
}
