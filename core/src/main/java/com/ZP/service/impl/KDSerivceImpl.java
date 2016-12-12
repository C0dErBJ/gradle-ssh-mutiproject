package com.ZP.service.impl;

import com.ZP.constant.DataValidityConstant;
import com.ZP.constant.OrderStatus;
import com.ZP.constant.StatusCode;
import com.ZP.dao.*;
import com.ZP.entity.*;
import com.ZP.entity.api.OrderViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.entity.api.UserViewModel;
import com.ZP.entity.kd.*;
import com.ZP.service.KDService;
import com.ZP.util.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.log4j.Logger;
import org.springframework.jdbc.core.CallableStatementCallback;
import org.springframework.jdbc.core.CallableStatementCreator;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Base64Utils;

import javax.annotation.Resource;
import javax.xml.soap.*;
import java.io.*;
import java.security.NoSuchAlgorithmException;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service.impl
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:06
 * Description:
 */

@Service
@Transactional
public class KDSerivceImpl extends BaseServiceImpl<OrderEntity> implements KDService {
    protected Logger logger = Logger.getLogger(this.getClass());

    @Resource
    UserDAO userDAO;

    @Resource
    ProductDAO productDAO;

    @Resource
    ChannelDAO channelDAO;

    @Resource
    OrderDAO orderDAO;

    @Resource
    OrderDetailDAO orderDetailDAO;

    @Resource
    IocertificateDAO iocertificateDAO;

    @Resource
    InverntoryDAO inverntoryDAO;


