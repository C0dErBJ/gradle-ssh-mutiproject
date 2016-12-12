package com.ZP.controller;

import com.ZP.entity.ProvinceEntity;
import com.ZP.entity.api.ExpressModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.service.impl.ExpressServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.controller
 * User: C0dEr
 * Date: 2016-11-29
 * Time: 10:40
 * Description:
 */
@RestController
@RequestMapping("/express")
@Api(value = "/express", description = "物流以及省市区相关")
public class ExpressController {
    @Resource
    ExpressServiceImpl expressService;

    @RequestMapping(value = "/city/{province}", method = RequestMethod.GET)
    @ApiOperation(notes = "通过省获取城市", httpMethod = "GET", value = "通过省获取城市", response = ExpressModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel getCity(@PathVariable @ApiParam(required = true) String province) {
        return expressService.getCity(province);
    }

    @RequestMapping(value = "/province", method = RequestMethod.GET)
    @ApiOperation(notes = "获取省", httpMethod = "GET", value = "获取省", response = ProvinceEntity.class, produces = "application/json", consumes = "application/json")
    public ResponseModel getProvince() {
        return expressService.getProvince();
    }

    @RequestMapping(value = "/area/{province}/{city}", method = RequestMethod.GET)
    @ApiOperation(notes = "获取区", httpMethod = "GET", value = "获取区", response = ExpressModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel getProvince(@PathVariable @ApiParam(required = true) String province, @PathVariable @ApiParam(required = true) String city) {
        return expressService.getArea(province, city);
    }

    @RequestMapping(value = "", method = RequestMethod.GET)
    @ApiOperation(notes = "自助获取", httpMethod = "GET", value = "自助获取", response = ExpressModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel getProvince(@RequestParam(required = false) @ApiParam(required = false) String province,
                                     @RequestParam(required = false) @ApiParam(required = false) String city,
                                     @RequestParam(required = false) @ApiParam(required = false) String area) {
        return expressService.getDetail(province, city, area);
    }
}
