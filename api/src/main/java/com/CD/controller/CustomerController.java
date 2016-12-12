package com.CD.controller;

import com.CD.entity.api.*;
import com.CD.service.CustomerService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
@RestController
@RequestMapping("/customer")
@Api(value = "/customer", description = "客户相关")
public class CustomerController {
    @Resource
    CustomerService customerService;


    @RequestMapping(value = "/getcustomerlist", method = RequestMethod.GET)
    @ApiOperation(notes = "获取客户列表", httpMethod = "GET", value = "获取客户列表", response = ResponseModel.class, produces = "application/json")
    @ApiResponses(@ApiResponse(response = CustomerViewModel.class, code = 200, message = "获取成功"))
    public ResponseModel getcustomerList(@RequestParam @ApiParam(required = true) int pageIndex
            , @RequestParam @ApiParam(required = true) int pageSize, @RequestParam @ApiParam(required = true) String cid) {
        return customerService.getcustomerList(pageIndex, pageSize, cid);
    }


    @RequestMapping(value = "/addcustomer", method = RequestMethod.POST)
    @ApiOperation(notes = "新增或者编辑客户", httpMethod = "POST", value = "新增或者编辑客户", response = CustomerViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel addCustomer(@RequestBody CustomerViewModel customerViewModel) {
        return customerService.addCustomer(customerViewModel);
    }

    @RequestMapping(value = "/addpurchaserecord", method = RequestMethod.POST)
    @ApiOperation(notes = "新增客户订单", httpMethod = "POST", value = "新增客户订单", response = PurchaseRecordViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel addPurchaseRecord(@RequestBody List<PurchaseRecordViewModel> purchaseRecordViewModelList) {
        return customerService.addPurchaseRecord(purchaseRecordViewModelList);
    }

    @RequestMapping(value = "/searchproduct", method = RequestMethod.GET)
    @ApiOperation(notes = "查出商品类型和名称", httpMethod = "GET", value = "查出商品类型和名称", response = ProductViewModel.class, produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = ProductViewModel.class, code = 200, message = "查询成功"))
    public ResponseModel searchProduct(@RequestParam @ApiParam(required = true) long channelid,
                                       @RequestParam @ApiParam(required = true) String productType) {
        return customerService.searchProduct(channelid, productType);
    }


    @RequestMapping(value = "/getpurchaselist", method = RequestMethod.GET)
    @ApiOperation(notes = "查出客户购买记录", httpMethod = "GET", value = "查出客户购买记录", response = ProductViewModel.class, produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = PurchaseRecordViewModel.class, code = 200, message = "查询成功"))
    public ResponseModel getPurchaseList(@RequestParam @ApiParam(required = true) long customerId, @RequestParam @ApiParam(required = true) String cid) {
        return customerService.getPurchaseList(customerId, cid);
    }

    @RequestMapping(value = "/takeproducts", method = RequestMethod.POST)
    @ApiOperation(notes = "提货", httpMethod = "POST", value = "提货", response = IocertificateViewModel.class, produces = "application/json")
    public ResponseModel takeProducts(@RequestBody IocertificateViewModel iocertificateViewModel) {
        return customerService.takeProducts(iocertificateViewModel);
    }

    @RequestMapping(value = "/getfirstpageinfo", method = RequestMethod.GET)
    @ApiOperation(notes = "查出首页需要的数据（客户,余额,库存,客服电话）", httpMethod = "GET", value = "查出首页需要的数据（客户,余额,库存，客服电话）", produces = "application/json", consumes = "application/json")
    public ResponseModel getFirstpageInfo(@RequestParam @ApiParam(required = true, value = "用户id") Long cid) {
        return customerService.getFirstpageInfo(cid);
    }


    @RequestMapping(value = "/getaccountinfo", method = RequestMethod.GET)
    @ApiOperation(notes = "查出账户信息", httpMethod = "GET", value = "查出账户信息", response = ProductViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel getAccountInfo(@RequestParam @ApiParam(required = true) Long id) {

        return customerService.getAccountInfo(id);
    }


}
