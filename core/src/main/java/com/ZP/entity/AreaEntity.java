package com.ZP.entity;

import com.ZP.db.BaseDTO;

import javax.persistence.*;

/**
 * ProjectName: QJZ
 * PackageName: com.ZP.entity
 * User: C0dEr
 * Date: 2016-11-28
 * Time: 17:32
 * Description:
 */
@Entity
@Table(name = "area", schema = "qjz", catalog = "")
public class AreaEntity extends BaseDTO {
    private Long id;
    private int areaId;
    private String area;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "areaID")
    public int getAreaId() {
        return areaId;
    }

    public void setAreaId(int areaId) {
        this.areaId = areaId;
    }

    @Basic
    @Column(name = "area")
    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        AreaEntity that = (AreaEntity) o;

        if (id != that.id) return false;
        if (areaId != that.areaId) return false;
        if (area != null ? !area.equals(that.area) : that.area != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + areaId;
        result = 31 * result + (area != null ? area.hashCode() : 0);
        return result;
    }
}
