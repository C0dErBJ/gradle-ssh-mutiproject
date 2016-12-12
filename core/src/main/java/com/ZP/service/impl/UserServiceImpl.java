package com.ZP.service.impl;

import com.ZP.constant.*;
import com.ZP.dao.CaptchaDAO;
import com.ZP.dao.UserDAO;
import com.ZP.entity.CaptchaEntity;
import com.ZP.entity.UserEntity;
import com.ZP.entity.api.ResponseModel;
import com.ZP.entity.api.UserViewModel;
import com.ZP.service.UserService;
import com.ZP.util.CaptchaUtil;
import com.ZP.util.EncryptUtil;
import com.ZP.util.ValideHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service.impl
 * User: C0dEr
 * Date: 2016-11-10
 * Time: 14:58
 * Description:
 */
@Service
@Transactional
public class UserServiceImpl extends BaseServiceImpl<UserEntity> implements UserService {
    @Resource
    UserDAO userDAO;

    @Resource
    CaptchaDAO captchaDAO;

    @Resource
    CaptchaServiceImpl smsService;

    @Resource
    KDSerivceImpl kdSerivce;

    @Override
    public ResponseModel login(String username, String password, String captcha, HttpSession session) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;

        if (ValideHelper.isNullOrEmpty(username) || ValideHelper.isNullOrEmpty(password)) {
            model.message = "请填写用户名和密码";
            return model;
        }
        if (session.getAttribute(SessionConstant.NEEDCAPTCHA) != null && ((int) session.getAttribute(SessionConstant.NEEDCAPTCHA)) == 1) {
            if (ValideHelper.isNullOrEmpty(captcha)) {
                model.message = "请填入验证码";
                return model;
            }
            if (session.getAttribute(SessionConstant.SESSION_CODE) == null) {
                model.message = "验证码不存在，请重新登陆";
                return model;
            }
            String code = session.getAttribute(SessionConstant.SESSION_CODE).toString();
            if (!code.toLowerCase().equals(captcha.toLowerCase())) {
                model.message = "验证码不正确";
                session.setAttribute(SessionConstant.SESSION_CODE, CaptchaUtil.getRandomString(4));
                return model;
            }
        }
        if (!ValideHelper.isPhone(username)) {
            model.message = "用户名格式不正确";
            return model;
        }
        List<UserEntity> users;
        try {
            String psw = EncryptUtil.EncoderByMd5(password);
            users = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
                {
                    put("username", username);
                    put("status", DataValidityConstant.INUSE);
                    put("password", psw);

                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            model.message = "异常";
            return model;
        }

        if (ValideHelper.isNullOrEmpty(users)) {
            model.message = "用户名或者密码错误";
            if (session.getAttribute(SessionConstant.NEEDCAPTCHA) == null) {
                session.setAttribute(SessionConstant.NEEDCAPTCHA, 1);
                Map<String, Object> cha = new HashMap<>();
                cha.put("needCaptcha", 1);
                cha.put("captcha", Base64.getEncoder().encode(CaptchaUtil.getCaptchaPic(96, 35, CaptchaUtil.getRandomString(4))));
                model.data = cha;
            }
            return model;
        }
        if (users.size() > 1) {
            model.message = "用户数据异常";
            return model;
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "登陆成功";
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.companyName = users.get(0).getCompanyname();
        userViewModel.address = users.get(0).getAddress();
        userViewModel.personName = users.get(0).getPersonname();
        userViewModel.phone = users.get(0).getPhone();
        userViewModel.username = users.get(0).getUsername();
        userViewModel.cid = users.get(0).getCid();
        userViewModel.id = users.get(0).getId();
        userViewModel.phone = users.get(0).getPhone();
        userViewModel.channelid = users.get(0).getChannelid();
        userViewModel.fitemid = users.get(0).getFitemId();
        userViewModel.fno = users.get(0).getCustomerid();
        userViewModel.role = users.get(0).getRole();
        model.data = userViewModel;
        users.get(0).setLastlogintime(new Timestamp(new Date().getTime()));
        userDAO.makePersistent(users.get(0));
        return model;
    }

    @Override
    public ResponseModel logout(HttpSession session) {
        ResponseModel model = new ResponseModel();
        model.message = "登出成功";
        model.statusCode = StatusCode.SUCCESS;
        session.removeAttribute(SessionConstant.CURRENTUSER);
        return model;
    }

    @Override
    public ResponseModel register(UserViewModel userViewModel) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (userViewModel == null || ValideHelper.isNullOrEmpty(userViewModel.username)
                || ValideHelper.isNullOrEmpty(userViewModel.password)
                || ValideHelper.isNullOrEmpty(userViewModel.phone)) {
            model.message = "请填写用户信息";
            return model;
        }
        if (!ValideHelper.isPhone(userViewModel.username) || !ValideHelper.isPhone(userViewModel.phone)) {
            model.message = "手机格式不正确";
        }
        if (!ValideHelper.isAGoodPassowrd(userViewModel.password)) {
            model.message = "密码强度不够";
        }
        UserEntity user = new UserEntity();
        user.setUsername(userViewModel.getUsername());
        try {
            user.setPassword(EncryptUtil.EncoderByMd5(userViewModel.password));
        } catch (Exception e) {
            e.printStackTrace();
            model.message = "系统错误";
            return model;
        }
        user.setLastlogintime(new Timestamp(new Date().getTime()));
        user.setAddress(userViewModel.address);
        user.setRole(userViewModel.role);
        user.setCompanyname(userViewModel.companyName);
        user.setPersonname(userViewModel.personName);
        user.setPhone(userViewModel.phone);
        user.setCreatetime(new Timestamp(new Date().getTime()));
        user.setUpdatetime(new Timestamp(new Date().getTime()));
        user.setStatus(DataValidityConstant.INUSE);
        UserEntity newUser = (UserEntity) userDAO.makePersistent(user);
        if (newUser == null) {
            model.message = "操作失败";
            return model;
        }
        userViewModel.id = newUser.getId();
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;
        model.data = userViewModel;
        return model;
    }

