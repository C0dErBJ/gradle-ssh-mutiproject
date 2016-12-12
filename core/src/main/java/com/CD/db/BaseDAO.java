package com.CD.db;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.RowCallbackHandler;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcDaoSupport;
import org.springframework.jdbc.support.rowset.SqlRowSet;
import org.springframework.stereotype.Repository;

import javax.persistence.*;
import javax.sql.DataSource;
import java.io.Serializable;
import java.lang.annotation.Annotation;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

@SuppressWarnings("unchecked")
@Repository("BaseDAO")
public class BaseDAO<T extends BaseDTO> extends NamedParameterJdbcDaoSupport {

    @PersistenceContext()
    protected EntityManager em;

    @Autowired
    public void setDS(DataSource ds) {
        this.setDataSource(ds);
    }

    protected Logger logger = Logger.getLogger(this.getClass());

    public List<T> findAll(Class<?> clazz) {
        return this.buildQuery(clazz, "from " + clazz.getName() + "  where 1=1 ").getResultList();
    }

    public List<T> findByField(Class<?> clazz, String filed, Object value) {
        if (StringUtils.isNumericSpace(filed)) {
            throw new IllegalArgumentException("查询的属性不能包含数字和空格！");
        }
        String jql = "from " + clazz.getName() + "  where 1=1 and " + filed + " = ?1";
        Query q = this.buildQuery(clazz, jql);
        q.setParameter(1, value);

        return q.getResultList();
    }

    public List<T> findByFields(Class<?> clazz, Map<String, Object> map) {
        String jql = "from " + clazz.getName() + " where 1=1 ";
        jql = jql + this.mapToJPQL(map);
        Query q = this.buildQuery(clazz, jql);
        Map<String, Object> m = (Map<String, Object>) map;
        if (map != null) {
            for (String n : m.keySet()) {
                q.setParameter(n, m.get(n));
            }
        }
        return q.getResultList();
    }


    public List<T> findByFieldsWithExpression(Class<?> clazz, Map<String, Object> map) {
        String jql = "from " + clazz.getName() + " where 1=1 ";
        jql = jql + this.mapToJPQLWithExpression(map);
        Query q = this.buildQuery(clazz, jql);
        return q.getResultList();
    }

    public List<T> findByFieldsWithExpressionAndOrderBy(Class<?> clazz, Map<String, Object> map, String orderby, String order) {
        String jql = "from " + clazz.getName() + " where 1=1 ";
        jql = jql + this.mapToJPQLWithExpression(map) + " order by " + "order";
        Query q = this.buildQuery(clazz, jql);
        return q.getResultList();
    }

    public List<T> findByNamedParameter(String jpql, Map<String, Object> paramaters) {
        Query q = this.em.createQuery(jpql);
        for (String n : paramaters.keySet()) {
            q.setParameter(n, paramaters.get(n));
        }
        List<T> result = q.getResultList();
        return result;
    }

    public T findById(Class<?> clazz, Serializable id, boolean lock) {
        Object entity;
        if (lock) {
            entity = em.find(clazz, id);
            em.lock(entity, LockModeType.WRITE);
        } else {
            entity = em.find(clazz, id);
        }
        return (T) entity;
    }

    public List<Map<String, Object>> findForMap(Class<?> clazz, Map<String, Object> map) {

        String table = this.getTable(clazz);
        String condition = this.mapToSql(map, table);
        String sql = "select * from " + table + " where 1=1 " + condition;
        return this.findForMapByNativeSql(this.getTable(clazz), sql);
    }

    public List<T> findByJpql(Class<?> clazz, String jpql, Map<String, Object> m) {
        Query q = this.buildQuery(clazz, jpql);
        if (m != null) {
            for (String n : m.keySet()) {
                q.setParameter(n, m.get(n));
            }
        }
        return q.getResultList();
    }

    public List<Map<String, Object>> findBySql(String sql) {
        return this.findForMapByNativeSql(null, sql);
    }

