package com.ZP.service.impl;

import com.ZP.constant.DataValidityConstant;
import com.ZP.constant.ProductConstant;
import com.ZP.constant.StatusCode;
import com.ZP.dao.ProductDAO;
import com.ZP.db.PageDTO;
import com.ZP.entity.ProductEntity;
import com.ZP.entity.api.ProductViewModel;
import com.ZP.entity.api.ResponseModel;
import com.ZP.service.ProductService;
import com.ZP.util.ValideHelper;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.service.impl
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 10:43
 * Description:
 */
@Service
@Transactional
public class ProductServiceImpl extends BaseServiceImpl<ProductEntity> implements ProductService {
    @Resource
    ProductDAO productDAO;

    @Override
    public ResponseModel getProductList(int pageIndex, int pageSize, String productType, String keyword) {
        ResponseModel model = new ResponseModel();
        PageDTO<ProductEntity> productEntityList = productDAO.findForPageAndOrderbyWithExpression(ProductEntity.class, new HashMap<String, Object>() {{
            {
                if (!ValideHelper.isNullOrEmpty(productType)) {
                    put("producttype", "='" + productType + "'");
                }
                put("status", "=" + DataValidityConstant.INUSE);
                if (!ValideHelper.isNullOrEmpty(keyword)) {
                    put(" ", "(productname like '%" + keyword + "%'" + " OR " + " `describe` " + " like '%" + keyword + "%' OR " + " producttype " + "like '%" + keyword + "%')");
                }
            }
        }}, pageSize, pageIndex, "createtime", "desc");
        model.data = productEntityList;
        model.message = "查询成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel getProductById(Long id) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<ProductEntity> productEntityList = productDAO.findByFields(ProductEntity.class, new HashMap<String, Object>() {
            {
                put("id", id);
                put("status", DataValidityConstant.INUSE);
            }
        });
        if (productEntityList.size() > 1) {
            model.message = "数据异常";
            return model;
        }
        if (ValideHelper.isNullOrEmpty(productEntityList)) {
            model.data = new ArrayList<ProductEntity>();
        } else {
            List<ProductViewModel> viewmodel = new ArrayList<>();
            productEntityList.forEach(a -> viewmodel.add(new ProductViewModel() {
                {
                    id = a.getId();
                    name = a.getProductname();
                    type = a.getProducttype();
                    price = a.getPrice();
                    pic = a.getPicfile();
                    unit = "瓶";
                    description = a.getDescribe();
                    isNew = a.getIsNew() == ProductConstant.NEW;
                    kpid = a.getFnumber();
                    fitemid = a.getPid().intValue();
                    fmodel = a.getFmodel();
                    fsupplyname = a.getFsupplyname();
                    fsourceaddress = a.getFsourceaddress();
                    fproductarea = a.getFproductarea();
                }
            }));
            model.data = viewmodel.get(0);
        }
        model.message = "查询成功";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }

    @Override
    public ResponseModel getProductType() {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        List<String> types = new ArrayList<>();
        productDAO.getJdbcTemplate().query("SELECT DISTINCT producttype FROM product", new RowCallbackHandler() {
            @Override
            public void processRow(ResultSet rs) throws SQLException {
                types.add(rs.getString("producttype") == null ? "其它" : rs.getString("producttype"));
            }
        });
        model.message = "查询成功";
        model.statusCode = StatusCode.SUCCESS;
        model.data = types;
        return model;
    }
}
