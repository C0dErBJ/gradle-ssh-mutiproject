package com.CD.service.impl;

import com.CD.constant.DataValidityConstant;
import com.CD.constant.OrderStatus;
import com.CD.constant.StatusCode;
import com.CD.dao.OrderDAO;
import com.CD.dao.OrderDetailDAO;
import com.CD.dao.ProductDAO;
import com.CD.db.PageDTO;
import com.CD.entity.OrderEntity;
import com.CD.entity.OrderdetailEntity;
import com.CD.entity.ProductEntity;
import com.CD.entity.api.OrderViewModel;
import com.CD.entity.api.ProductViewModel;
import com.CD.entity.api.ResponseModel;
import com.CD.entity.api.UserViewModel;
import com.CD.service.ExpressService;
import com.CD.service.OrderService;
import com.CD.util.CaptchaUtil;
import com.CD.util.ValideHelper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.*;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.service.impl
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:06
 * Description:
 */
@Service
@Transactional
public class OrderServiceImpl extends BaseServiceImpl<OrderEntity> implements OrderService {
    @Resource
    KDSerivceImpl kdSerivce;

    @Resource
    ProductDAO productDAO;

    @Resource
    OrderDAO orderDAO;

    @Resource
    OrderDetailDAO orderDetailDAO;

    @Resource
    ExpressService expressService;

    @Override
    public ResponseModel order(OrderViewModel orderViewModel, UserViewModel uvm) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (orderViewModel == null) {
            model.message = "订单无效";
            return model;
        }
        if (ValideHelper.isNullOrEmpty(orderViewModel.address)
                || ValideHelper.isNullOrEmpty(orderViewModel.city)
                || ValideHelper.isNullOrEmpty(orderViewModel.cid)
                || ValideHelper.isNullOrEmpty(orderViewModel.customName)
                || ValideHelper.isNullOrEmpty(orderViewModel.phone)
                || !ValideHelper.isPhone(orderViewModel.phone)
                || ValideHelper.isNullOrEmpty(orderViewModel.province)
                || orderViewModel.expressFee == null
                || ValideHelper.isNullOrEmpty(orderViewModel.products)
                ) {
            model.message = "请填写完整订单信息";
            return model;
        }
        List<Integer> fitems = new ArrayList<>();
        orderViewModel.products.forEach(a -> fitems.add(a.fitemid));
        ResponseModel syncmodel = kdSerivce.syncProductPrice(fitems);
        if (syncmodel.statusCode == StatusCode.FAIL) {
            model.message = "产品价格查同步失败";
            return model;
        }
        List<Object> productIdList = new ArrayList<>();
        orderViewModel.products.forEach(a -> productIdList.add(a.id));
        if (productIdList.size() == 0) {
            model.message = "请选择商品";
            return model;
        }
        List<ProductEntity> products = productDAO.findIn(ProductEntity.class, "id", productIdList);
        if (productIdList.size() != products.size()) {
            model.message = "部分商品不存在";
            return model;
        }

        double sum = 0;
        for (ProductEntity pe : products) {
            Optional<ProductViewModel> p = orderViewModel.products.stream().filter(b -> b.id == pe.getId()).findFirst();
            if (!p.isPresent()) {
                model.message = "部分商品不存在";
                return model;
            } else {
                p.get().kpid = pe.getFnumber();
                p.get().name = pe.getProductname();
                p.get().price = pe.getPrice();
                sum += p.get().num * pe.getPrice();
            }
        }

