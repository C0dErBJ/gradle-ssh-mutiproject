package com.CD.service.impl;

import com.CD.constant.DataValidityConstant;
import com.CD.constant.StatusCode;
import com.CD.dao.InverntoryDAO;
import com.CD.dao.ProductDAO;
import com.CD.entity.InverntoryEntity;
import com.CD.entity.ProductEntity;
import com.CD.entity.api.InverntoryViewModel;
import com.CD.entity.api.ResponseModel;
import com.CD.service.InverntoryService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by GhostRain on 2016/11/16.
 */
@Service
@Transactional
public class InverntoryServiceImpl extends BaseServiceImpl<InverntoryEntity> implements InverntoryService {
    @Resource
    InverntoryDAO inverntoryDAO;

    @Resource
    ProductDAO productDAO;

    public ResponseModel addInverntory(String cid, Long pid) {
        ResponseModel model = new ResponseModel();
        model.statusCode = StatusCode.FAIL;
        InverntoryEntity ie = new InverntoryEntity();
        ie.setCid(cid);
        ie.setNum(0);
        ie.setPid(pid);
        ie.setSalfnum(0);
        ie.setCreatetime(new Timestamp(new Date().getTime()));
        ie.setUpdatetime(new Timestamp(new Date().getTime()));
        ie.setStatus(DataValidityConstant.INUSE);
        model.statusCode = StatusCode.SUCCESS;
        inverntoryDAO.makePersistent(ie);
        return model;
    }

    public ResponseModel getInverntoryList(String cid) {
        ResponseModel model = new ResponseModel();
        List<InverntoryViewModel> ivtModels = new ArrayList<>();
        Map map = new HashMap();
        map.put("cid", cid);
        map.put("status", DataValidityConstant.INUSE);
        List<InverntoryEntity> ivtList = inverntoryDAO.findByFields(InverntoryEntity.class, map);
        if (ivtList.size() > 0) {
            for (int i = 0; i < ivtList.size(); i++) {
                InverntoryViewModel ivtModel = new InverntoryViewModel();
                if (ivtList.get(i).getPid() != null) {
                    Long pid = ivtList.get(i).getPid();
                    Map map1 = new HashMap();
                    map1.put("id", new Long(pid));
                    map1.put("status", DataValidityConstant.INUSE);
                    List<ProductEntity> products = productDAO.findByFields(ProductEntity.class, map1);
                    if (products.size() > 0) {
                        ivtModel.setCid(cid);
                        ivtModel.setSalfnum(ivtList.get(i).getSalfnum());
                        ivtModel.setId(ivtList.get(i).getId());
                        ivtModel.setPid(pid);
                        ivtModel.setPrice(products.get(0).getPrice());
                        ivtModel.setProductname(products.get(0).getProductname());
                        ivtModel.setType(products.get(0).getProducttype());
                        ivtModel.pic = products.get(0).getPicfile();
                    }
                    ivtModels.add(ivtModel);
                }
            }
        }
        model.statusCode = StatusCode.SUCCESS;
        model.message = "操作成功";
        model.data = ivtModels;
        return model;
    }

    public ResponseModel changeInverntory(String cid, Long pid, int salfnum) {
        ResponseModel model = new ResponseModel();
        Map map = new HashMap();
        map.put("cid", cid);
        map.put("pid", pid);
        List<InverntoryEntity> list = inverntoryDAO.findByFields(InverntoryEntity.class, map);
        if (list.size() == 0) {
            this.addInverntory(cid, pid);
            list = inverntoryDAO.findByFields(InverntoryEntity.class, map);
        }
        InverntoryEntity inverntoryEntity = list.get(0);
        inverntoryEntity.setSalfnum(inverntoryEntity.getSalfnum() + salfnum);
        inverntoryDAO.makePersistent(inverntoryEntity);
        model.message = "操作成功！";
        model.statusCode = StatusCode.SUCCESS;
        return model;
    }
}
