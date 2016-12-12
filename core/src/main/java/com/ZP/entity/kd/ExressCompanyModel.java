package com.ZP.entity.kd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.entity.kd
 * User: C0dEr
 * Date: 2016-11-21
 * Time: 11:15
 * Description:
 */
@ApiModel(description = "快递公司model")
public class ExressCompanyModel {
    @ApiModelProperty(value = "公司名称")
    public String name;
    @ApiModelProperty(value = "公司代号，用于查询快递")
    public String type;
    @ApiModelProperty(value = "首字母")
    public String letter;
    @ApiModelProperty(value = "电话")
    public String tel;
    @ApiModelProperty(value = "测试单号")
    public String number;
}
