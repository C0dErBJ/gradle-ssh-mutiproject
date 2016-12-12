package com.CD.service;

import com.CD.db.PageDTO;

import java.util.List;
import java.util.Map;


/**
 * @param <T>
 * @author HuangJian
 * @date 2013-8-1
 */
public interface BaseService<T> {

    public T create(T entity);

    public T update(T entity);

    public void remove(Class<T> clazz, Long id);

    public T find(Class clazz, Long id);

    public PageDTO find(Class clazz, Map<String, Object> param, int pageSize, int pageNo);

    public List<T> findAll(Class<T> clazz);

    public PageDTO findForPageAndOrderby(Class clazz, Map<String, Object> param, int pageSize, int pageNo, String field, String order);

    public List<T> findByParams(Class<T> clazz, Map<String, Object> params);
}
