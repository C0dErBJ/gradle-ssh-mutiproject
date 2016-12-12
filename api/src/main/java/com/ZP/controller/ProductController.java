package com.ZP.controller;

import com.ZP.entity.api.ProductViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.service.ProductService;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.controller
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 10:36
 * Description:
 */
@RestController
@RequestMapping("/product")
@Api(value = "/product", description = "产品相关")
public class ProductController {
    @Resource
    ProductService productService;

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ApiOperation(notes = "查询产品列表", httpMethod = "GET", value = "查询产品列表", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = ProductViewModel.class, code = 200, message = "获取成功"))
    public ResponseModel getProductList(@RequestParam @ApiParam(required = true) int pageIndex,
                                        @RequestParam @ApiParam(required = true) int pageSize,
                                        @RequestParam(required = false) @ApiParam(required = false) String productType,
                                        @RequestParam(required = false) @ApiParam(required = false) String keyword) {
        return productService.getProductList(pageIndex, pageSize, productType, keyword);
    }

    @RequestMapping(value = "/{id}", method = RequestMethod.GET)
    @ApiOperation(notes = "通过id查询产品", httpMethod = "GET", value = "通过id查询产品", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = ProductViewModel.class, code = 200, message = "获取成功"))
    public ResponseModel getSingleProduct(@PathVariable @ApiParam(required = true) long id) {
        return productService.getProductById(id);
    }

    @RequestMapping(value = "/type", method = RequestMethod.GET)
    @ApiOperation(notes = "获取产品类型", httpMethod = "GET", value = "获取产品类型", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = ResponseModel.class, code = 200, message = "获取成功"))
    public ResponseModel getProductType() {
        return productService.getProductType();
    }
}