        int total = orderViewModel.products.stream().mapToInt(a -> a.num).sum();
        ResponseModel rm = expressService.getExperssFee(orderViewModel.province);
        if (rm.statusCode == StatusCode.FAIL || rm.data == null) {
            model.message = "未找到该地区";
            return model;
        }
        com.CD.entity.ProvinceEntity expressModels = ((List<com.CD.entity.ProvinceEntity>) rm.data).get(0);
        sum += Math.round(total / 6) * expressModels.getExpressFee();
//        Float currentBalance = kdSerivce.getBalance(uvm.fitemid);
//        if (currentBalance == null || sum > currentBalance) {
//            model.message = "账户余额不足";
//            return model;
//        }
        orderViewModel.orderGid = new Date().getTime() + CaptchaUtil.getRandomNum(6);
        ResponseModel kdOrderRp = kdSerivce.order(orderViewModel, uvm, expressModels.getExpressFee().floatValue());
        if (kdOrderRp.statusCode == StatusCode.FAIL) {
            model.message = kdOrderRp.message;
            return model;
        }
        OrderEntity orderEntity = new OrderEntity();
        orderEntity.setKiskey(kdOrderRp.data.toString());
        orderEntity.setCreateby(uvm.id.intValue());
        orderEntity.setUpdateby(uvm.id.intValue());
        orderEntity.setUpdatetime(new Timestamp(new Date().getTime()));
        orderEntity.setCreatetime(new Timestamp(new Date().getTime()));
        orderEntity.setAddress(orderViewModel.address);
        orderEntity.setAllprice(sum);
        orderEntity.setCid(uvm.getCid());
        orderEntity.setCity(orderViewModel.city);
        orderEntity.setLogistics(orderViewModel.expressFee);
        orderEntity.setOrderstauts(OrderStatus.ORDERED);
        orderEntity.setStatus(DataValidityConstant.INUSE);
        orderEntity.setProvince(orderViewModel.province);
        orderEntity.setCity(orderViewModel.city);
        orderEntity.setUserid(uvm.id);
        orderEntity.setCustomername(orderViewModel.customName);
        orderEntity.setPhone(orderViewModel.phone);
        orderEntity.setOid(orderViewModel.orderGid);
        orderEntity.setDistributionprice(0.0);
        orderEntity.setIfexpired(0);
        orderEntity.setIfexpireddistributed(0);
        orderEntity.setPlacetime(new Timestamp(new Date().getTime()));
        OrderEntity oe = (OrderEntity) orderDAO.makePersistent(orderEntity);
        orderViewModel.id = oe.getId();
        List<OrderdetailEntity> ods = new ArrayList<>();
        for (ProductViewModel a : orderViewModel.products) {
            OrderdetailEntity entity = new OrderdetailEntity();
            entity.setOid(oe.getId());
            entity.setCid(oe.getCid());
            entity.setCreateby(oe.getCreateby());
            entity.setUpdateby(oe.getUpdateby());
            entity.setCreatetime(new Timestamp(new Date().getTime()));
            entity.setUpdatetime(new Timestamp(new Date().getTime()));
            entity.setNum(a.num);
            entity.setLogisticsprice(expressModels.getExpressFee() * Math.round(a.num / 6));
            entity.setPid(a.id);
            entity.setPrice(a.price);
            entity.setStatus(DataValidityConstant.INUSE);
            ods.add(entity);
        }
        for (OrderdetailEntity od : ods) {
            orderDetailDAO.makePersistent(od);
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "下单成功";
        model.data = orderViewModel;
        return model;
    }

    @Override
    public ResponseModel getUsersOrderList(Long userId, String expressStatus, int pageSize, int pageIndex) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (userId == null) {
            model.message = "用户id不能为空";
            return model;
        }
        Map<String, Object> para = new HashMap<String, Object>() {{
            put("userid", userId);
            if (!ValideHelper.isNullOrEmpty(expressStatus))
                put("orderstauts", expressStatus);
        }};
        PageDTO<OrderEntity> ods = orderDAO.findForPageAndOrderby(OrderEntity.class, para, pageSize, pageIndex, "createtime", "desc");
        List<OrderViewModel> ovm = new ArrayList<>();
        ObjectMapper mapper = new ObjectMapper();
        List<OrderEntity> oe = null;
        try {
            oe = mapper.readValue(mapper.writeValueAsString(ods.getResultList()),
                    new TypeReference<List<OrderEntity>>() {
                    });
        } catch (IOException e) {
            e.printStackTrace();
        }
        for (OrderEntity od : oe) {
            OrderViewModel ov = new OrderViewModel() {
                {
                    id = od.getId();
                    orderGid = od.getOid();
                    orderAmount = od.getAllprice().floatValue();
                    time = new Date(od.getPlacetime().getTime());
                }
            };
            ovm.add(ov);
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        model.data = new PageDTO<OrderViewModel>(pageSize, pageIndex, ods.getRowCount(), ovm);
        return model;
    }

