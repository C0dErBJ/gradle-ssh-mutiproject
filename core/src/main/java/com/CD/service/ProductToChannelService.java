package com.CD.service;

import com.CD.entity.ProducttochannelEntity;
import com.CD.entity.api.PCViewModel;
import com.CD.entity.api.ResponseModel;

import java.util.List;
import java.util.Map;

/**
 * Created by C0dEr on 2016/12/5.
 */
public interface ProductToChannelService extends BaseService<ProducttochannelEntity> {
    ResponseModel getAllRelation(List<Long> ProductId);

    ResponseModel getProduct(final int pageIndex, final int pageSize);

    ResponseModel getAllChannel();

    ResponseModel setRelation(List<PCViewModel> keyValue);

}