    public PageDTO<T> findForPage(Class<?> clazz, Map<String, Object> param, int pageSize, int pageNo) {

        String sql = "SELECT * FROM " + getTable(clazz) + " WHERE 1=1 ";
        String countSql = "SELECT COUNT(*) FROM " + getTable(clazz) + " WHERE 1=1 ";
        StringBuffer buffer = new StringBuffer(sql);

        if (param != null) {
            for (String prop : param.keySet()) {
                buffer.append(" AND ");
                buffer.append(prop);
                buffer.append("=:" + prop);
                countSql = countSql + " AND " + prop + "=:" + prop;
            }
        }

        PageDTO<T> page = this.findForPageByNamedParamaters(buffer.toString(), countSql, param, pageSize, pageNo);
        return page;
    }

    public PageDTO<T> findForPageAndOrderby(Class<?> clazz, Map<String, Object> param, int pageSize, int pageNo, String orderby, String order) {

        String sql = "SELECT * FROM " + getTable(clazz) + " WHERE 1=1 ";
        String countSql = "SELECT COUNT(*) FROM " + getTable(clazz) + " WHERE 1=1 ";
        StringBuffer buffer = new StringBuffer(sql);

        if (param != null) {
            for (String prop : param.keySet()) {
                buffer.append(" AND ");
                buffer.append(prop);
                buffer.append("=:" + prop);
                countSql = countSql + " AND " + prop + "=:" + prop;
            }
        }

        if (orderby != null && orderby.equals("") == false && order != null && (order.equals("desc") || order.equals(""))) {

            buffer.append(" order by ").append(orderby).append(" ").append(order);
        }

        PageDTO<T> page = this.findForPageByNamedParamaters(buffer.toString(), countSql, param, pageSize, pageNo);
        return page;
    }

    public PageDTO<T> findForPageAndOrderbyWithExpression(Class<?> clazz, Map<String, Object> param, int pageSize, int pageNo, String orderby, String order) {

        String sql = "SELECT * FROM " + getTable(clazz) + " WHERE 1=1 ";
        final String[] countSql = {"SELECT COUNT(*) FROM " + getTable(clazz) + " WHERE 1=1 "};
        StringBuffer buffer = new StringBuffer(sql);

        if (param != null) {
            param.forEach((k, v) -> {
                buffer.append(" AND ");
                buffer.append(k);
                buffer.append(v);
                countSql[0] = countSql[0] + " AND " + k + v;
            });

        }

        if (orderby != null && orderby.equals("") == false && order != null && (order.equals("desc") || order.equals(""))) {

            buffer.append(" order by ").append(orderby).append(" ").append(order);
        }

        PageDTO<T> page = this.findForPageByNamedParamaters(buffer.toString(), countSql[0], param, pageSize, pageNo);
        return page;
    }

    public PageDTO<T> findForPage(String table, String sql, int pageSize, int pageNo) {

        int start = (pageNo - 1) * pageSize;

        String sql2 = sql.substring(sql.indexOf("FROM"));
        String countSql = "SELECT COUNT(*) SIZE " + sql2;

        logger.debug("SQL: " + countSql);
        SqlRowSet rs = this.getJdbcTemplate().queryForRowSet(countSql);

        rs.first();
        int size = rs.getInt("size");

        String nsql = "(select id " + sql2 + " order by id asc limit " + start + ",1) ";
        if (pageSize == 0) {
            nsql = "select * from (" + sql + ") t where t.id>=" + nsql;
        } else {
            nsql = "select * from (" + sql + ") t where t.id>=" + nsql + " limit " + pageSize;
        }
        List<Map<String, Object>> list = this.findForMapByNativeSql(table, nsql);

        PageDTO<T> result = new PageDTO<T>(pageSize, pageNo, size, list);

        return result;
    }

    public PageDTO<T> findForPageOrderBy(String table, String sql, int pageSize, int pageNo, String orderby, String order) {

        int start = (pageNo - 1) * pageSize;

        String sql2 = sql.substring(sql.indexOf("FROM"));
        String countSql = "SELECT COUNT(*) SIZE " + sql2;

        logger.debug("SQL: " + countSql);
        SqlRowSet rs = this.getJdbcTemplate().queryForRowSet(countSql);

        rs.first();
        int size = rs.getInt("size");

        String nsql = "(select id " + sql2 + " order by " + orderby + " " + order + " limit " + start + ",1) ";
        if (pageSize == 0) {
            nsql = "select * from (" + sql + ") t where t.id>=" + nsql;
        } else {
            nsql = "select * from (" + sql + ") t where t.id>=" + nsql + " limit " + pageSize;
        }
        List<Map<String, Object>> list = this.findForMapByNativeSql(table, nsql);

        PageDTO<T> result = new PageDTO<T>(pageSize, pageNo, size, list);

        return result;
    }

