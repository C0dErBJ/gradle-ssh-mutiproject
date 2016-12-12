package com.ZP.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.entity.api
 * User: C0dEr
 * Date: 2016-11-10
 * Time: 11:42
 * Description:
 */
@ApiModel(description = "用户对象")
public class UserViewModel {
    @ApiModelProperty(value = "用户id")
    public Long id;
    @ApiModelProperty(value = "用户名")
    public String username;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    @ApiModelProperty(value = "密码")
    public String password;
    @ApiModelProperty(value = "地址")
    public String address;
    @ApiModelProperty(value = "姓名")
    public String personName;
    @ApiModelProperty(value = "手机")
    public String phone;
    @ApiModelProperty(value = "公司名称")
    public String companyName;
    @ApiModelProperty(value = "公司id")
    public String cid;
    @ApiModelProperty(value = "渠道id")
    public Long channelid;
    @ApiModelProperty(value = "账户余额")
    public Float remainmoney;
    @ApiModelProperty(value = "角色")
    public Integer role;
    @ApiModelProperty(value = "金蝶用户内码id")
    public Long fitemid;

    @ApiModelProperty(value = "金蝶用户编号")
    public String fno;

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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPersonName() {
        return personName;
    }

    public void setPersonName(String personName) {
        this.personName = personName;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public Long getChannelid() {
        return channelid;
    }

    public void setChannelid(Long channelid) {
        this.channelid = channelid;
    }

    public Float getRemainmoney() {
        return remainmoney;
    }

    public void setRemainmoney(Float remainmoney) {
        this.remainmoney = remainmoney;
    }
}
