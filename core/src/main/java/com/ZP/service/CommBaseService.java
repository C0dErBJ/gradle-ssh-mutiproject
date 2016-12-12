package com.ZP.service;

import com.ZP.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("BaseService")
public class CommBaseService extends BaseServiceImpl<Object> implements
        BaseService<Object> {

}