    @SuppressWarnings("deprecation")
    public PageDTO<T> findForPageBySql(String sql, String countSql, Map<String, Object> paramMap, int pageSize, int pageNo) {

        int size = this.getNamedParameterJdbcTemplate().queryForObject(countSql, paramMap, Integer.class);

        int start = (pageNo - 1) * pageSize;
        sql = sql + " limit " + start + "," + pageSize;

        List<Map<String, Object>> data = this.getNamedParameterJdbcTemplate().queryForList(sql, paramMap);
        PageDTO<T> result = new PageDTO<T>(pageSize, pageNo, size, data);
        return result;
    }

    public Object findForSingleResult(Class<?> clazz, Map<String, Object> map) {
        String jql = "FROM " + clazz.getName() + " where 1=1 and delflag= " + 0 + " " + this.mapToJPQL(map);

        Query q = this.buildQuery(clazz, jql);
        Map<String, Object> m = (Map<String, Object>) map;
        if (m != null) {
            for (String n : m.keySet()) {
                q.setParameter(n, m.get(n));
            }
        }
        Object result = null;
        try {
            result = q.getSingleResult();
        } catch (NoResultException e) {
            //查询结果为空，直接返回null
        }
        return result;
    }

    public Object findForSingleResult(Class<?> clazz, String field, Object value) {
        String jql = "from " + clazz.getName() + " where 1=1 ";
        Object result = null;
        if (value instanceof String)
            jql = jql + " and " + field + "='" + value + "'";
        else
            jql = jql + " and " + field + "=" + value;

        try {
            Query q = this.buildQuery(clazz, jql);
            result = q.getSingleResult();
        } catch (NoResultException e) {
            //查询结果为空，直接返回null
            return null;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }

        return result;
    }

    public Object makePersistent(Object entity) {
        return em.merge(entity);
    }

    public void makeTransient(Object entity) {
        if (entity == null)
            return;
        em.remove(entity);
    }

    public int removeAll(Class<?> clazz) {
        Query q = this.buildQuery(clazz, "delete from " + clazz.getName() + " where 1=1 ");
        return q.executeUpdate();
    }

    public int removeByField(Class<?> clazz, String filed, Object value) {
        String jql = "delete from " + clazz.getName() + " where 1=1 and " + filed + " = ?1";

        Query dql = this.buildQuery(clazz, jql);
        dql.setParameter(1, value);
        return dql.executeUpdate();
    }

    public int removeByFields(Class<?> clazz, Map<String, Object> map) {

        String jql = "delete from " + clazz.getName() + " where 1=1 " + this.mapToJPQL(map);
        return em.createQuery(jql).executeUpdate();
    }

    public void executeJql(String jql) {
        this.em.createQuery(jql).executeUpdate();
    }

    public List<T> executeNativeSQL(String sql, Map<String, Object> paramaters, RowMapper<T> mapper) {

        List<T> list = this.getNamedParameterJdbcTemplate().query(sql, paramaters, mapper);
        return list;
    }

    @SuppressWarnings("deprecation")
    public int executeNativeSqlForInt(String sql, Map<String, Object> paramaters) {
        int count = this.getNamedParameterJdbcTemplate().queryForObject(sql, paramaters, Integer.class);
        return count;
    }

    /***
     * Entity有两种编写模式 @Entity（name) 或者 @Table(name)
     * @param entity
     * @return
     */
    public String getTable(Object entity) {
        if (entity.getClass().getAnnotation(Entity.class).name() != null)
            return entity.getClass().getAnnotation(Entity.class).name();
        if (entity.getClass().getAnnotation(Table.class).name() != null)
            return entity.getClass().getAnnotation(Table.class).name();
        return "";
    }

    /***
     * Entity有两种编写模式 @Entity（name) 或者 @Table(name)
     * @param
     * @return
     */
    public String getTable(Class<?> clazz) {
        Annotation ann = clazz.getAnnotation(Table.class);
        if (ann == null) {
            ann = clazz.getAnnotation(Entity.class);
            if (ann == null)
                return null;
            else
                return ((Entity) ann).name();
        }
        return ((Table) ann).name();
    }


