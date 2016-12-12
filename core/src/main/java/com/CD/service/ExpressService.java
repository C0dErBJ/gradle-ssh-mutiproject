package com.CD.service;

import com.CD.entity.api.ResponseModel;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.service
 * User: C0dEr
 * Date: 2016-11-28
 * Time: 17:16
 * Description:
 */
public interface ExpressService {
    ResponseModel getDetail(String province, String city, String area);

    ResponseModel getExperssFee(String province);

    ResponseModel getProvince();

    ResponseModel getCity(String province);

    ResponseModel getArea(String province, String city);
}