    @Override
    public ResponseModel order(OrderViewModel order, UserViewModel user, float expressFee) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        OrderModel om = new OrderModel();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        om.orderId = order.orderGid;
        om.otherCreateName = "";
        om.otherApproveName = "";
        om.shopProvinceGeoId = order.province;
        om.shopCityGeoId = order.city;
        om.shopSeatGeoId = order.address;
        om.shopSeatGeoId = order.area;
        om.clientPartyId = user.fitemid + "";
        om.custNo = user.fno;
        om.postalAddress = order.address;
        om.linkMan = order.customName;
        om.phone = order.phone;
        om.orderTime = sdf.format(new Date());
        List<OrderItem> orderItems = new ArrayList<>();
        order.products.forEach(a -> {
            OrderItem item = new OrderItem();
            item.itemNo = a.kpid;
            item.itemName = a.name;
            item.qty = a.num;
            item.pice = a.price.floatValue();
            item.amount = item.qty * item.pice;
            item.Fee = Math.round((float) item.qty / 6) * expressFee;
            orderItems.add(item);
        });
        om.orderItems = orderItems;
        HttpProxy proxy = HttpProxy.Instance("order");
        proxy.ps.setParam(new HashMap<String, Object>() {{
            put("dataArray", new ArrayList<OrderModel>() {{
                add(om);
            }});
        }});
        ApiResponseModel apiResponseModel = proxy.DoRequest(response -> {
            MessageFactory msgFactory = null;
            try {
                System.setProperty("javax.xml.soap.MessageFactory", "com.sun.xml.internal.messaging.saaj.soap.ver1_2.SOAPMessageFactory1_2Impl");
                msgFactory = MessageFactory.newInstance();
                SOAPMessage message = msgFactory.createMessage(new MimeHeaders(), new ByteArrayInputStream(response.body().string().getBytes("UTF-8")));
                SOAPElement rp = (SOAPElement) ((SOAPElement) message.getSOAPBody().getChildElements().next()).getChildElements().next();
                javax.xml.soap.Text content = (javax.xml.soap.Text) rp.getChildElements().next();
                JsonNode node = new ObjectMapper().readValue(content.getValue(), JsonNode.class);
                String values = node.get("dataReturn").get(0).get("iSuccess").asText();
                String msg = node.get("dataReturn").get(0).get("returnMessage").asText();
                String ord = node.get("dataReturn").get(0).get("iInterID").asText();
                return new ApiResponseModel() {
                    {
                        isSuccess = values.equals("1");
                        message = msg;
                        data = ord;
                    }
                };

            } catch (SOAPException | UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ApiResponseModel() {
                {
                    isSuccess = false;
                }
            };
        }, obj -> {
            String req = "";
            try {
                req = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
                        "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
                        "  <soap12:Body>\n" +
                        "    <createOrUpdateOrderWS xmlns=\"http://tempuri.org/\">\n" +
                        "      <token>string</token>\n" +
                        "      <JsonText>" + new ObjectMapper().writeValueAsString(obj) + "</JsonText>\n" +
                        "      <strReturnMessage>string</strReturnMessage>\n" +
                        "    </createOrUpdateOrderWS>\n" +
                        "  </soap12:Body>\n" +
                        "</soap12:Envelope>";
//                new ObjectMapper().writeValueAsString("1");
//                req = "<?xml version=\"1.0\" encoding=\"utf-8\"?>\n" +
//                        "<soap12:Envelope xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\" xmlns:soap12=\"http://www.w3.org/2003/05/soap-envelope\">\n" +
//                        "  <soap12:Body>\n" +
//                        "    <createOrUpdateOrderWS xmlns=\"http://tempuri.org/\">\n" +
//                        "      <token>string</token>\n" +
//                        "      <JsonText>" + "{\n" +
//                        "    \"dataArray\": [{\n" +
//                        "        \"orderId\": \"seorderApp01\",\n" +
//                        "        \"orderTime\": \"2016-11-29\",\n" +
//                        "        \"otherCreateName\": \"lilin\",\n" +
//                        "        \"otherApproveName\": \"lilin\",\n" +
//                        "        \"shopProvinceGeoId\": \"省\",\n" +
//                        "        \"shopCityGeoId\": \"市\",\n" +
//                        "        \"shopSeatGeoId\": \"区\",\n" +
//                        "        \"clientPartyId\": \"239\",\n" +
//                        "        \"custNo\": \"WSHOP9999\",\n" +
//                        "        \"postalAddress\": \"上海市徐汇区宜山路705号\",\n" +
//                        "        \"linkMan\": \"李林\",\n" +
//                        "        \"phone\": \"13636581734\",\n" +
//                        "        \"orderItems\": [{\n" +
//                        "            \"itemNo\": \"RF00200020001\",\n" +
//                        "            \"itemName\": \"贝卡塔纳庄园干红葡萄酒\",\n" +
//                        "            \"pice\": \"500\",\n" +
//                        "            \"qty\": \"2\",\n" +
//                        "            \"amount\": \"1000\",\n" +
//                        "            \"Fee\": \"8\"\n" +
//                        "        }, {\n" +
//                        "            \"itemNo\": \"RA00200020002\",\n" +
//                        "            \"itemName\": \"诺顿庄园探戈马尔白克干红葡萄酒\",\n" +
//                        "            \"pice\": \"500\",\n" +
//                        "            \"qty\": \"2\",\n" +
//                        "            \"amount\": \"1000\",\n" +
//                        "            \"Fee\": \"8\"\n" +
//                        "        }]\n" +
//                        "    }]\n" +
//                        "}" + "</JsonText>\n" +
//                        "      <strReturnMessage>string</strReturnMessage>\n" +
//                        "    </createOrUpdateOrderWS>\n" +
//                        "  </soap12:Body>\n" +
//                        "</soap12:Envelope>";

            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }
            return req;
        });
        model.statusCode = apiResponseModel.isSuccess ? StatusCode.SUCCESS : StatusCode.FAIL;
        model.message = ValideHelper.isNullOrEmpty(apiResponseModel.message) ? "下单失败" : apiResponseModel.message;
        model.data = apiResponseModel.data;
        return model;
    }

    @Override
    /**
     * @param 金蝶fitemid
     */
    public Float getBalance(Long cid) {
        float[] balance = new float[1];
        DbManager.Instance().jdbcTemplate.execute(new CallableStatementCreator() {
            @Override
            public CallableStatement createCallableStatement(Connection connection) throws SQLException {
                String storedProc = "{call sp_shkd_getRemainARAmount(?)}";// 调用的sql
                CallableStatement cs = connection.prepareCall(storedProc);
                cs.setLong(1, cid);// 设置输入参数的值
                return cs;
            }
        }, (CallableStatementCallback<RemainModel>) callableStatement ->
        {
            callableStatement.execute();
            ResultSet set = callableStatement.getResultSet();
            if (set.next()) {
                balance[0] = (Float) set.getFloat("FRemaiAmount");
            }
            return null;
        });
        return balance[0] == 0 ? null : balance[0];
    }

