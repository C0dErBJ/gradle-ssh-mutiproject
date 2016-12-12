package com.CD.service.impl;

import com.CD.constant.DataValidityConstant;
import com.CD.constant.StatusCode;
import com.CD.dao.*;
import com.CD.db.PageDTO;
import com.CD.entity.*;
import com.CD.entity.api.*;
import com.CD.service.CustomerService;
import com.CD.util.CaptchaUtil;
import com.CD.util.ValideHelper;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by Administrator on 2016/11/16.
 */
@Service
@Transactional
public class CustomerServiceImpl extends BaseServiceImpl<CustomerEntity> implements CustomerService {

    @Resource
    CustomerDAO customerDAO;

    @Resource
    ProductDAO productDAO;

    @Resource
    PurchaseRecordDetailDAO prdDAO;

    @Resource
    PurchaseRecordDAO prDAO;

    @Resource
    InverntoryDAO ivDAO;

    @Resource
    IocertificateDAO iocDAO;

    @Resource
    UserDAO userDAO;

    @Resource
    BaseInfoDAO baseInfoDAO;

    @Resource
    KDSerivceImpl kdSerivce;

    @Override
    public ResponseModel getcustomerList(int pageIndex, int pageSize, String cid) {

        ResponseModel model = new ResponseModel();
        Map map = new HashMap<>();
        map.put("cid", "=" + cid);
        map.put("status", "=" + DataValidityConstant.INUSE);
        PageDTO<CustomerEntity> customerList = customerDAO.findForPageAndOrderbyWithExpression(CustomerEntity.class, map, pageSize, pageIndex, "createtime", "desc");

        model.data = customerList;
        model.message = "查询成功";
        model.statusCode = StatusCode.SUCCESS;

        return model;
    }

    @Override
    public ResponseModel addCustomer(CustomerViewModel customerViewModel) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        if (customerViewModel == null || ValideHelper.isNullOrEmpty(customerViewModel.customer)
                || ValideHelper.isNullOrEmpty(customerViewModel.customername)
                || ValideHelper.isNullOrEmpty(customerViewModel.customerphone)) {
            model.message = "请填写用户信息";
            return model;
        }
        if (!ValideHelper.isPhone(customerViewModel.customerphone)) {
            model.message = "手机格式不正确";
            return model;
        }

