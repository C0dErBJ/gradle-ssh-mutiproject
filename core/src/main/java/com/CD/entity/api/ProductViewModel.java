package com.CD.entity.api;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity.api
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 10:41
 * Description:
 */
@ApiModel(description = "产品")
public class ProductViewModel {
    @ApiModelProperty(value = "产品id")
    public long id;
    @ApiModelProperty(value = "品名")
    public String name;
    @ApiModelProperty(value = "价格")
    public Double price;
    @ApiModelProperty(value = "描述")
    public String description;
    @ApiModelProperty(value = "是否是新品")
    public boolean isNew;
    @ApiModelProperty(value = "类型")
    public String type;
    @ApiModelProperty(value = "图片（暂定base64）")
    public String pic;
    @ApiModelProperty(value = "数量，用于购买")
    public Integer num;
    @ApiModelProperty(value = "单位，待拓展，目前无用")
    public String unit;
    @ApiModelProperty(value = "金蝶商品编码")
    public String kpid;
    @ApiModelProperty(value = "金蝶商品内码")
    public Integer fitemid;
    @ApiModelProperty(value = "产品规格")
    public String fmodel;
    @ApiModelProperty(value = "生产商")
    public String fsupplyname;
    @ApiModelProperty(value = "产地")
    public String fsourceaddress;
    @ApiModelProperty(value = "产地")
    public String fproductarea;
}