    public List<Map<String, Object>> findForMapByNativeSql(String table, String sql) {

        sql = this.buildSQL(table, sql);
        logger.debug("SQL: " + sql);

        final List<Map<String, Object>> results = new LinkedList<Map<String, Object>>();
        this.getJdbcTemplate().query(sql, new RowCallbackHandler() {
            public void processRow(ResultSet rs) throws SQLException {
                Map<String, Object> map = new HashMap<String, Object>();
                ResultSetMetaData rmd = rs.getMetaData();
                int cc = rmd.getColumnCount();
                for (int i = 1; i <= cc; i++) {
                    Object obj = rs.getObject(i);
                    map.put(rmd.getColumnLabel(i), obj == null ? "" : obj);
                }
                results.add(map);
            }
        });

        return results;
    }

    protected String mapToJPQL(Map<String, Object> map) {
        Set<String> props = map.keySet();
        Object value;
        String jql = "";
        for (String key : props) {
            value = map.get(key);
            if (value != null) {
                if (StringUtils.isEmpty(value.toString()))
                    continue;
                jql = jql + " and " + key + "=:" + key + "";
            }
        }
        return jql;
    }


    protected String mapToJPQLWithExpression(Map<String, Object> map) {
        Set<String> props = map.keySet();
        Object value;
        String jql = "";
        for (String key : props) {
            value = map.get(key);
            if (value != null) {
                if (value instanceof String) {
                    if (StringUtils.isEmpty((String) value))
                        continue;
                    jql = jql + " and " + key + value + "'";
                } else {
                    jql = jql + " and " + key + value;
                }
            }
        }
        return jql;
    }

    protected String mapToOrJql(Map<String, Object> map) {
        Set<String> props = map.keySet();
        Object value;
        String jql = "";
        for (String key : props) {
            value = map.get(key);
            if (value != null) {
                if (StringUtils.isEmpty((String) value))
                    continue;
                jql = jql + " or " + key + "=:" + key + "";
            }
        }
        return jql;
    }

    protected String mapToLikeJql(Map<String, Object> map) {
        Set<String> props = map.keySet();
        Object value;
        String jql = "";
        for (String key : props) {
            value = map.get(key);
            if (value != null) {
                if (StringUtils.isEmpty((String) value))
                    continue;
                jql = jql + " or " + key + " like %:" + key + "%";
            }
        }
        return jql;
    }

    protected String mapToSql(Map<String, Object> map, String table) {
        Set<String> props = map.keySet();
        Object value;
        String sql = "";
        for (String key : props) {
            value = map.get(key);
            if (value != null) {
                if (value instanceof String) {
                    if (StringUtils.isEmpty((String) value))
                        continue;
                    sql = sql + " and " + table + "." + key + "='" + value + "'";
                } else
                    sql = sql + " and " + table + "." + key + "=" + value;
            }
        }
        return sql;
    }

    protected String mapToLikeSql(Map<String, Object> map, String table) {
        Set<String> props = map.keySet();
        Object value;
        String sql = "";
        for (String key : props) {
            value = map.get(key);
            if (value != null) {
                if (value instanceof String) {
                    if (StringUtils.isEmpty((String) value))
                        continue;
                    sql = sql + " or " + table + "." + key + " like '%" + value + "%'";
                } else
                    sql = sql + " or " + table + "." + key + "=" + value;
            }
        }
        return sql;
    }

    protected Query buildQuery(Class<?> clazz, String jql) {
        Query q = em.createQuery(jql);
        return q;
    }

    protected String buildSQL(String table, String sql) {
        return sql;
    }

    public PageDTO<T> fuzzyQueryPage(Class<?> clazz, Map<String, Object> map, int start, int limit) {
        String table = this.getTable(clazz);
        String sql = "select * from " + table + " where ";
        String wh = this.mapToLikeSqls(map, this.getTable(clazz));
        if (wh.length() > 0) {
            wh = wh.substring(wh.indexOf("and") + 3);
            sql = sql + wh;
        } else {
            sql = sql + " 1=1";
        }
        return this.findForPage(table, sql, start, limit);
    }

