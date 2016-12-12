package com.CD.entity;

import com.CD.db.BaseDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/15.
 */
@Entity
@Table(name = "customer", schema = "qjz", catalog = "")
public class CustomerEntity extends BaseDTO {
    private Long id;
    private String cid;
    private String customer;
    private String customername;
    private String customersex;
    private String customeraddress;
    private String customerphone;
    private Integer ifvip;
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
    @Column(name = "cid")
    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    @Basic
    @Column(name = "customer")
    public String getCustomer() {
        return customer;
    }

    public void setCustomer(String customer) {
        this.customer = customer;
    }

    @Basic
    @Column(name = "customername")
    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

    @Basic
    @Column(name = "customersex")
    public String getCustomersex() {
        return customersex;
    }

    public void setCustomersex(String customersex) {
        this.customersex = customersex;
    }

    @Basic
    @Column(name = "customeraddress")
    public String getCustomeraddress() {
        return customeraddress;
    }

    public void setCustomeraddress(String customeraddress) {
        this.customeraddress = customeraddress;
    }

    @Basic
    @Column(name = "customerphone")
    public String getCustomerphone() {
        return customerphone;
    }

    public void setCustomerphone(String customerphone) {
        this.customerphone = customerphone;
    }

    @Basic
    @Column(name = "ifvip")
    public Integer getIfvip() {
        return ifvip;
    }

    public void setIfvip(Integer ifvip) {
        this.ifvip = ifvip;
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

        CustomerEntity that = (CustomerEntity) o;

        if (id != that.id) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (customer != null ? !customer.equals(that.customer) : that.customer != null) return false;
        if (customername != null ? !customername.equals(that.customername) : that.customername != null) return false;
        if (customersex != null ? !customersex.equals(that.customersex) : that.customersex != null) return false;
        if (customeraddress != null ? !customeraddress.equals(that.customeraddress) : that.customeraddress != null)
            return false;
        if (customerphone != null ? !customerphone.equals(that.customerphone) : that.customerphone != null)
            return false;
        if (ifvip != null ? !ifvip.equals(that.ifvip) : that.ifvip != null) return false;
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
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (customer != null ? customer.hashCode() : 0);
        result = 31 * result + (customername != null ? customername.hashCode() : 0);
        result = 31 * result + (customersex != null ? customersex.hashCode() : 0);
        result = 31 * result + (customeraddress != null ? customeraddress.hashCode() : 0);
        result = 31 * result + (customerphone != null ? customerphone.hashCode() : 0);
        result = 31 * result + (ifvip != null ? ifvip.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createby != null ? createby.hashCode() : 0);
        result = 31 * result + (updateby != null ? updateby.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