    @Override
    public ResponseModel unRegister(long userId, String username) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<UserEntity> user = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
            {
                put("id", userId);
                put("username", username);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (ValideHelper.isNullOrEmpty(user)) {
            model.message = "用户不存在";
            return model;
        }
        if (user.size() > 1) {
            model.message = "用户数据异常";
            return model;
        }
        user.get(0).setStatus(DataValidityConstant.DELETED);
        Object col = userDAO.makePersistent(user.get(0));
        if (col == null) {
            model.message = "操作失败";
            return model;
        }
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel updateInfo(long id, UserViewModel userViewModel) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<UserEntity> user = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
            {
                put("id", id);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (ValideHelper.isNullOrEmpty(user)) {
            model.message = "用户不存在";
            return model;
        }
        if (user.size() > 1) {
            model.message = "用户数据异常";
            return model;
        }
        user.get(0).setUpdatetime(new Timestamp(new Date().getTime()));
        if (!ValideHelper.isNullOrEmpty(userViewModel.getAddress())) {
            user.get(0).setAddress(userViewModel.address);
        }
        if (!ValideHelper.isNullOrEmpty(userViewModel.getPhone())) {
            user.get(0).setPhone(userViewModel.phone);
        }
        if (!ValideHelper.isNullOrEmpty(userViewModel.getCompanyName())) {
            user.get(0).setCompanyname(userViewModel.companyName);
        }
        if (!ValideHelper.isNullOrEmpty(userViewModel.getPersonName())) {
            user.get(0).setPersonname(userViewModel.personName);
        }
        Object col = userDAO.makePersistent(user.get(0));
        if (col == null) {
            model.message = "操作失败";
            return model;
        }
        model.data = user.get(0);
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel resetPassword(String username, String newPassword, String msgCode) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (ValideHelper.isNullOrEmpty(username) || ValideHelper.isNullOrEmpty(newPassword) || ValideHelper.isNullOrEmpty(msgCode)) {
            model.message = "数据参数不完整";
            return model;
        }
        if (!ValideHelper.isAGoodPassowrd(newPassword)) {
            model.message = "密码至少包含数字，字符，英文中的两种，至少8位以上";
            return model;
        }
        List<UserEntity> user = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
            {
                put("username", username);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (ValideHelper.isNullOrEmpty(user)) {
            model.message = "用户不存在";
            return model;
        }
        if (user.size() > 1) {
            model.message = "用户数据异常";
            return model;
        }
        user.get(0).setUpdatetime(new Timestamp(new Date().getTime()));
        List<CaptchaEntity> captchaEntities = captchaDAO.findByFields(CaptchaEntity.class, new HashMap<String, Object>() {
            {
                put("useraccount", username);
                put("usage", SMSTemplate.RESETPASSWORD);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (captchaEntities.size() == 0) {
            model.message = "未发送验证码";
            return model;
        }
        List<CaptchaEntity> entities = captchaEntities.stream().sorted((a, b) -> b.getCreatetime().compareTo(a.getCreatetime())).collect(Collectors.toList());
        if ((new Timestamp(new Date().getTime()).getTime() - entities.get(0).getCreatetime().getTime()) > 3 * 60 * 1000) {
            model.message = "验证码过期或不存在";
            return model;
        }

        if (!entities.get(0).getCaptchacode().equals(msgCode)) {
            model.message = "验证码错误";
            return model;
        }
        try {
            user.get(0).setPassword(EncryptUtil.EncoderByMd5(newPassword));
        } catch (Exception e) {
            e.printStackTrace();
            model.message = "系统错误";
            return model;
        }
        Object col = userDAO.makePersistent(user.get(0));
        if (col == null) {
            model.message = "操作失败";
            return model;
        }
        smsService.useCaptcha(entities.get(0).getId());
//        entities.get(0).setStatus(DataValidityConstant.DELETED);
//        captchaDAO.makePersistent(entities.get(0));
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel resetPayPassword(long id, String username, String newPassword, String msgCode) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (ValideHelper.isNullOrEmpty(username) || ValideHelper.isNullOrEmpty(newPassword) || ValideHelper.isNullOrEmpty(msgCode)) {
            model.message = "数据参数不完整";
            return model;
        }
        if (!ValideHelper.isAGoodPayPassowrd(newPassword)) {
            model.message = "密码必须是6位数字";
            return model;
        }
        List<UserEntity> user = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
            {
                put("id", id);
                put("username", username);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (ValideHelper.isNullOrEmpty(user)) {
            model.message = "用户不存在";
            return model;
        }
        if (user.size() > 1) {
            model.message = "用户数据异常";
            return model;
        }
        user.get(0).setUpdatetime(new Timestamp(new Date().getTime()));
        List<CaptchaEntity> captchaEntities = captchaDAO.findByFields(CaptchaEntity.class, new HashMap<String, Object>() {
            {
                put("userId", id);
                put("useraccount", username);
                put("usage", SMSTemplate.RESETPAYPASSWORD);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (captchaEntities.size() == 0) {
            model.message = "未发送验证码";
            return model;
        }
        List<CaptchaEntity> entities = captchaEntities.stream().sorted((a, b) -> b.getCreatetime().compareTo(a.getCreatetime())).collect(Collectors.toList());
        if ((new Timestamp(new Date().getTime()).getTime() - entities.get(0).getCreatetime().getTime()) > 3 * 60 * 1000) {
            model.message = "验证码过期或不存在";
            return model;
        }
        if (!entities.get(0).getCaptchacode().equals(msgCode)) {
            model.message = "验证码错误";
            return model;
        }
        try {
            user.get(0).setPaypassword(EncryptUtil.EncoderByMd5(newPassword));
        } catch (Exception e) {
            e.printStackTrace();
            model.message = "系统错误";
            return model;
        }
        Object col = userDAO.makePersistent(user.get(0));
        if (col == null) {
            model.message = "操作失败";
            return model;
        }
        smsService.useCaptcha(entities.get(0).getId());
//        entities.get(0).setStatus(DataValidityConstant.DELETED);
//        captchaDAO.makePersistent(entities.get(0));
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel fetchUserInfo(long userId, long fitemid) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        // kdSerivce.syncSingleUser((int) fitemid);
        List<UserEntity> users = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
            {
                put("id", userId);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (ValideHelper.isNullOrEmpty(users)) {
            model.message = "用户不存在";
            return model;
        }
        if (users.size() > 1) {
            model.message = "用户数据异常";
            return model;
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.companyName = users.get(0).getCompanyname();
        userViewModel.address = users.get(0).getAddress();
        userViewModel.personName = users.get(0).getPersonname();
        userViewModel.phone = users.get(0).getPhone();
        userViewModel.username = users.get(0).getUsername();
        userViewModel.cid = users.get(0).getCid();
        userViewModel.id = users.get(0).getId();
        userViewModel.phone = users.get(0).getPhone();
        userViewModel.channelid = users.get(0).getChannelid();
        userViewModel.fitemid = users.get(0).getFitemId();
        userViewModel.fno = users.get(0).getCustomerid();
        userViewModel.role = users.get(0).getRole();
        model.data = userViewModel;
        return model;
    }

    @Override
    public ResponseModel getUserList(UserViewModel userViewModel) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        // kdSerivce.syncSingleUser((int) fitemid);
        if (userViewModel.role != RoleConstant.ADMINISTRATOR) {
            model.message = "用户权限不正确";
            return model;
        }
        List<UserEntity> users = userDAO.findByFields(UserEntity.class, new HashMap<String, Object>() {
            {
                put("status", DataValidityConstant.INUSE);
            }
        });

        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        List<UserViewModel> userViewModels = new ArrayList<>();
        users.forEach(u -> {
            UserViewModel uvm = new UserViewModel();
            uvm.companyName = u.getCompanyname();
            uvm.address = u.getAddress();
            uvm.personName = u.getPersonname();
            uvm.phone = u.getPhone();
            uvm.username = u.getUsername();
            uvm.cid = u.getCid();
            uvm.id = u.getId();
            uvm.phone = u.getPhone();
            uvm.channelid = u.getChannelid();
            uvm.fitemid = u.getFitemId();
            uvm.fno = u.getCustomerid();
            uvm.role = u.getRole();
            userViewModels.add(uvm);
        });

        model.data = userViewModels;
        return model;
    }


}
