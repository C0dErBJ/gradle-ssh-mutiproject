package com.ZP.service.impl;

import com.ZP.constant.StatusCode;
import com.ZP.dao.ProvinceDAO;
import com.ZP.entity.ProvinceEntity;
import com.ZP.entity.api.ResponseModel;
import com.ZP.service.ExpressService;
import com.ZP.util.ValideHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service.impl
 * User: C0dEr
 * Date: 2016-11-28
 * Time: 17:16
 * Description:
 */
@Service
@Transactional
public class ExpressServiceImpl implements ExpressService {
    @Resource
    ProvinceDAO provinceDAO;

    @Override
    public ResponseModel getDetail(String province, String city, String area) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        model.data = provinceDAO.getProvince(province, city, area);
        model.message = "查询成功";
        return model;
    }

    @Override
    public ResponseModel getExperssFee(String province) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (ValideHelper.isNullOrEmpty(province)) {
            model.message = "请输入省份";
            return model;
        }
        List<ProvinceEntity> provinceEntityList = provinceDAO.findByField(ProvinceEntity.class, "province", province);
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        model.data = provinceEntityList;
        return model;
    }

    @Override
    public ResponseModel getProvince() {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        model.data = provinceDAO.findAll(ProvinceEntity.class);
        model.message = "查询成功";
        return model;
    }

    @Override
    public ResponseModel getCity(String province) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        if (ValideHelper.isNullOrEmpty(province)) {
            model.statusCode = StatusCode.FAIL;
            model.message = "请输入省份";
            return model;
        }
        model.data = provinceDAO.getProvince(province, null, null);
        model.message = "查询成功";
        return model;
    }

    @Override
    public ResponseModel getArea(String province, String city) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        if (ValideHelper.isNullOrEmpty(province)) {
            model.statusCode = StatusCode.FAIL;
            model.message = "请输入省份";
            return model;
        }
        if (ValideHelper.isNullOrEmpty(city)) {
            model.statusCode = StatusCode.FAIL;
            model.message = "请输入城市";
            return model;
        }
        model.data = provinceDAO.getProvince(province, city, null);
        model.message = "查询成功";
        return model;
    }
}
