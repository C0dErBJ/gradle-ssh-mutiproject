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
@Table(name = "city", schema = "qjz", catalog = "")
public class CityEntity extends BaseDTO {
    private Long id;
    private Long cityId;
    private String city;

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
    @Column(name = "cityID")
    public Long getCityId() {
        return cityId;
    }

    public void setCityId(Long cityId) {
        this.cityId = cityId;
    }

    @Basic
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        CityEntity that = (CityEntity) o;

        if (id != that.id) return false;
        if (cityId != that.cityId) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (int) (cityId ^ (cityId >>> 32));
        result = 31 * result + (city != null ? city.hashCode() : 0);
        return result;
    }
}
