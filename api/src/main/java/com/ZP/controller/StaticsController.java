package com.ZP.controller;

import com.ZP.entity.api.ResponseModel;
import com.ZP.service.impl.OrderServiceImpl;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.Date;

/**
 * Created by C0dEr on 2016/12/7.
 */
@RestController
@RequestMapping("/statics")
@Api(value = "/statics", description = "统计相关")
public class StaticsController {
    @Resource
    OrderServiceImpl orderService;

    @RequestMapping("purchase")
    @ApiOperation(notes = "获取发货订单统计", httpMethod = "GET", value = "获取发货订单统计", response = ResponseModel.class, produces = "application/json")
    @ApiResponses(@ApiResponse(response = ResponseModel.class, code = 200, message = "获取成功"))
    public ResponseModel purchase(@RequestParam @ApiParam(required = true) String year) {
        return orderService.getPurchaseStatics(year);
    }

    @RequestMapping("order")
    @ApiOperation(notes = "获取采购订单统计", httpMethod = "GET", value = "获取采购订单统计", response = ResponseModel.class, produces = "application/json")
    @ApiResponses(@ApiResponse(response = ResponseModel.class, code = 200, message = "获取成功"))
    public ResponseModel order(@RequestParam @ApiParam(required = true) String year) {
        return orderService.getOrderStatics(year);
    }
}
