package com.CD.entity.kd;

import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity.kd
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:09
 * Description:
 */
public class OrderModel {
    public String orderId;
    public String orderTime;
    public String otherCreateName;
    public String otherApproveName;
    public String shopProvinceGeoId;
    public String shopCityGeoId;
    public String shopSeatGeoId;
    public String clientPartyId;
    public String custNo;
    public String postalAddress;
    public List<OrderItem> orderItems;
    public String linkMan;
    public String phone;


}
