package com.CD.entity;

import com.CD.db.BaseDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/15.
 */
@Entity
@Table(name = "purchaserecorddetail", schema = "qjz", catalog = "")
public class PurchaserecorddetailEntity extends BaseDTO {
    private Long id;
    private Long pid;
    private String coid;
    private Integer num;
    private Double price;
    private Integer tokennum;
    private Timestamp cotime;
    private Timestamp createtime;
    private Integer createby;
    private Integer updateby;
    private Timestamp updatetime;
    private Integer status;

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "pid")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "coid")
    public String getCoid() {
        return coid;
    }

    public void setCoid(String coid) {
        this.coid = coid;
    }

    @Basic
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
    }

    @Basic
    @Column(name = "price")
    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Basic
    @Column(name = "tokennum")
    public Integer getTokennum() {
        return tokennum;
    }

    public void setTokennum(Integer tokennum) {
        this.tokennum = tokennum;
    }

    @Basic
    @Column(name = "cotime")
    public Timestamp getCotime() {
        return cotime;
    }

    public void setCotime(Timestamp cotime) {
        this.cotime = cotime;
    }

    @Basic
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
    }

    @Basic
    @Column(name = "createby")
    public Integer getCreateby() {
        return createby;
    }

    public void setCreateby(Integer createby) {
        this.createby = createby;
    }

    @Basic
    @Column(name = "updateby")
    public Integer getUpdateby() {
        return updateby;
    }

    public void setUpdateby(Integer updateby) {
        this.updateby = updateby;
    }

    @Basic
    @Column(name = "updatetime")
    public Timestamp getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Timestamp updatetime) {
        this.updatetime = updatetime;
    }

    @Basic
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        PurchaserecorddetailEntity that = (PurchaserecorddetailEntity) o;

        if (id != that.id) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (coid != null ? !coid.equals(that.coid) : that.coid != null) return false;
        if (num != null ? !num.equals(that.num) : that.num != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (tokennum != null ? !tokennum.equals(that.tokennum) : that.tokennum != null) return false;
        if (cotime != null ? !cotime.equals(that.cotime) : that.cotime != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createby != null ? !createby.equals(that.createby) : that.createby != null) return false;
        if (updateby != null ? !updateby.equals(that.updateby) : that.updateby != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (coid != null ? coid.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (tokennum != null ? tokennum.hashCode() : 0);
        result = 31 * result + (cotime != null ? cotime.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createby != null ? createby.hashCode() : 0);
        result = 31 * result + (updateby != null ? updateby.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
