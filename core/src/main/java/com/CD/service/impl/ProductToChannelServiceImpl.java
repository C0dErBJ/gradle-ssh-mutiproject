package com.CD.service.impl;

import com.CD.constant.DataValidityConstant;
import com.CD.constant.StatusCode;
import com.CD.dao.ChannelDAO;
import com.CD.dao.ProductDAO;
import com.CD.dao.ProductToChannelDAO;
import com.CD.db.PageDTO;
import com.CD.entity.ProducttochannelEntity;
import com.CD.entity.api.PCViewModel;
import com.CD.entity.api.ProductViewModel;
import com.CD.entity.api.ResponseModel;
import com.CD.service.ProductToChannelService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Created by C0dEr on 2016/12/5.
 */
@Transactional
@Service
public class ProductToChannelServiceImpl extends BaseServiceImpl<ProducttochannelEntity> implements ProductToChannelService {
    @Resource
    ProductDAO productDAO;
    @Resource
    ChannelDAO channelDAO;
    @Resource
    ProductToChannelDAO productToChannelDAO;

    @Override
    public ResponseModel getAllRelation(List<Long> ProductId) {
        List<ProducttochannelEntity> producttochannelEntities = new ArrayList<>();
        String in = " AND productid IN(" + ProductId.stream().map(a -> a.toString()).collect(Collectors.joining(",")) + ")";
        this.channelDAO.getJdbcTemplate().query("SELECT\n" +
                "  producttochannel.id,\n" +
                "  producttochannel.channelid,\n" +
                "  producttochannel.productid\n" +
                "FROM producttochannel\n" +
                "WHERE status = 0\n" + in +
                "ORDER BY id DESC;", rs -> {
            ProducttochannelEntity ce = new ProducttochannelEntity();
            ce.setId(rs.getLong("id"));
            ce.setChannelid(rs.getLong("channelid"));
            ce.setProductid(rs.getLong("productid"));
            producttochannelEntities.add(ce);
        });
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        model.data = producttochannelEntities;
        return model;
    }

    @Override
    public ResponseModel getProduct(final int pageIndex, final int pageSize) {
        List<Map<String, Object>> productEntities = new ArrayList<>();
        String countsql = "SELECT count(*) AS totalCount FROM product";
        int[] count = new int[1];
        this.productDAO.getJdbcTemplate().query(countsql, rs -> {
            count[0] = rs.getInt("totalCount");
        });
        this.productDAO.getJdbcTemplate().query("SELECT\n" +
                "  productname,\n" +
                "  producttype,\n" +
                "  fnumber,\n" +
                "  id\n" +
                "FROM product\n" +
                "WHERE  status = 0\n" +
                "ORDER BY id DESC LIMIT ?,?;", ps -> {
            ps.setInt(1, (pageIndex - 1) * pageSize);
            ps.setInt(2, pageSize);

        }, rs -> {
            Map<String, Object> pe = new HashMap<>();
            pe.put("id", rs.getLong("id"));
            pe.put("productName", rs.getString("productname"));
            pe.put("productType", rs.getString("producttype"));
            pe.put("fnumber", rs.getString("fnumber"));
            productEntities.add(pe);
        });
        PageDTO<ProductViewModel> pvm = new PageDTO<>(pageSize, pageIndex, count[0], productEntities);
        ResponseModel model = new ResponseModel();
        model.message = "查询成功";
        model.data = pvm;
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel getAllChannel() {
        List<Map<String, Object>> channelEntities = new ArrayList<>();
        this.channelDAO.getJdbcTemplate().query("SELECT\n" +
                "  channel.id,\n" +
                "  channel.`key`,\n" +
                "  channel.channelname\n" +
                "FROM channel\n" +
                "WHERE status = 0\n" +
                "ORDER BY id DESC;", rs -> {
            Map<String, Object> ce = new HashMap<>();
            ce.put("id", rs.getLong("id"));
            ce.put("key", rs.getString("key"));
            ce.put("channelname", rs.getString("channelname"));
            channelEntities.add(ce);
        });
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        model.message = "查询成功";
        model.data = channelEntities;
        return model;
    }

    @Override
    public ResponseModel setRelation(List<PCViewModel> keyValue) {

        for (PCViewModel map : keyValue) {
            ProducttochannelEntity entity = new ProducttochannelEntity();
            entity.setProductid(map.pid);
            entity.setChannelid(map.cid);
            entity.setUpdatetime(new Timestamp(new Date().getTime()));
            entity.setCreatetime(new Timestamp(new Date().getTime()));
            entity.setStatus(DataValidityConstant.INUSE);
            this.productToChannelDAO.makePersistent(entity);
        }
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.SUCCESS;
        model.message = "新增成功";
        return model;
    }


}
