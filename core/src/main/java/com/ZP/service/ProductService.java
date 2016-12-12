package com.ZP.service;

import com.ZP.entity.ProductEntity;
import com.ZP.entity.api.ResponseModel;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 10:42
 * Description:
 */
public interface ProductService extends BaseService<ProductEntity> {
    ResponseModel getProductList(int pageIndex, int pageSize, String productType, String keyword);

    ResponseModel getProductById(Long id);

    ResponseModel getProductType();
}
