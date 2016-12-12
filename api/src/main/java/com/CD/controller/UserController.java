package com.CD.controller;


import com.CD.constant.SessionConstant;
import com.CD.constant.StatusCode;
import com.CD.entity.api.ResponseModel;
import com.CD.entity.api.UserViewModel;
import com.CD.service.impl.UserServiceImpl;
import com.CD.util.CaptchaUtil;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.Base64;

/**
 * ProjectName: YouChi
 * PackageName: com.CD.controller
 * UserEntity: C0dEr
 * Date: 2016-10-31
 * Time: 15:31
 * Description:
 */
@RestController
@RequestMapping("/user")
@Api(value = "/user", description = "用户相关")
public class UserController {
    @Resource
    UserServiceImpl userService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    @ApiOperation(notes = "用户登陆", httpMethod = "POST", value = "用户登陆", produces = "application/json", consumes = "application/json")
    @ApiResponses(@ApiResponse(response = UserViewModel.class, code = 200, message = "登陆成功"))
    public ResponseModel login(@RequestParam @ApiParam(required = true, example = "18888888888") String username
            , @RequestParam @ApiParam(required = true, example = "111111") String password,
                               HttpSession session,
                               @RequestParam(required = true) @ApiParam(required = true, example = "4r3e") String captcha,
                               @RequestParam(required = false) @ApiParam(required = false) boolean rmbPwd,
                               HttpServletResponse response,
                               HttpServletRequest request) {
        ResponseModel model = userService.login(username, password, captcha, session);
        session.setAttribute(SessionConstant.CURRENTUSER, model.data);
        if (model.statusCode == 0 && rmbPwd) {
            Cookie cookie = new Cookie("a&p", new String(Base64.getEncoder().encode((username + "|%|#|" + password).getBytes())));
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/login");
            response.addCookie(cookie);
        }
        if (model.statusCode == 0 && !rmbPwd) {
            Cookie cookie = new Cookie("a&p", new String(Base64.getEncoder().encode(username.getBytes())));
            cookie.setMaxAge(30 * 24 * 60 * 60);
            cookie.setPath("/login");
            response.addCookie(cookie);
        }

        return model;
    }

    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    @ApiOperation(notes = "用户登出", httpMethod = "POST", value = "用户登出", response = ResponseModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel logout(HttpSession session) {
        return userService.logout(session);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    @ApiOperation(notes = "用户注册或新增", httpMethod = "POST", value = "用户注册或新增", response = UserViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel regist(@RequestBody UserViewModel userViewModel) {
        return userService.register(userViewModel);
    }

    @RequestMapping(value = "/unregister", method = RequestMethod.DELETE)
    @ApiOperation(notes = "删除用户", httpMethod = "DELETE", value = "删除用户", response = ResponseModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel unregist(@RequestParam @ApiParam(required = true, example = "1") int userid
            , @RequestParam @ApiParam(required = true, example = "18888888888") String username) {
        return userService.unRegister(userid, username);
    }

    @RequestMapping(value = "/updateinfo/{id}", method = RequestMethod.PUT)
    @ApiOperation(notes = "用户修改信息", httpMethod = "PUT", value = "用户修改信息", response = UserViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel updateInfo(@PathVariable @ApiParam(required = true, example = "1") int id, @ApiParam(required = true) @RequestBody UserViewModel userViewModel) {
        return userService.updateInfo(id, userViewModel);
    }

    @RequestMapping(value = "/resetpassword/{userId}", method = RequestMethod.PUT)
    @ApiOperation(notes = "用户修改密码", httpMethod = "PUT", value = "用户修改密码", response = ResponseModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel resetPassword(
            @RequestParam @ApiParam(required = true) String username,
            @RequestParam @ApiParam(required = true) String newPassword,
            @RequestParam @ApiParam(required = true) String msgcode) {
        return userService.resetPassword(username, newPassword, msgcode);
    }

    @RequestMapping(value = "/resetpaypassword/{userId}", method = RequestMethod.PUT)
    @ApiOperation(notes = "用户修改支付密码", httpMethod = "PUT", value = "用户修改支付密码", response = UserViewModel.class, produces = "application/json", consumes = "application/json")
    public ResponseModel resetPayPassword(@PathVariable @ApiParam(required = true) int userId,
                                          @RequestParam @ApiParam(required = true) String username,
                                          @RequestParam @ApiParam(required = true) String newPayPassword,
                                          @RequestParam @ApiParam(required = true) String msgcode) {
        return userService.resetPayPassword(userId, username, newPayPassword, msgcode);
    }

    @RequestMapping(value = "/userinfo", method = RequestMethod.GET)
    @ApiOperation(notes = "获取用户信息", httpMethod = "GET", value = "获取用户信息", response = ResponseModel.class, produces = "application/json")
    public ResponseModel getUserInfo(HttpSession httpSession) {
        if (httpSession.getAttribute(SessionConstant.CURRENTUSER) == null) {
            ResponseModel model = new ResponseModel();
            model.statusCode = StatusCode.FAIL;
            model.message = "用户未登陆";
            return model;
        }
        UserViewModel model = (UserViewModel) httpSession.getAttribute(SessionConstant.CURRENTUSER);
        return userService.fetchUserInfo(model.id, model.fitemid);
    }

    @RequestMapping(value = "/captcha", method = RequestMethod.GET)
    @ApiOperation(notes = "图形验证码", httpMethod = "GET", value = "图形验证码", produces = "image/png")
    public void captcha(HttpServletResponse response, HttpSession session) {
        session.setAttribute(SessionConstant.NEEDCAPTCHA, 1);
        response.setContentType("image/png");
        OutputStream os = null;
        try {
            os = response.getOutputStream();
            String str = CaptchaUtil.getRandomString(4);
            ByteArrayInputStream bis = new ByteArrayInputStream(CaptchaUtil.getCaptchaPic(96, 35, str));
            session.setAttribute(SessionConstant.SESSION_CODE, str);
            BufferedImage image = ImageIO.read(bis);
            ImageIO.write(image, "png", os);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    @RequestMapping(value = "/userlist", method = RequestMethod.GET)
    @ApiOperation(notes = "获取用户列表（管理员权限）", httpMethod = "GET", value = "获取用户列表（管理员权限）", response = ResponseModel.class, produces = "application/json")
    public ResponseModel getUserList(HttpSession httpSession) {
        if (httpSession.getAttribute(SessionConstant.CURRENTUSER) == null) {
            ResponseModel model = new ResponseModel();
            model.statusCode = StatusCode.FAIL;
            model.message = "用户未登陆";
            return model;
        }
        UserViewModel model = (UserViewModel) httpSession.getAttribute(SessionConstant.CURRENTUSER);
        return userService.getUserList(model);
    }
}
