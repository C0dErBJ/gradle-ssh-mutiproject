package com.CD.service;

import com.CD.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

@Service("BaseService")
public class CommBaseService extends BaseServiceImpl<Object> implements
        BaseService<Object> {

}
