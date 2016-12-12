package com.CD.service;

import com.CD.entity.UserEntity;
import com.CD.entity.api.ResponseModel;
import com.CD.entity.api.UserViewModel;

import javax.servlet.http.HttpSession;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.service
 * User: C0dEr
 * Date: 2016-11-10
 * Time: 14:57
 * Description:
 */
public interface UserService extends BaseService<UserEntity> {

    ResponseModel login(String username, String password, String captcha, HttpSession session);

    ResponseModel logout(HttpSession session);

    ResponseModel register(UserViewModel userViewModel);

    ResponseModel unRegister(long userId, String username);

    ResponseModel updateInfo(long userId, UserViewModel userViewModel);

    ResponseModel resetPassword(String username, String newPassword, String msgCode);

    ResponseModel resetPayPassword(long id, String username, String newPassword, String msgCode);

    ResponseModel fetchUserInfo(long userId, long fitemid);

    ResponseModel getUserList(UserViewModel userViewModel);

}