        CustomerEntity customer = new CustomerEntity();
        customer.setId(customerViewModel.getId());
        customer.setCustomer(customerViewModel.getCustomer());
        customer.setCid(customerViewModel.getCid());
        customer.setCustomeraddress(customerViewModel.getCustomeraddress());
        customer.setCustomername(customerViewModel.getCustomername());
        customer.setCustomerphone(customerViewModel.getCustomerphone());
        customer.setCustomersex(customerViewModel.getCustomersex());
        customer.setIfvip(customerViewModel.getIfvip());
        if (customerViewModel.getId() == 0) {
            customer.setCreatetime(new Timestamp(new Date().getTime()));
        } else {
            CustomerEntity customerEntity = customerDAO.findById(CustomerEntity.class, customerViewModel.getId(), false);
            customer.setCreatetime(customerEntity.getCreatetime());
        }
        customer.setUpdatetime(new Timestamp(new Date().getTime()));
        customer.setStatus(DataValidityConstant.INUSE);
        CustomerEntity customerNew = (CustomerEntity) customerDAO.makePersistent(customer);
        if (customerNew == null) {
            model.message = "操作失败";
            return model;
        }
        customerViewModel.id = customerNew.getId();
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;
        model.data = customerViewModel;
        return model;
    }

    @Override
    public ResponseModel addPurchaseRecord(List<PurchaseRecordViewModel> purchaseRecordViewModelList) {

        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        String coid = new Date().getTime() + CaptchaUtil.getRandomNum(8);
        for (int i = 0; i < purchaseRecordViewModelList.size(); i++) {
            int num = purchaseRecordViewModelList.get(i).getNum();
            Long proId = purchaseRecordViewModelList.get(i).getPid();
            Map<String, Object> map = new HashMap<>();
            map.put("pid", proId);
            map.put("status", DataValidityConstant.INUSE);
            int selfnum;
            List<InverntoryEntity> ivList = ivDAO.findByFields(InverntoryEntity.class, map);
            if (ivList.size() > 0) {
                selfnum = ivList.get(0).getSalfnum();
                if (selfnum < num) {
                    model.message = "剩余库存不足！";
                    return model;
                }
            } else {
                model.message = "产品尚未入库";
                return model;
            }

            PurchaserecorddetailEntity prd = new PurchaserecorddetailEntity();
            prd.setCoid(coid);
            prd.setCotime(Timestamp.valueOf(purchaseRecordViewModelList.get(i).getCotime() + " 00:00:00"));
            prd.setNum(purchaseRecordViewModelList.get(i).getNum());
            prd.setPid(purchaseRecordViewModelList.get(i).getPid());
            prd.setPrice(purchaseRecordViewModelList.get(i).getPrice());
            prd.setTokennum(0);
            prd.setStatus(DataValidityConstant.INUSE);
            prd.setCreatetime(new Timestamp(new Date().getTime()));
            prd.setUpdatetime(new Timestamp(new Date().getTime()));
            PurchaserecorddetailEntity prdNew = (PurchaserecorddetailEntity) prdDAO.makePersistent(prd);
            if (prdNew == null) {
                model.message = "操作失败";
                return model;
            }
            int remainNum = selfnum - num;//剩余库存
            ivList.get(0).setSalfnum(remainNum);
            InverntoryEntity ivNew = (InverntoryEntity) ivDAO.makePersistent(ivList.get(0));
            if (ivNew == null) {
                model.message = "操作失败";
                return model;
            }

        }
        PurchaserecordEntity pr = new PurchaserecordEntity();
        pr.setCid(purchaseRecordViewModelList.get(0).getCid());
        pr.setCoid(coid);
        pr.setCustid(purchaseRecordViewModelList.get(0).getCustomerId());
        pr.setCreatetime(new Timestamp(new Date().getTime()));
        pr.setUpdatetime(new Timestamp(new Date().getTime()));
        pr.setCotime(Timestamp.valueOf(purchaseRecordViewModelList.get(0).getCotime() + " 00:00:00"));
        pr.setStatus(DataValidityConstant.INUSE);
        PurchaserecordEntity prNew = (PurchaserecordEntity) prDAO.makePersistent(pr);
        if (prNew == null) {
            model.message = "操作失败";
            return model;
        }
        model.message = "创建成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel searchProduct(long channelid, String type) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<Map<String, String>> map = new ArrayList<>();
//        if (type.equals("其它")) {
//            this.productDAO.getJdbcTemplate().query("SELECT a.productname,a.producttype,a.fproductarea\n" +
//                    "FROM product AS a\n" +
//                    "WHERE a.status = 0 AND a.producttype = '其它'\n" +
//                    "ORDER BY a.id, a.createtime DESC", ps -> {
//                ps.setString(1, type);
//                ps.setLong(2, channelid);
//            }, rs -> {
//                Map<String, String> m = new HashMap<String, String>();
//                m.put("productName", rs.getString("productname") == null ? "" : rs.getString("productname"));
//                m.put("productType", rs.getString("producttype") == null ? "" : rs.getString("producttype"));
//                m.put("fproductArea", rs.getString("fproductarea") == null ? "" : rs.getString("fproductarea"));
//                m.put("productId", rs.getString("id") == null ? "" : rs.getString("id"));
//                map.add(m);
//            });
//        } else {
        this.productDAO.getJdbcTemplate().query("SELECT a.id,a.productname,a.producttype,a.fproductarea\n" +
                "FROM product AS a\n" +
                "  JOIN producttochannel AS b ON a.id = b.productid\n" +
                "WHERE a.status = 0 AND a.producttype = ? AND b.channelid = ?\n" +
                "ORDER BY a.id, a.createtime DESC", ps -> {
            ps.setString(1, type);
            ps.setLong(2, channelid);
        }, rs -> {
            Map<String, String> m = new HashMap<String, String>();
            m.put("productName", rs.getString("productname") == null ? "" : rs.getString("productname"));
            m.put("productType", rs.getString("producttype") == null ? "" : rs.getString("producttype"));
            m.put("fproductArea", rs.getString("fproductarea") == null ? "" : rs.getString("fproductarea"));
            m.put("productId", rs.getString("id") == null ? "" : rs.getString("id"));
            map.add(m);
        });
//        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        model.data = map;
        return model;
    }

    @Override
    public ResponseModel getPurchaseList(long customerID, String cid) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        Map map = new HashMap<>();
        map.put("custid", customerID);
        map.put("cid", cid);
        map.put("status", DataValidityConstant.INUSE);

        List<PurchaseRecordViewModel> prModels = new ArrayList<>();
        List<PurchaserecordEntity> prList = prDAO.findByFields(PurchaserecordEntity.class, map);
        if (prList.size() > 0) {
            for (int i = 0; i < prList.size(); i++) {
                String coid = prList.get(i).getCoid();
                Map<String, Object> map1 = new HashMap<>();
                map1.put("coid", coid);
                map1.put("status", DataValidityConstant.INUSE);
                List<PurchaserecorddetailEntity> prdList = prdDAO.findByFields(PurchaserecorddetailEntity.class, map1);
                if (prdList.size() > 0) {
                    for (int x = 0; x < prdList.size(); x++) {
                        PurchaseRecordViewModel prModel = new PurchaseRecordViewModel();
                        prModel.setNum(prdList.get(x).getNum());
                        prModel.setTokennum(prdList.get(x).getTokennum());
                        prModel.setCotime(prdList.get(x).getCotime().toString());
                        prModel.setPrice(prdList.get(x).getPrice());
                        prModel.setCustomerId(prList.get(0).getCustid());
                        prModel.setCid(prList.get(0).getCid());
                        prModel.setId(prList.get(i).getId());
                        prModel.setDetailId(prdList.get(x).getId());
                        prModel.setCoid(prdList.get(x).getCoid());
                        Long productId = prdList.get(x).getPid();
                        prModel.setPid(productId);
                        Map<String, Object> map2 = new HashMap<>();
                        map2.put("id", productId);
                        map2.put("status", DataValidityConstant.INUSE);
                        List<ProductEntity> products = productDAO.findByFields(ProductEntity.class, map2);
                        if (products.size() > 0) {
                            prModel.setProductName(products.get(0).getProductname());
                            prModel.setProductType(products.get(0).getProducttype());
                            prModel.setProductDescribe(products.get(0).getDescribe());
                            prModel.fpic = products.get(0).getPicfile();
                            prModel.fmodel = products.get(0).getFmodel();
                            prModel.fproductarea = products.get(0).getFproductarea();
                            prModel.fsourceaddress = products.get(0).getFsourceaddress();
                            prModel.fsupplyname = products.get(0).getFsupplyname();
                        }
                        prModels.add(prModel);
                    }
                }
            }
        }
        model.data = prModels;
        model.message = "查询成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel takeProducts(IocertificateViewModel iocertificateViewModel) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;

        Long detailID = iocertificateViewModel.getDetailId();
        PurchaserecorddetailEntity purchaserecorddetail = prdDAO.findById(PurchaserecorddetailEntity.class, detailID, false);
        int remainNum = purchaserecorddetail.getNum() - purchaserecorddetail.getTokennum();
        if (iocertificateViewModel.getNum() > remainNum) {
            model.message = "提货数量超出剩余可提数量！";
            return model;
        }

        int newNum = purchaserecorddetail.getTokennum() + iocertificateViewModel.getNum();
        purchaserecorddetail.setTokennum(newNum);
        PurchaserecorddetailEntity prdNew = (PurchaserecorddetailEntity) prdDAO.makePersistent(purchaserecorddetail);
        if (prdNew == null) {
            model.message = "操作失败";
            return model;
        }

        IocertificateEntity ioc = new IocertificateEntity();
        ioc.setPid(iocertificateViewModel.getPid());
        ioc.setNum(iocertificateViewModel.getNum());
        ioc.setCid(iocertificateViewModel.getCid());
        ioc.setCoid(iocertificateViewModel.getCoid());
        ioc.setCustid(iocertificateViewModel.getCustid());
        ioc.setInorout(0);
        ioc.setLogisticsid(iocertificateViewModel.getLogisticsid());
        ioc.setType(iocertificateViewModel.getType());
        ioc.setDotime(new Timestamp(new Date().getTime()));
        ioc.setCreatetime(new Timestamp(new Date().getTime()));
        ioc.setUpdatetime(new Timestamp(new Date().getTime()));

        IocertificateEntity iocNew = (IocertificateEntity) iocDAO.makePersistent(ioc);
        if (iocNew == null) {
            model.message = "操作失败";
            return model;
        }
        model.message = "操作成功";
        model.statusCode = StatusCode.SUCCESS;

        return model;
    }

    @Override
    public ResponseModel getFirstpageInfo(Long cid) {

        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        Map map = new HashMap<>();
        Float money = null;
        List<UserEntity> userEntities = userDAO.findByField(UserEntity.class, "id", cid);
        if (userEntities.size() != 1) {
            model.message = "查询失败";
            return model;
        }
        money = kdSerivce.getBalance(userEntities.get(0).getFitemId());
        map.put("cid", userEntities.get(0).getCid());
        map.put("status", DataValidityConstant.INUSE);
        List<CustomerEntity> customerList = customerDAO.findByFields(CustomerEntity.class, map);

        Map<String, Object> json = new HashMap<>();
        if (customerList.size() > 0) {
            int customerNum = customerList.size();
            json.put("customerNum", customerNum);
        }
        json.put("balance", money == null ? 0 : money);
        List<InverntoryEntity> ivtList = ivDAO.findByFields(InverntoryEntity.class, map);
        int kucun = 0;
        if (ivtList.size() > 0) {
            for (int i = 0; i < ivtList.size(); i++) {
                kucun = kucun + ivtList.get(i).getSalfnum();
            }
            json.put("kucun", kucun);
        }

        Map map1 = new HashMap();
        String key = "csphone";
        map1.put("key", key);
        map1.put("status", DataValidityConstant.INUSE);
        List<BaseinfoEntity> bis = baseInfoDAO.findByFields(BaseinfoEntity.class, map1);
        String phone = "";
        if (bis.size() > 0) {
            phone = bis.get(0).getValue();
        }
        json.put("phone", phone);
        model.message = "查询成功";
        model.statusCode = StatusCode.SUCCESS;
        model.data = json;

        return model;
    }

    @Override
    public ResponseModel getAccountInfo(Long id) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        UserEntity userEntity = userDAO.findById(UserEntity.class, id, false);
        if (userEntity == null) {
            model.message = "数据不存在！";
            return model;
        }
        UserViewModel userViewModel = new UserViewModel();
        userViewModel.setUsername(userEntity.getUsername());
        userViewModel.setAddress(userEntity.getAddress());
        userViewModel.setCompanyName(userEntity.getCompanyname());
        userViewModel.setPhone(userEntity.getPhone());
        KDSerivceImpl kd = new KDSerivceImpl();
        float money = kd.getBalance(userEntity.getFitemId());
        userViewModel.setRemainmoney(money);

        model.message = "创建成功";
        model.data = userViewModel;
        model.statusCode = StatusCode.SUCCESS;

        return model;
    }





/*    public String addIocertificate(IocertificateViewModel iocertificateViewModel){
        String message="操作失败";
        IocertificateEntity iocertificateEntity=new IocertificateEntity();
        iocertificateEntity.setInorout(iocertificateViewModel.getInorout());
        iocertificateEntity.setType(iocertificateViewModel.getType());
        iocertificateEntity.setCid(iocertificateViewModel.getCid());
        iocertificateEntity.setNum(iocertificateViewModel.getNum());
        iocertificateEntity.setOid(iocertificateViewModel.getOid());
        iocertificateEntity.setCoid(iocertificateViewModel.getCoid());

        iocertificateEntity.setDotime(new Timestamp((new Date().getTime())));
        iocertificateEntity.setCreatetime(new Timestamp(new Date().getTime()));
        iocertificateEntity.setUpdatetime(new Timestamp(new Date().getTime()));

        IocertificateEntity iocNew= (IocertificateEntity) iocDAO.makePersistent(iocertificateEntity);
        if(iocNew==null){

            return message;
        }
        message="操作成功";
        model.data="";

        return message;
    }*/


}
