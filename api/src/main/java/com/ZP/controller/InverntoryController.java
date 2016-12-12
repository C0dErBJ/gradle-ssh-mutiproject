package com.ZP.controller;

import com.ZP.entity.api.InverntoryViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.service.InverntoryService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by GhostRain on 2016/11/16.
 */
@RestController
@RequestMapping("/inverntory")
@Api(value = "/inverntory", description = "库存相关")
public class InverntoryController {
    @Resource
    InverntoryService inverntoryService;

    @RequestMapping(value = "/addinverntory", method = RequestMethod.POST)
    @ApiOperation(notes = "新增库存条目", httpMethod = "POST", value = "新增库存条目", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = InverntoryViewModel.class, code = 200, message = "新增库存条目成功"))
    public ResponseModel addInverntory(@RequestBody InverntoryViewModel inverntoryViewModel) {
        ResponseModel model = inverntoryService.addInverntory(inverntoryViewModel.getCid(), inverntoryViewModel.getPid());
        return model;
    }

    @RequestMapping(value = "/changeinverntory", method = RequestMethod.POST)
    @ApiOperation(notes = "修改库存数量,若条目不存在则会自动新增条目", httpMethod = "POST", value = "修改库存数量", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = InverntoryViewModel.class, code = 200, message = "修改库存成功"))
    public ResponseModel changeInverntory(@RequestBody InverntoryViewModel inverntoryViewModel) {
        ResponseModel model = inverntoryService.changeInverntory(inverntoryViewModel.getCid(), inverntoryViewModel.getPid(), inverntoryViewModel.getSalfnum());
        return model;
    }

    @RequestMapping(value = "/getinverntoryList", method = RequestMethod.GET)
    @ApiOperation(notes = "获取库存列表", httpMethod = "GET", value = "获取库存列表", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = InverntoryViewModel.class, code = 200, message = "成功"))
    public ResponseModel getInverntoryList(@RequestParam String cid) {
        ResponseModel model = inverntoryService.getInverntoryList(cid);
        return model;
    }
}
