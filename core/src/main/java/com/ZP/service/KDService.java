package com.ZP.service;

import com.ZP.entity.OrderEntity;
import com.ZP.entity.api.OrderViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.entity.api.UserViewModel;

import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:05
 * Description:
 */
public interface KDService extends BaseService<OrderEntity> {
    /**
     * 采购下单
     *
     * @param order
     * @return
     */
    ResponseModel order(OrderViewModel order, UserViewModel user, float expressFee);

    /**
     * 获取用户余额
     *
     * @param cid
     * @return
     */
    Float getBalance(Long cid);

    /**
     * 同步产品
     *
     * @return
     */
    ResponseModel syncProduct();

    /**
     * 同步产品价格for下单
     *
     * @return
     */
    ResponseModel syncProductPrice(List<Integer> fitemId);

    /**
     * 同步物流
     *
     * @return
     */
    ResponseModel syncExpress();

    /**
     * 同步渠道
     *
     * @return
     */
    ResponseModel syncChannel();

    /**
     * 同步用户
     *
     * @return
     */
    ResponseModel syncUser();

    /**
     * 同步用户
     *
     * @return
     */
    ResponseModel syncSingleUser(Integer fitemid);

    /**
     * 更新快递状态
     *
     * @return
     */
    ResponseModel updateExpress();

    /**
     * 获取物流信息
     *
     * @param expressId
     * @param type
     * @return
     */
    ResponseModel getExpress(String expressId, String type);

    /**
     * 获取物流公司
     *
     * @return
     */
    ResponseModel getExpressCompany();

}