    /**
     * 命名参数的分页查询
     *
     * @param sql
     * @param countSql
     * @param paramaters
     * @param paramaters
     * @param pageSize
     * @param pageNo
     * @return
     */
    @SuppressWarnings("deprecation")
    public PageDTO<T> findForPageByNamedParamaters(String sql, String countSql, Map<String, Object> paramaters, int pageSize, int pageNo) {

        sql = sql + " LIMIT :start,:limit";
        int start = (pageNo - 1) * pageSize;

        if (paramaters == null)
            paramaters = new HashMap<String, Object>();
        paramaters.put("start", start);
        paramaters.put("limit", pageSize);
        Integer size = this.getNamedParameterJdbcTemplate().queryForObject(countSql, paramaters, Integer.class);
        List<Map<String, Object>> resultList = this.getNamedParameterJdbcTemplate().queryForList(sql, paramaters);
        PageDTO<T> result = new PageDTO<T>(pageSize, pageNo, size, resultList);

        return result;
    }

    public List<T> findByGroup(Class<?> clazz, Map<String, Object> map, String groupField) {
        String jql = "from " + clazz.getName() + " where 1=1 " + this.mapToJPQL(map);

        if (!StringUtils.isEmpty(groupField)) {
            jql = jql + " group by " + groupField;
        }
        return this.findByJpql(clazz, jql, map);
    }

    public PageDTO<T> findForExact(Class<?> clazz, Map<String, Object> map, int start, int limit) {
        String table = this.getTable(clazz);
        String sql = "select * from " + table + " where ";
        String wh = this.mapToLikeSqls(map, this.getTable(clazz));
        if (wh.length() > 0) {
            wh = wh.substring(wh.indexOf("and") + 3);
            sql = sql + wh;
        } else {
            sql = sql + " 1=1";
        }
        return this.findForPage(this.getTable(clazz), sql, start, limit);
    }

    protected String mapToLikeSqls(Map<String, Object> map, String table) {
        Set<String> props = map.keySet();
        Object value;
        String sql = "";
        String starDate = "";
        String endDate = "";
        String cloum = "";
        for (String key : props) {
            if (key.indexOf("_") != -1) {
                if (key.indexOf("_") == 0) {
                    starDate = (String) map.get(key);
                    cloum = key.replace("_", "");
                } else {
                    endDate = (String) map.get(key);
                }
                if (starDate != "" && endDate != "") {
                    sql = sql + " and " + table + "." + cloum + " between '" + starDate + "'" + " and '" + endDate + "'";
                }
            } else {
                value = map.get(key);
                if (value != null) {
                    if (value instanceof String) {
                        if (StringUtils.isEmpty((String) value))
                            continue;
                        sql = sql + " and " + table + "." + key + " like '%" + value + "%'";
                    } else
                        sql = sql + " and " + table + "." + key + "=" + value;
                }
            }
        }
        return sql;
    }

    public List<T> findIn(Class<?> clazz, String key, List<Object> values) {
        String jql = "from " + clazz.getName() + " where 1=1 ";
        String startWith = " AND " + key + " IN (";

        String middle = values.stream().map(a -> ":_" + a).collect(Collectors.joining(","));
        String endWith = ")";
        jql = jql + startWith + middle + endWith;
        Query q = this.buildQuery(clazz, jql);
        if (values != null)
            values.forEach(a -> q.setParameter("_" + a, a));
        return q.getResultList();
    }

    public void batchSave(List<T> entities) {
        for (T entity : entities) {
            this.em.persist(entity);
        }
        this.em.flush();
    }

    public void batchDelete(Class clazz, List<Object> ids) {
        for (Object id : ids) {
            em.remove(this.em.find(clazz, id));
        }
        this.em.flush();
    }

    public void save(T entity) {
        this.batchSave(new LinkedList<T>() {{
            add(0, entity);
        }});
    }

    public void delete(Class clazz, Object id) {
        this.batchDelete(clazz, new LinkedList<Object>() {{
            add(0, id);
        }});
    }

    public List<T> batchCreateOrUpdate(List<T> entities) {
        for (T entity : entities) {
            entity = this.em.merge(entity);
        }
        return entities;
    }

    public T createOrUpdate(T entity) {
        return this.batchCreateOrUpdate(new LinkedList<T>() {{
            add(0, entity);
        }}).get(0);
    }

//    public List<T> find(Class<?> clazz) {
////        return this.em
//    }


}