    @Override
    public ResponseModel getOrderDetail(Long orderId) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (orderId == null) {
            model.message = "订单id不能为空";
            return model;
        }
        List<OrderEntity> ods = orderDAO.findByField(OrderEntity.class, "id", orderId);
        if (ods.size() > 1) {
            model.message = "数据异常";
            return model;
        }
        OrderViewModel ovm = null;
        if (!ValideHelper.isNullOrEmpty(ods)) {
            ovm = new OrderViewModel() {
                {
                    id = ods.get(0).getId();
                    address = ods.get(0).getAddress();
                    province = ods.get(0).getProvince();
                    cid = ods.get(0).getCid();
                    city = ods.get(0).getCity();
                    orderAmount = ods.get(0).getAllprice().floatValue();
                    expressId = ods.get(0).getLogisticsid();
                    phone = ods.get(0).getPhone();
                    orderGid = ods.get(0).getOid();
                    time = ods.get(0).getPlacetime();
                    expressFee = ods.get(0).getLogistics();
                    customName = ods.get(0).getCustomername();
                }
            };
            List<OrderdetailEntity> odds = orderDetailDAO.findByField(OrderdetailEntity.class, "oid", ods.get(0).getId());
            List<ProductViewModel> pvm = new ArrayList<>();
            this.orderDetailDAO.getJdbcTemplate().query("select a.id,a.num,a.price,b.fmodel,b.fproductarea,b.fsourceaddress,b.fsupplyname,b.productname,b.producttype,b.picfile from orderdetail as a join product as b on a.pid=b.id where a.id=?",
                    ps -> {
                        ps.setLong(1, ods.get(0).getId());
                    },
                    rs -> {
                        pvm.add(new ProductViewModel() {
                                    {
                                        id = rs.getLong("id");
                                        price = rs.getDouble("price");
                                        num = rs.getInt("num");
                                        fmodel = rs.getString("fmodel");
                                        fproductarea = rs.getString("fproductarea");
                                        fsourceaddress = rs.getString("fsourceaddress");
                                        fsupplyname = rs.getString("fsupplyname");
                                        name = rs.getString("productname");
                                        type = rs.getString("producttype");
                                        pic = rs.getString("picfile");

                                    }
                                }
                        );
                    });
            ovm.products = pvm;
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        model.data = ovm;
        return model;

    }

    @Override
    public List<OrderEntity> getOrderWithPageNotEqStatus(int pageSize, int pageIndex, String expressStatus) {
        return orderDAO.getOrderWithPageNotEqStatus(pageSize, pageIndex, expressStatus);
    }

    @Override
    public int getCountByneqExpress(String expressStatus) {
        return orderDAO.getCountByneqExpress(expressStatus);
    }

    @Override
    public ResponseModel getPurchaseStatics(String Year) {
        Map<Integer, Float> monthSum = new HashMap<>();
        this.orderDAO.getJdbcTemplate().query("SELECT MONTH(createtime) as monthly , " +
                "sum(tokennum*price) as sumprice FROM purchaserecorddetail WHERE YEAR(createtime) = ? GROUP BY MONTH(createtime)", ps -> {
            ps.setString(1, Year);
        }, rs -> {
            monthSum.put(rs.getInt("monthly"), rs.getFloat("sumprice"));
        });
        float[] sums = new float[12];
        for (int i = 1; i <= 12; i++) {
            if (monthSum.get(i) != null) {
                sums[i - 1] = monthSum.get(i);
            } else {
                sums[i - 1] = 0;
            }
        }
        ResponseModel model = new ResponseModel();
        model.message = "查询成功";
        model.data = sums;
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel getOrderStatics(String Year) {
        Map<Integer, Float> monthSum = new HashMap<>();
        this.orderDAO.getJdbcTemplate().query("SELECT MONTH(createtime) as monthly , " +
                "sum(allprice) as sumprice FROM `order` WHERE YEAR(createtime) = ?" +
                " GROUP BY MONTH(createtime)", ps -> {
            ps.setString(1, Year);
        }, rs -> {
            monthSum.put(rs.getInt("monthly"), rs.getFloat("sumprice"));
        });
        float[] sums = new float[12];
        for (int i = 1; i <= 12; i++) {
            if (monthSum.get(i) != null) {
                sums[i - 1] = monthSum.get(i);
            } else {
                sums[i - 1] = 0;
            }
        }
        ResponseModel model = new ResponseModel();
        model.message = "查询成功";
        model.data = sums;
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

}
