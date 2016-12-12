package com.ZP.service;

import com.ZP.entity.InverntoryEntity;
import com.ZP.entity.api.ResponseModel;

/**
 * Created by GhostRain on 2016/11/16.
 */
public interface InverntoryService extends BaseService<InverntoryEntity> {
    ResponseModel addInverntory(String cid, Long pid);

    ResponseModel getInverntoryList(String cid);

    ResponseModel changeInverntory(String cid, Long pid, int salfnum);
}
