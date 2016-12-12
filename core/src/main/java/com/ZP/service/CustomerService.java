package com.ZP.service;

import com.ZP.entity.CustomerEntity;
import com.ZP.entity.api.CustomerViewModel;
import com.ZP.entity.api.IocertificateViewModel;
import com.ZP.entity.api.PurchaseRecordViewModel;
import com.ZP.entity.api.ResponseModel;

import java.util.List;

/**
 * Created by Administrator on 2016/11/16.
 */
public interface CustomerService extends BaseService<CustomerEntity> {

    ResponseModel getcustomerList(int pageIndex, int pagesize, String cid);

    ResponseModel addCustomer(CustomerViewModel customerViewModel);

    ResponseModel addPurchaseRecord(List<PurchaseRecordViewModel> purchaseRecordViewModelList);

    ResponseModel searchProduct(long channelid, String type);

    ResponseModel getPurchaseList(long customerID, String cid);

    ResponseModel takeProducts(IocertificateViewModel iocertificateViewModel);

    ResponseModel getFirstpageInfo(Long cid);

    ResponseModel getAccountInfo(Long id);


}
