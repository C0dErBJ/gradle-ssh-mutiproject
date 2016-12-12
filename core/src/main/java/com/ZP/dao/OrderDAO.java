package com.ZP.dao;

import com.ZP.db.BaseDAO;
import com.ZP.entity.OrderEntity;
import org.springframework.stereotype.Repository;

import javax.persistence.Query;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.dao
 * User: C0dEr
 * Date: 2016-11-19
 * Time: 12:08
 * Description:
 */
@Repository
public class OrderDAO extends BaseDAO<OrderEntity> {
    public List<OrderEntity> getOrderWithPageNotEqStatus(int pageSize, int pageIndex, String expressStatus) {
        String sql = "from " + OrderEntity.class.getName() + " where logistics!=:expressStatus limit :start,:size";
        Query q = this.buildQuery(OrderEntity.class, sql);
        q.setParameter("expressStatus", expressStatus);
        q.setParameter("start", pageIndex - 1);
        q.setParameter("size", pageSize);
        return q.getResultList();
    }

    public int getCountByneqExpress(String expressStatus) {
        String sql = "SELECT COUNT(*) FROM " + this.getTable(OrderEntity.class) + " where logistics!=:expressStatus";
        Map<String, String> paramaters = new HashMap<>();
        paramaters.put("expressStatus", expressStatus);
        return this.getNamedParameterJdbcTemplate().queryForObject(sql, paramaters, Integer.class);
    }
}