    @Override
    public ResponseModel syncProduct() {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<ProductModel> models = new ArrayList<>();
        final int[] total = new int[1];
        int pageSize = 50;
        logger.info("-------------------正在同步产品信息----------------------");
        DbManager.Instance().jdbcTemplate.query("select count(*) as totalCount from shkd_view_ICItem", rc -> {
            total[0] = rc.getInt("totalCount");
        });
        logger.info("-------------------发现" + total[0] + "条数据----------------------");
        int pages = (int) Math.ceil((float) total[0] / pageSize);
        for (int i = 0; i < pages; i++) {
            DbManager.Instance().jdbcTemplate.query("SELECT TOP " + pageSize + " * FROM( SELECT ROW_NUMBER() OVER(ORDER BY FitemID) AS RowNumber, Fitemid, Fnumber, Fname, Fmodel, FSourceAddress, Fyear, Fgrapes, FProductArea, Ftype, FSupplyName, Fstatus, Fpicture, FpurchasePrice, FsalePrice FROM shkd_view_ICItem) AS A WHERE RowNumber > " + pageSize * (i), (ResultSet rs) -> {
                ProductModel pModel = new ProductModel();
                pModel.Fitemid = rs.getLong("Fitemid");
                pModel.Fnumber = rs.getString("Fnumber");
                pModel.Fname = rs.getString("Fname") != null ? rs.getString("Fname").trim() : "";
                pModel.Fmodel = rs.getString("Fmodel");
                pModel.FSourceAddress = rs.getString("FSourceAddress");
                pModel.Fyear = rs.getString("Fyear");
                pModel.Fgrapes = rs.getString("Fgrapes");
                pModel.FProductArea = rs.getString("FProductArea");
                pModel.Ftype = rs.getString("Ftype");
                pModel.FSupplyName = rs.getString("FSupplyName");
                pModel.Fstatus = rs.getInt("Fstatus");
                byte[] pic = rs.getBytes("Fpicture");
                if (pic != null) {
                    pModel.Fpicture = Base64Utils.encodeToString(pic);
                }
                pModel.FpurchasePrice = rs.getFloat("FpurchasePrice");
                pModel.FsalePrice = rs.getFloat("FsalePrice");
                models.add(pModel);
            });
            if (models.size() == 0) {
                logger.info("-------------------发现0条数据----------------------");
                model.statusCode = StatusCode.SUCCESS;
                model.message = "更新完成";
                return model;
            }
            logger.info("-------------------同步" + models.size() + "条数据----------------------");
            List<ProductEntity> products = productDAO.findIn(ProductEntity.class, "pid", models.stream().map(a -> a.Fitemid.longValue()).collect(Collectors.toList()));
            List<ProductEntity> newProducts = new ArrayList<>();
            models.forEach(a -> {
                Optional<ProductEntity> mdl = products.stream().filter(b -> b.getPid().equals(a.Fitemid)).findFirst();
                if (mdl.isPresent()) {
                    ProductEntity pe = mdl.get();
                    pe.setFnumber(a.Fnumber);
                    pe.setProductname(a.Fname);
                    pe.setFmodel(a.Fmodel);
                    pe.setFsourceaddress(a.FSourceAddress);
                    pe.setFyear(a.Fyear);
                    pe.setFgrapes(a.Fgrapes);
                    pe.setFproductarea(a.FProductArea);
                    pe.setProducttype(a.Ftype == null ? "其它" : a.Ftype);
                    pe.setFsupplyname(a.FSupplyName);
                    pe.setStatus(a.Fstatus);
                    pe.setPicfile(a.Fpicture);
                    pe.setPrice(a.FsalePrice.doubleValue());
                    pe.setIsNew(0);
                    pe.setRecommendedprice(a.FpurchasePrice.doubleValue());
                    pe.setUpdatetime(new Timestamp(new Date().getTime()));
                } else {
                    ProductEntity pe = new ProductEntity();
                    pe.setPid(a.Fitemid);
                    pe.setFnumber(a.Fnumber);
                    pe.setProductname(a.Fname);
                    pe.setFmodel(a.Fmodel);
                    pe.setFsourceaddress(a.FSourceAddress);
                    pe.setFyear(a.Fyear);
                    pe.setFgrapes(a.Fgrapes);
                    pe.setFproductarea(a.FProductArea);
                    pe.setProducttype(a.Ftype);
                    pe.setFsupplyname(a.FSupplyName);
                    pe.setStatus(a.Fstatus);
                    pe.setPicfile(a.Fpicture);
                    pe.setPrice(a.FsalePrice.doubleValue());
                    pe.setIsNew(1);
                    pe.setRecommendedprice(a.FpurchasePrice.doubleValue());
                    pe.setUpdatetime(new Timestamp(new Date().getTime()));
                    pe.setCreatetime(new Timestamp(new Date().getTime()));
                    newProducts.add(pe);

                }
            });
            products.addAll(newProducts);
            List<ProductEntity> uus = this.productDAO.batchCreateOrUpdate(products);
            if (ValideHelper.isNullOrEmpty(uus)) {
                logger.info("-------------------更新失败----------------------");
                model.message = "更新失败";
                return model;
            }
            logger.info("-------------------更新" + uus.size() + "条数据----------------------");
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "同步成功";
        return model;
    }

    @Override
    public ResponseModel syncProductPrice(List<Integer> fitemids) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<ProductModel> models = new ArrayList<>();
        String sql = " IN (";
        sql += fitemids.stream().map(a -> a.toString()).collect(Collectors.joining(","));
        sql += ")";
        logger.info("-------------------正在同步产品信息----------------------");
        DbManager.Instance().jdbcTemplate.query("select Fitemid, Fnumber, Fname, Fmodel, FSourceAddress, Fyear, Fgrapes, FProductArea, Ftype, FSupplyName, Fstatus, Fpicture,FpurchasePrice,FsalePrice from shkd_view_ICItem WHERE Fitemid" + sql, rs -> {
            ProductModel pModel = new ProductModel();
            pModel.Fitemid = rs.getLong("Fitemid");
            pModel.Fnumber = rs.getString("Fnumber");
            pModel.Fname = rs.getString("Fname") != null ? rs.getString("Fname").trim() : "";
            pModel.Fstatus = rs.getInt("Fstatus");
            pModel.FpurchasePrice = rs.getFloat("FpurchasePrice");
            pModel.FsalePrice = rs.getFloat("FsalePrice");
            models.add(pModel);
        });
        if (models.size() == 0) {
            logger.info("-------------------发现0条数据----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------同步" + models.size() + "条数据----------------------");
        List<ProductEntity> products = productDAO.findIn(ProductEntity.class, "pid", models.stream().map(a -> a.Fitemid).collect(Collectors.toList()));
        List<ProductEntity> newProducts = new ArrayList<>();
        models.forEach(a -> {
            Optional<ProductEntity> mdl = products.stream().filter(b -> Objects.equals(b.getPid(), a.Fitemid)).findFirst();
            if (mdl.isPresent()) {
                ProductEntity pe = mdl.get();
                pe.setFnumber(a.Fnumber);
                pe.setProductname(a.Fname);
                pe.setStatus(a.Fstatus);
                pe.setPrice(a.FsalePrice.doubleValue());
                pe.setRecommendedprice(a.FpurchasePrice.doubleValue());
                pe.setUpdatetime(new Timestamp(new Date().getTime()));
            }
        });
        products.addAll(newProducts);
        List<ProductEntity> uus = this.productDAO.batchCreateOrUpdate(products);
        if (ValideHelper.isNullOrEmpty(uus)) {
            logger.info("-------------------更新失败----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------更新" + uus.size() + "条数据----------------------");
        model.statusCode = StatusCode.SUCCESS;
        model.message = "同步成功";
        return model;
    }

    @Override
    public ResponseModel syncExpress() {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<DeliveryModel> models = new ArrayList<>();
        logger.info("-------------------正在同步发货信息----------------------");
        DbManager.Instance().jdbcTemplate.query("SELECT FOrderID, FOrderNo, FexpressName, FexpressNo FROM shkd_view_Send", rs -> {
            DeliveryModel deliveryModel = new DeliveryModel();
            deliveryModel.FOrderID = rs.getLong("FOrderID");
            deliveryModel.FOrderNo = rs.getString("FOrderNo");
            deliveryModel.FexpressName = rs.getString("FexpressName");
            deliveryModel.FexpressNo = rs.getString("FexpressNo");
            models.add(deliveryModel);
        });
        if (models.size() == 0) {
            logger.info("-------------------发现0条数据----------------------");
            model.statusCode = StatusCode.SUCCESS;
            model.message = "更新完成";
            return model;
        }
        logger.info("-------------------发现" + models.size() + "条数据----------------------");
        List<OrderEntity> orders = orderDAO.findIn(OrderEntity.class, "forderid", models.stream().map(a -> a.FOrderID).collect(Collectors.toList()));
        List<OrderEntity> newOrders = new ArrayList<>();
        models.forEach(a -> {
            Optional<OrderEntity> mdl = orders.stream().filter(b -> Objects.equals(b.getForderid(), a.FOrderID)).findFirst();
            if (mdl.isPresent()) {
                OrderEntity oe = mdl.get();
                oe.setExpresscompany(a.FexpressName);
                oe.setLogisticsid(a.FexpressNo);
                oe.setKiskey(a.FOrderNo);
                oe.setUpdatetime(new Timestamp(new Date().getTime()));
            } else {
                OrderEntity oe = new OrderEntity();
                oe.setExpresscompany(a.FexpressName);
                oe.setLogisticsid(a.FexpressNo);
                oe.setKiskey(a.FOrderNo);
                oe.setForderid(a.FOrderID);
                oe.setUpdatetime(new Timestamp(new Date().getTime()));
                oe.setCreatetime(new Timestamp(new Date().getTime()));
                newOrders.add(oe);
            }
        });
        orders.addAll(newOrders);
        List<OrderEntity> uus = this.orderDAO.batchCreateOrUpdate(orders);
        if (ValideHelper.isNullOrEmpty(uus)) {
            logger.info("-------------------更新失败----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------更新" + uus.size() + "条数据----------------------");
        model.statusCode = StatusCode.SUCCESS;
        model.message = "同步成功";
        return model;
    }

    @Override
    public ResponseModel syncChannel() {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<ChannelModel> models = new ArrayList<>();
        logger.info("-------------------正在同步渠道信息----------------------");
        DbManager.Instance().jdbcTemplate.query("select fid, FInterID, Fname, Fstatus from shkd_view_Channel", rs -> {
            ChannelModel channelModel = new ChannelModel();
            channelModel.Fitemid = rs.getLong("FInterID");
            channelModel.Fnumber = rs.getString("fid");
            channelModel.Fname = rs.getString("Fname");
            channelModel.Fstatus = rs.getInt("Fstatus");
            models.add(channelModel);
        });
        if (models.size() == 0) {
            logger.info("-------------------发现0条数据----------------------");
            model.statusCode = StatusCode.SUCCESS;
            model.message = "更新完成";
            return model;
        }
        logger.info("-------------------发现" + models.size() + "条数据----------------------");
        List<ChannelEntity> channels = channelDAO.findIn(ChannelEntity.class, "fitemid", models.stream().map(a -> a.Fitemid).collect(Collectors.toList()));
        List<ChannelEntity> newChannels = new ArrayList<>();
        models.forEach(a -> {
            Optional<ChannelEntity> mdl = channels.stream().filter(b -> Objects.equals(b.getFitemid(), a.Fitemid)).findFirst();
            if (mdl.isPresent()) {
                ChannelEntity ue = mdl.get();
                ue.setKey(a.Fnumber);
                ue.setChannelname(a.Fname);
                ue.setStatus(a.Fstatus);
                ue.setUpdatetime(new Timestamp(new Date().getTime()));
            } else {
                ChannelEntity ue = new ChannelEntity();
                ue.setKey(a.Fnumber);
                ue.setChannelname(a.Fname);
                ue.setStatus(a.Fstatus);
                ue.setFitemid(a.Fitemid);
                ue.setUpdatetime(new Timestamp(new Date().getTime()));
                ue.setCreatetime(new Timestamp(new Date().getTime()));
                newChannels.add(ue);
            }
        });
        channels.addAll(newChannels);
        List<ChannelEntity> uus = this.channelDAO.batchCreateOrUpdate(channels);
        if (ValideHelper.isNullOrEmpty(uus)) {
            logger.info("-------------------更新失败----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------更新" + uus.size() + "条数据----------------------");
        model.statusCode = StatusCode.SUCCESS;
        model.message = "同步成功";
        return model;
    }

    @Override
    public ResponseModel syncUser() {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<UserModel> models = new ArrayList<>();
        logger.info("-------------------正在同步用户信息----------------------");
        DbManager.Instance().jdbcTemplate.query("SELECT FItemID, Fnumber, Fname, linkMan, shoptelNumber, shopPhone, shopProvinceGeoId, shopCityGeoId, shopSeatGeoId, comments, Fstatus, FchanelNo FROM shkd_view_Customer", rs -> {
            UserModel userModel = new UserModel();
            userModel.FItemID = rs.getLong("FItemID");
            userModel.Fnumber = rs.getString("Fnumber");
            userModel.Fname = rs.getString("Fname");
            userModel.linkMan = rs.getString("linkMan");
            userModel.shoptelNumber = rs.getString("shoptelNumber");
            userModel.shopPhone = rs.getString("shopPhone");
            userModel.shopProvinceGeoId = rs.getString("shopProvinceGeoId");
            userModel.shopCityGeoId = rs.getString("shopCityGeoId");
            userModel.shopSeatGeoId = rs.getString("shopSeatGeoId");
            userModel.comments = rs.getString("comments");
            userModel.Fstatus = rs.getInt("Fstatus");
            userModel.FchanelNo = rs.getLong("FchanelNo");
            models.add(userModel);
        });
        if (models.size() == 0) {
            logger.info("-------------------发现0条数据----------------------");
            model.statusCode = StatusCode.SUCCESS;
            model.message = "更新完成";
            return model;
        }
        logger.info("-------------------发现" + models.size() + "条数据----------------------");
        List<UserEntity> users = userDAO.findIn(UserEntity.class, "fitemid", models.stream().map(a -> a.FItemID).collect(Collectors.toList()));
        List<UserEntity> newUsers = new ArrayList<>();
        models.forEach(a -> {
            Optional<UserEntity> mdl = users.stream().filter(b -> b.getFitemId().equals(a.FItemID)).findFirst();
            if (mdl.isPresent()) {
                UserEntity ue = mdl.get();
                ue.setFitemId(a.FItemID);
                ue.setUsername(a.shopPhone);
                ue.setRemark(a.comments);
                ue.setPhone(a.shopPhone);
                ue.setTelphone(a.shoptelNumber);
                ue.setChannelid(a.FchanelNo);
                ue.setArea(a.shopSeatGeoId);
                ue.setCity(a.shopCityGeoId);
                ue.setProvince(a.shopProvinceGeoId);
                ue.setCustomerid(a.Fnumber);
                ue.setPersonname(a.Fname);
                ue.setStatus(a.Fstatus);
                ue.setLinkMan(a.linkMan);
                ue.setUpdatetime(new Timestamp(new Date().getTime()));
            } else {

                try {
                    UserEntity ue = new UserEntity();
                    ue.setPassword(EncryptUtil.EncoderByMd5("123456"));
                    ue.setFitemId(a.FItemID);
                    ue.setUsername(a.shopPhone);
                    ue.setRemark(a.comments);
                    ue.setPhone(a.shopPhone);
                    ue.setTelphone(a.shoptelNumber);
                    ue.setChannelid(a.FchanelNo);
                    ue.setArea(a.shopSeatGeoId);
                    ue.setCity(a.shopCityGeoId);
                    ue.setProvince(a.shopProvinceGeoId);
                    ue.setCustomerid(a.Fnumber);
                    ue.setPersonname(a.Fname);
                    ue.setStatus(a.Fstatus);
                    ue.setLinkMan(a.linkMan);
                    ue.setUpdatetime(new Timestamp(new Date().getTime()));
                    ue.setCreatetime(new Timestamp(new Date().getTime()));
                    newUsers.add(ue);
                } catch (NoSuchAlgorithmException | UnsupportedEncodingException e) {
                    e.printStackTrace();
                    logger.info("-------------------异常,fitemid为" + a.FItemID + "的数据插入失败----------------------");
                }

            }
        });
        users.addAll(newUsers);
        List<UserEntity> uus = this.userDAO.batchCreateOrUpdate(users);
        if (ValideHelper.isNullOrEmpty(uus)) {
            logger.info("-------------------更新失败----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------更新" + uus.size() + "条数据----------------------");
        model.statusCode = StatusCode.SUCCESS;
        model.message = "同步成功";
        return model;
    }

    @Override
    public ResponseModel syncSingleUser(Integer fitemid) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<UserModel> models = new ArrayList<>();
        logger.info("-------------------正在同步用户信息----------------------");
        DbManager.Instance().jdbcTemplate.query("SELECT FItemID, Fnumber, Fname, linkMan, shoptelNumber, shopPhone, shopProvinceGeoId, shopCityGeoId, shopSeatGeoId, comments, Fstatus, FchanelNo FROM shkd_view_Customer WHERE FItemID=" + fitemid, rs -> {
            UserModel userModel = new UserModel();
            userModel.FItemID = rs.getLong("FItemID");
            userModel.Fnumber = rs.getString("Fnumber");
            userModel.Fname = rs.getString("Fname");
            userModel.linkMan = rs.getString("linkMan");
            userModel.shoptelNumber = rs.getString("shoptelNumber");
            userModel.shopPhone = rs.getString("shopPhone");
            userModel.shopProvinceGeoId = rs.getString("shopProvinceGeoId");
            userModel.shopCityGeoId = rs.getString("shopCityGeoId");
            userModel.shopSeatGeoId = rs.getString("shopSeatGeoId");
            userModel.comments = rs.getString("comments");
            userModel.Fstatus = rs.getInt("Fstatus");
            userModel.FchanelNo = rs.getLong("FchanelNo");
            models.add(userModel);
        });
        if (models.size() == 0) {
            logger.info("-------------------发现0条数据----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------发现" + models.size() + "条数据----------------------");
        List<UserEntity> users = userDAO.findIn(UserEntity.class, "fitemid", models.stream().map(a -> a.FItemID).collect(Collectors.toList()));
        List<UserEntity> newUsers = new ArrayList<>();
        models.forEach(a -> {
            Optional<UserEntity> mdl = users.stream().filter(b -> Objects.equals(b.getFitemId(), a.FItemID)).findFirst();
            if (mdl.isPresent()) {
                UserEntity ue = mdl.get();
                ue.setFitemId(a.FItemID);
                ue.setUsername(a.shopPhone);
                ue.setRemark(a.comments);
                ue.setPhone(a.shopPhone);
                ue.setTelphone(a.shoptelNumber);
                ue.setChannelid(a.FchanelNo);
                ue.setArea(a.shopSeatGeoId);
                ue.setCity(a.shopCityGeoId);
                ue.setProvince(a.shopProvinceGeoId);
                ue.setCustomerid(a.Fnumber);
                ue.setPersonname(a.Fname);
                ue.setStatus(a.Fstatus);
                ue.setLinkMan(a.linkMan);
                ue.setUpdatetime(new Timestamp(new Date().getTime()));
            }
        });
        users.addAll(newUsers);
        List<UserEntity> uus = this.userDAO.batchCreateOrUpdate(users);
        if (ValideHelper.isNullOrEmpty(uus)) {
            logger.info("-------------------更新失败----------------------");
            model.message = "更新失败";
            return model;
        }
        logger.info("-------------------更新" + uus.size() + "条数据----------------------");
        model.statusCode = StatusCode.SUCCESS;
        model.message = "同步成功";
        return model;
    }

    @Override
    public ResponseModel updateExpress() {
        ResponseModel rmodel = new ResponseModel();
        rmodel.statusCode = StatusCode.FAIL;
        int count = orderDAO.getCountByneqExpress(OrderStatus.ORDER_SUCCESS);
        int pageSize = 100;
        double pages = Math.ceil(count / pageSize);
        logger.info("-------------------发现" + count + "条数据----------------------");
        for (int i = 0; i < pages; i++) {
            List<OrderEntity> ods = orderDAO.getOrderWithPageNotEqStatus(pageSize, i + 1, OrderStatus.ORDER_SUCCESS);
            ods.forEach(a -> {
                if (ValideHelper.isNullOrEmpty(a.getLogisticsid())) {
                    a.setExpressstatus(OrderStatus.ORDER_UNSEND);
                } else {
                    ResponseModel model = getExpress(a.getLogisticsid(), null);
                    if (model.statusCode == StatusCode.SUCCESS) {
                        ExpressModel m = (ExpressModel) model.data;
                        switch (m.deliverystatus) {
                            case "1":
                                a.setExpressstatus(OrderStatus.ORDER_ONTHEWAY);
                                break;
                            case "2":
                                a.setExpressstatus(OrderStatus.ORDER_EXPRESS);
                                break;
                            case "3":
                                a.setExpressstatus(OrderStatus.ORDER_SUCCESS);
                                IocertificateEntity en = new IocertificateEntity();
                                en.setCreatetime(new Timestamp(new Date().getTime()));
                                en.setUpdatetime(new Timestamp(new Date().getTime()));
                                en.setType(2);
                                en.setDotime(new Timestamp(new Date().getTime()));
                                en.setLogisticsid(a.getLogisticsid());
                                en.setCid(a.getCid());
                                en.setCoid(a.getId());
                                en.setCustid(a.getUserid());
                                en.setKiskey(a.getKiskey());
                                en.setStatus(DataValidityConstant.INUSE);
                                en.setInorout(1);
                                en.setNum(this.orderDetailDAO.findByField(OrderdetailEntity.class, "oid", a.getId()).size());
                                Object result = iocertificateDAO.makePersistent(en);
                                if (result == null) {
                                    a.setExpressstatus(OrderStatus.ORDER_EXPRESS);
                                    logger.info("-------------------凭证生成失败，记录将会在下一次同步中更新----------------------");
                                }
                                List<InverntoryEntity> ies = new ArrayList<InverntoryEntity>();
                                this.orderDAO.getJdbcTemplate().query("select b.pid,b.num,a.cid from `order` as a join orderdetail as b on a.id=b.oid where a.id=?",
                                        ps -> {
                                            ps.setLong(1, a.getId());
                                        }, rs -> {
                                            InverntoryEntity ie = new InverntoryEntity();
                                            ie.setCid(rs.getString("cid"));
                                            ie.setCreatetime(new Timestamp(new Date().getTime()));
                                            ie.setUpdatetime(new Timestamp(new Date().getTime()));
                                            ie.setStatus(DataValidityConstant.INUSE);
                                            // ie.setNum(rs.getInt("num"));
                                            ie.setPid(rs.getLong("pid"));
                                            ie.setSalfnum(rs.getInt("num"));
                                            ies.add(ie);
                                        });
                                List<InverntoryEntity> oies = this.inverntoryDAO.findIn(InverntoryEntity.class, "pid", ies.stream().map(b -> b.getPid()).collect(Collectors.toList()));
                                List<InverntoryEntity> newie = new ArrayList<InverntoryEntity>();
                                ies.forEach(b -> {
                                    Optional<InverntoryEntity> o = oies.stream().filter(c -> c.getPid().equals(b.getPid())).findFirst();
                                    if (o.isPresent()) {
                                        InverntoryEntity cie = o.get();
                                        cie.setSalfnum(b.getSalfnum() + cie.getSalfnum());
                                        cie.setUpdatetime(new Timestamp(new Date().getTime()));
                                        newie.add(cie);
                                    } else {
                                        newie.add(b);
                                    }
                                });
                                List<InverntoryEntity> updateie = this.inverntoryDAO.batchCreateOrUpdate(newie);
                                if (updateie == null) {
                                    a.setExpressstatus(OrderStatus.ORDER_EXPRESS);
                                    logger.info("-------------------库存更新失败，记录将会在下一次同步中更新----------------------");
                                }
                                break;
                            case "4":
                                a.setExpressstatus(OrderStatus.ORDER_EXPRESSFAIL);
                                break;
                            default:
                                break;
                        }
                    } else {
                        logger.error("未获取到快递单号: " + a.getLogisticsid());
                    }
                }

            });
            List<OrderEntity> orders = orderDAO.batchCreateOrUpdate(ods);
            if (ValideHelper.isNullOrEmpty(orders)) {
                logger.info("-------------------更新失败----------------------");
                rmodel.message = "更新失败";
                return rmodel;
            }
        }
        rmodel.statusCode = StatusCode.SUCCESS;
        rmodel.message = "更新成功";
        return rmodel;
    }

    @Override
    public ResponseModel getExpress(String expressId, String type) {
        ResponseModel rmodel = new ResponseModel();
        rmodel.statusCode = StatusCode.FAIL;
        if (ValideHelper.isNullOrEmpty(expressId)) {
            rmodel.message = "快递单号不能为空";
            return rmodel;
        }
        HttpProxy proxy = HttpProxy.Instance("express");
        proxy.ps.setParam(new HashMap<String, Object>() {{
            put("${number}", expressId);
        }});
        if (ValideHelper.isNullOrEmpty(type)) {
            proxy.ps.setParam(new HashMap<String, Object>() {{
                put("${auto}", "auto");
            }});
        } else {
            proxy.ps.setParam(new HashMap<String, Object>() {{
                put("${auto}", type);
            }});
        }
        proxy.ps.setHead(new HashMap<String, String>() {{
            put("Authorization", "APPCODE ea41224da57b4b5880303e7213a5bcb4");
        }});
        ApiResponseModel model = proxy.DoRequest();
        ObjectMapper mapper = new ObjectMapper();

        JsonNode object = null;
        try {
            object = mapper.readValue((String) model.data, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        if (object.get("status") == null) {
            rmodel.message = "获取失败";
            return rmodel;
        } else if (object.get("status").asInt() != 201) {
            rmodel.message = "快递单号为空";
            return rmodel;
        } else if (object.get("status").asInt() != 202) {
            rmodel.message = "快递公司为空";
            return rmodel;
        } else if (object.get("status").asInt() != 203) {
            rmodel.message = "快递公司不存在";
            return rmodel;
        } else if (object.get("status").asInt() != 204) {
            rmodel.message = "快递公司识别失败";
            return rmodel;
        } else if (object.get("status").asInt() != 205) {
            rmodel.message = "没有信息";
            return rmodel;
        }
        rmodel.message = "查询成功";

        try {
            rmodel.data = mapper.readValue(object.get("result").asText(), ExpressModel.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rmodel;
    }

    @Override
    public ResponseModel getExpressCompany() {
        HttpProxy proxy = HttpProxy.Instance("expresscompany");
        ApiResponseModel model = proxy.DoRequest();
        ResponseModel rmodel = new ResponseModel();

        JsonNode object = null;
        try {
            object = new ObjectMapper().readValue((String) model.data, JsonNode.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        rmodel.statusCode = StatusCode.FAIL;

        if (object.get("status") == null || object.get("status").asInt() != 0) {
            rmodel.message = "获取失败";
            return rmodel;
        }
        rmodel.message = "查询成功";
        rmodel.data = object.get("result").asText();
        return rmodel;
    }


}
