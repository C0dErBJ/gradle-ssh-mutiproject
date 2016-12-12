package com.ZP.service;

import com.ZP.entity.OrderEntity;
import com.ZP.entity.api.OrderViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.entity.api.UserViewModel;

import java.util.Date;
import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:05
 * Description:
 */
public interface OrderService extends BaseService<OrderEntity> {
    ResponseModel order(OrderViewModel orderViewModel, UserViewModel model);

    ResponseModel getUsersOrderList(Long userId, String expressStatus, int pageSize, int pageIndex);

    ResponseModel getOrderDetail(Long orderId);

    List<OrderEntity> getOrderWithPageNotEqStatus(int pageSize, int pageIndex, String expressStatus);

    int getCountByneqExpress(String expressStatus);

    ResponseModel getPurchaseStatics(String Year);

    ResponseModel getOrderStatics(String Year);

}
