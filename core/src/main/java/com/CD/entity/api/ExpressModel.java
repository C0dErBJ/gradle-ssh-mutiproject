package com.CD.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity.api
 * User: C0dEr
 * Date: 2016-11-28
 * Time: 17:18
 * Description:
 */
@ApiModel(description = "省市区对象")
public class ExpressModel {
    @ApiModelProperty(value = "省")
    public String province;
    @ApiModelProperty(value = "市")
    public String city;
    @ApiModelProperty(value = "区")
    public String area;
    @ApiModelProperty(value = "运费")
    public float expressFee;
}
