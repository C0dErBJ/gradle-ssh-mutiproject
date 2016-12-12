package com.CD.service.impl;

import com.CD.constant.DataValidityConstant;
import com.CD.constant.SMSTemplate;
import com.CD.constant.StatusCode;
import com.CD.dao.CaptchaDAO;
import com.CD.entity.CaptchaEntity;
import com.CD.entity.api.ResponseModel;
import com.CD.service.CaptchaService;
import com.CD.util.CaptchaUtil;
import com.CD.util.StringHelper;
import com.CD.util.ValideHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.taobao.api.ApiException;
import com.taobao.api.DefaultTaobaoClient;
import com.taobao.api.TaobaoClient;
import com.taobao.api.request.AlibabaAliqinFcSmsNumSendRequest;
import com.taobao.api.response.AlibabaAliqinFcSmsNumSendResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.service.impl
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 14:19
 * Description:
 */
@Service
@Transactional
public class CaptchaServiceImpl extends BaseServiceImpl<CaptchaEntity> implements CaptchaService {

    @Resource
    CaptchaDAO captchaDAO;

    @Override
    public ResponseModel sendResetPasswordCaptcha(String phone, HttpServletRequest request) {
        CaptchaEntity captchaEntity;
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (!ValideHelper.isPhone(phone)) {
            model.message = "请输入正确的手机号";
            return model;
        }
        List<CaptchaEntity> captchaEntities = captchaDAO.findByFields(CaptchaEntity.class, new HashMap<String, Object>() {
            {
                put("useraccount", phone);
                put("status", DataValidityConstant.INUSE);
                put("usage", SMSTemplate.RESETPASSWORD);
            }
        });
        captchaEntities = captchaEntities.stream().sorted((a, b) -> b.getCreatetime().compareTo(a.getCreatetime())).collect(Collectors.toList());
        if (!ValideHelper.isNullOrEmpty(captchaEntities) && (new Timestamp(new Date().getTime()).getTime() - captchaEntities.get(0).getCreatetime().getTime()) < 60 * 1000) {
            model.message = "操作频繁";
            return model;
        }
        String captchaCode = CaptchaUtil.getRandomNum(6);
        boolean isSuccess = sendSMS(null, new ArrayList<String>() {{
            add(phone);
        }}, new HashMap<String, Object>() {{
            put("code", captchaCode);
            put("product", StringHelper.EncryptPhone(phone, '*'));
        }}, SMSTemplate.RESETPASSWORD);
        if (!isSuccess) {
            model.message = "发送失败";
            return model;
        } else {
            captchaEntity = new CaptchaEntity();
            captchaEntity.setCreatetime(new Timestamp(new Date().getTime()));
            captchaEntity.setUpdatetime(new Timestamp(new Date().getTime()));
            captchaEntity.setStatus(DataValidityConstant.INUSE);
            captchaEntity.setCaptchacode(captchaCode);
            captchaEntity.setIp(getIp(request));
            captchaEntity.setDescription("重置登陆密码");
            captchaEntity.setUseraccount(phone);
            captchaEntity.setUsage(SMSTemplate.RESETPAYPASSWORD);
            Object result = captchaDAO.makePersistent(captchaEntity);
            if (result == null) {
                model.message = "生成失败";
                return model;
            }
        }
        model.message = "发送成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel sendResetPayPasswordCaptcha(Long userId, String phone, HttpServletRequest request) {
        CaptchaEntity captchaEntity;
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (!ValideHelper.isPhone(phone)) {
            model.message = "请输入正确的手机号";
            return model;
        }
        List<CaptchaEntity> captchaEntities = captchaDAO.findByFields(CaptchaEntity.class, new HashMap<String, Object>() {
            {
                put("userid", userId);
                put("status", DataValidityConstant.INUSE);
                put("usage", SMSTemplate.RESETPAYPASSWORD);
            }
        });
        captchaEntities = captchaEntities.stream().sorted((a, b) -> b.getCreatetime().compareTo(a.getCreatetime())).collect(Collectors.toList());
        if (!ValideHelper.isNullOrEmpty(captchaEntities) && (new Timestamp(new Date().getTime()).getTime() - captchaEntities.get(0).getCreatetime().getTime()) < 60 * 1000) {
            model.message = "操作频繁";
            return model;
        }

        String captchaCode = CaptchaUtil.getRandomNum(6);
        boolean isSuccess = sendSMS(userId, new ArrayList<String>() {{
            add(phone);
        }}, new HashMap<String, Object>() {{
            put("code", captchaCode);
            put("product", StringHelper.EncryptPhone(phone, '*'));
        }}, SMSTemplate.RESETPAYPASSWORD);
        if (!isSuccess) {
            model.message = "发送失败";
            return model;
        } else {
            captchaEntity = new CaptchaEntity();
            captchaEntity.setUpdateby(userId.intValue());
            captchaEntity.setCreateby(userId.intValue());
            captchaEntity.setCreatetime(new Timestamp(new Date().getTime()));
            captchaEntity.setUpdatetime(new Timestamp(new Date().getTime()));
            captchaEntity.setStatus(DataValidityConstant.INUSE);
            captchaEntity.setCaptchacode(captchaCode);
            captchaEntity.setIp(getIp(request));
            captchaEntity.setUserid(userId);
            captchaEntity.setDescription("重置支付密码");
            captchaEntity.setUseraccount(phone);
            captchaEntity.setUsage(SMSTemplate.RESETPAYPASSWORD);
            Object result = captchaDAO.makePersistent(captchaEntity);
            if (result == null) {
                model.message = "生成失败";
                return model;
            }
        }
        model.message = "发送成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public void useCaptcha(Long captchaId) {
        List<CaptchaEntity> captchaEntities = captchaDAO.findByFields(CaptchaEntity.class, new HashMap<String, Object>() {
            {
                put("id", captchaId);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (!ValideHelper.isNullOrEmpty(captchaEntities)) {
            captchaEntities.get(0).setStatus(DataValidityConstant.DELETED);
            captchaEntities.get(0).setUpdatetime(new Timestamp(new Date().getTime()));
            captchaDAO.makePersistent(captchaEntities.get(0));
        }
    }

    private String toString(List<String> phones) {
        if (ValideHelper.isNullOrEmpty(phones)) {
            return "";
        }
        return phones.stream().reduce((a, b) -> a + "," + b).get();
    }

    @Override
    public boolean sendSMS(Long userId, List<String> phone, Map<String, Object> SMSParam, String smsTemplate) {
        TaobaoClient client = new DefaultTaobaoClient("http://gw.api.taobao.com/router/rest", "23387234", "ee1629bad7e14113ef16b6ff86419ae2");
        AlibabaAliqinFcSmsNumSendRequest req = new AlibabaAliqinFcSmsNumSendRequest();
        req.setExtend(userId != null ? userId + "" : "");
        req.setSmsType("normal");
        req.setSmsFreeSignName("去酒庄");
        try {
            req.setSmsParamString(new ObjectMapper().writeValueAsString(SMSParam));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        req.setRecNum(toString(phone));
        req.setSmsTemplateCode(smsTemplate);
        AlibabaAliqinFcSmsNumSendResponse rsp;
        try {
            rsp = client.execute(req);
            return rsp.getBody().contains("alibaba_aliqin_fc_sms_num_send_response");
        } catch (ApiException e) {
            e.printStackTrace();
        }
        return false;
    }


    private String getIp(HttpServletRequest request) {
        String ip = request.getHeader("X-Real-IP");
        if (!ValideHelper.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            return ip;
        }
        ip = request.getHeader("X-Forwarded-For");
        if (!ValideHelper.isNullOrEmpty(ip) && !"unknown".equalsIgnoreCase(ip)) {
            int index = ip.indexOf(',');
            if (index != -1) {
                return ip.substring(0, index);
            } else {
                return ip;
            }
        } else {
            return request.getRemoteAddr();
        }

    }
}
