package com.ZP.entity.kd;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.entity.kd
 * User: C0dEr
 * Date: 2016-11-21
 * Time: 11:10
 * Description:
 */
@ApiModel(description = "查询快递返回model")
public class ExpressModel {
    @ApiModelProperty(value = "快递单号")
    public String number;
    @ApiModelProperty(value = "快递公司")
    public String type;
    @ApiModelProperty(value = "派送状态1在途中 2派送中 3已签收 4派送失败")
    public String deliverystatus;
    @ApiModelProperty(value = "是否签收（已弃用）")
    public String issign;
    @ApiModelProperty(value = "快递运送节点")
    public List<exp> list;

    @ApiModel(description = "快递运送节点")
    private class exp {
        @ApiModelProperty(value = "发生时间")
        public String time;
        @ApiModelProperty(value = "状态")
        public String status;
    }
}
