package com.CD.controller;

import com.CD.constant.SessionConstant;
import com.CD.constant.StatusCode;
import com.CD.entity.api.ResponseModel;
import com.CD.entity.api.UserViewModel;
import com.CD.service.impl.CaptchaServiceImpl;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.controller
 * User: C0dEr
 * Date: 2016-11-16
 * Time: 21:59
 * Description:
 */
@RestController
@RequestMapping("/sms")
@Api(value = "/sms", description = "短信相关")
public class SmsController {

    @Resource
    CaptchaServiceImpl smsService;

    @RequestMapping(value = "/srpc", method = RequestMethod.POST)
    @ApiOperation(notes = "发送修改登陆密码验证码", httpMethod = "POST", value = "发送修改登陆密码验证码", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = ResponseModel.class, code = 200, message = "发送成功"))
    public ResponseModel sendResetPasswordCaptcha(@RequestParam @ApiParam(required = true) String phone
            , HttpServletRequest request, HttpSession session) {
        return smsService.sendResetPasswordCaptcha(phone, request);
    }

    @RequestMapping(value = "/srppc", method = RequestMethod.POST)
    @ApiOperation(notes = "发送修改支付密码验证码", httpMethod = "POST", value = "发送修改支付密码验证码", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = ResponseModel.class, code = 200, message = "发送成功"))
    public ResponseModel sendResetPayPasswordCaptcha(@RequestParam @ApiParam(required = true) String phone
            , HttpServletRequest request, HttpSession session) {
        if (session.getAttribute(SessionConstant.CURRENTUSER) == null) {
            ResponseModel model = new ResponseModel();
            model.statusCode = StatusCode.FAIL;
            model.message = "用户未登陆";
            return model;
        }
        UserViewModel model = (UserViewModel) session.getAttribute(SessionConstant.CURRENTUSER);
        return smsService.sendResetPayPasswordCaptcha(model.id, phone, request);
    }
}
