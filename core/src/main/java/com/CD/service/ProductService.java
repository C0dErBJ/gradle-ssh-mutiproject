package com.CD.service;

import com.CD.entity.ProductEntity;
import com.CD.entity.api.ResponseModel;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.service
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
