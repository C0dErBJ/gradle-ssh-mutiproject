package com.ZP.service.impl;

import com.ZP.db.BaseDAO;
import com.ZP.db.PageDTO;
import com.ZP.service.BaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;


/**
 * @param <T>
 * @author HuangJian
 */
@Service
public abstract class BaseServiceImpl<T> implements BaseService<T> {

    protected Log log = LogFactory.getLog(this.getClass());

    @Resource(name = "BaseDAO")
    protected BaseDAO dao;

    @Override
    public T create(T entity) {
        return (T) dao.makePersistent(entity);
    }

    @Override
    public T update(T entity) {
        return (T) dao.makePersistent(entity);
    }

    @Override
    public void remove(Class<T> clazz, Long id) {
        T entity = (T) dao.findById(clazz, id, true);
        dao.makeTransient(entity);
    }

    @Override
    public T find(Class clazz, Long id) {
        return (T) dao.findById(clazz, id, false);
    }

    @Override
    public PageDTO find(Class clazz, Map<String, Object> param, int pageSize, int pageNo) {

        return dao.findForPage(clazz, param, pageSize, pageNo);
    }

    @Override
    public List<T> findAll(Class<T> clazz) {
        return dao.findAll(clazz);
    }

    @Override
    public PageDTO findForPageAndOrderby(Class clazz, Map<String, Object> param, int pageSize, int pageNo, String field, String order) {

        return dao.findForPageAndOrderby(clazz, param, pageSize, pageNo, field, order);
    }

    @Override
    public List<T> findByParams(Class<T> clazz, Map<String, Object> params) {

        return dao.findByFields(clazz, params);
    }

}
