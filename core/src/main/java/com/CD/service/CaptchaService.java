package com.CD.service;

import com.CD.entity.CaptchaEntity;
import com.CD.entity.api.ResponseModel;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.service
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 14:19
 * Description:
 */
public interface CaptchaService extends BaseService<CaptchaEntity> {

    ResponseModel sendResetPasswordCaptcha(String phone, HttpServletRequest request);

    ResponseModel sendResetPayPasswordCaptcha(Long userId, String phone, HttpServletRequest request);

    void useCaptcha(Long captchaId);

    boolean sendSMS(Long userId, List<String> phone, Map<String, Object> SMSParam, String smsTemplate);

}
