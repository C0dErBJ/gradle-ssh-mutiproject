package com.ZP.controller;

import com.ZP.constant.SessionConstant;
import com.ZP.constant.StatusCode;
import com.ZP.entity.api.OrderViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.entity.api.UserViewModel;
import com.ZP.service.impl.OrderServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.controller
 * User: C0dEr
 * Date: 2016-11-20
 * Time: 11:30
 * Description:
 */
@RestController
@RequestMapping("/order")
@Api(value = "/order", description = "采购订单相关")
public class OrderController {
    @Resource
    OrderServiceImpl orderService;

    @RequestMapping(value = "", method = RequestMethod.POST)
    @ApiOperation(notes = "新增采购订单", httpMethod = "POST", value = "新增采购订单", response = OrderViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel order(@RequestBody OrderViewModel orderViewModel, HttpSession session) {
        if (session.getAttribute(SessionConstant.CURRENTUSER) == null) {
            ResponseModel model = new ResponseModel();
            model.statusCode = StatusCode.FAIL;
            model.message = "用户未登陆";
            return model;
        }
        UserViewModel model = (UserViewModel) session.getAttribute(SessionConstant.CURRENTUSER);
        return orderService.order(orderViewModel, model);
    }

    @RequestMapping(value = "/user/{userid}", method = RequestMethod.GET)
    @ApiOperation(notes = "获取用户订单", httpMethod = "GET", value = "获取用户订单", response = OrderViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel orderlist(@PathVariable @ApiParam(required = true) Long userid,
                                   @RequestParam(required = false) @ApiParam(required = false) String ExpressStatus,
                                   @RequestParam @ApiParam(required = true) int pageIndex,
                                   @RequestParam @ApiParam(required = true) int pageSize) {
        return orderService.getUsersOrderList(userid, ExpressStatus, pageSize, pageIndex);
    }

    @RequestMapping(value = "/{orderid}", method = RequestMethod.GET)
    @ApiOperation(notes = "获取用户订单详情", httpMethod = "GET", value = "获取用户订单详情", response = OrderViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel orderdetail(@PathVariable @ApiParam(required = true) Long orderid) {
        return orderService.getOrderDetail(orderid);
    }
}
