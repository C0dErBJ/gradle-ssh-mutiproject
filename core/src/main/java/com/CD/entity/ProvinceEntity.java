package com.CD.entity;

import com.CD.db.BaseDTO;

import javax.persistence.*;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity
 * User: C0dEr
 * Date: 2016-11-28
 * Time: 17:32
 * Description:
 */
@Entity
@Table(name = "province", schema = "qjz", catalog = "")
public class ProvinceEntity extends BaseDTO {
    private Long id;
    private Long provinceId;
    private String province;
    private Double expressFee;

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
    @Column(name = "provinceID")
    public Long getProvinceId() {
        return provinceId;
    }

    public void setProvinceId(Long provinceId) {
        this.provinceId = provinceId;
    }

    @Basic
    @Column(name = "province")
    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    @Basic
    @Column(name = "ExpressFee")
    public Double getExpressFee() {
        return expressFee;
    }

    public void setExpressFee(Double expressFee) {
        this.expressFee = expressFee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProvinceEntity that = (ProvinceEntity) o;

        if (id != that.id) return false;
        if (provinceId != that.provinceId) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (expressFee != null ? !expressFee.equals(that.expressFee) : that.expressFee != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (provinceId ^ (provinceId >>> 32));
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (expressFee != null ? expressFee.hashCode() : 0);
        return result;
    }
}
