package com.ZP.dao;

import com.ZP.db.BaseDAO;
import com.ZP.entity.AreaEntity;
import com.ZP.entity.CityEntity;
import com.ZP.entity.ProvinceEntity;
import com.ZP.entity.api.ExpressModel;
import com.ZP.util.ValideHelper;
import org.springframework.stereotype.Repository;

import java.sql.ResultSetMetaData;
import java.util.ArrayList;
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
public class ProvinceDAO extends BaseDAO<ProvinceEntity> {
    public List<ExpressModel> getProvince(String province, String city, String area) {
        String sql = "select province,city,area,expressFee from " + this.getTable(ProvinceEntity.class) + " as  a join " + this.getTable(CityEntity.class) + "  as b on a.provinceID=b.fatherID join " + this.getTable(AreaEntity.class) + " as  c on b.cityID=c.fatherID where 1=1 ";
        if (!ValideHelper.isNullOrEmpty(province)) {
            sql += " and province='" + province + "'";
        }
        if (!ValideHelper.isNullOrEmpty(city)) {
            sql += " and city='" + city + "'";
        }
        if (!ValideHelper.isNullOrEmpty(area)) {
            sql += " and area='" + area + "'";
        }
        final List<ExpressModel> models = new ArrayList<>();
        this.getJdbcTemplate().query(sql, rs -> {
            Map<String, Object> map = new HashMap<String, Object>();
            ResultSetMetaData rmd = rs.getMetaData();
            ExpressModel model = new ExpressModel();
            model.province = rs.getObject(1).toString();
            model.city = rs.getObject(2).toString();
            model.area = rs.getObject(3).toString();
            model.expressFee = (Float) rs.getObject(4);
            models.add(model);
        });
        return models;
    }
}
