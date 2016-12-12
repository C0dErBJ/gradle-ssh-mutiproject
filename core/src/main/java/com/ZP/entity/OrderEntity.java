package com.ZP.entity;

import com.ZP.db.BaseDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/15.
 */
@Entity
@Table(name = "`order`", schema = "qjz", catalog = "")
public class OrderEntity extends BaseDTO {
    private Long id;
    private String oid;
    private String cid;
    private String kiskey;
    private String logisticsid;
    private Float logistics;
    private Timestamp placetime;
    private Timestamp confirmtime;
    private String address;
    private String province;
    private String city;
    private Double distributionprice;
    private String orderstauts;
    private Integer ifexpired;
    private Integer ifexpireddistributed;
    private Double allprice;
    private Integer logisticsprice;
    private Timestamp createtime;
    private Integer createby;
    private Integer updateby;
    private Timestamp updatetime;
    private Integer status;
    private Long userid;
    private String phone;
    private String customername;
    private String expressstatus;
    private String expresscompany;
    private Long forderid;


    @Basic
    @Column(name = "phone")
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Basic
    @Column(name = "expressstatus")
    public String getExpressstatus() {
        return expressstatus;
    }

    public void setExpressstatus(String expressstatus) {
        this.expressstatus = expressstatus;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Basic
    @Column(name = "forderid")
    public Long getForderid() {
        return forderid;
    }

    public void setForderid(Long forderid) {
        this.forderid = forderid;
    }

    @Basic
    @Column(name = "expresscompany")
    public String getExpresscompany() {
        return expresscompany;
    }

    public void setExpresscompany(String expresscompany) {
        this.expresscompany = expresscompany;
    }

    @Basic
    @Column(name = "userid")
    public Long getUserid() {
        return userid;
    }

    public void setUserid(Long userid) {
        this.userid = userid;
    }

    @Basic
    @Column(name = "customername")
    public String getCustomername() {
        return customername;
    }

    public void setCustomername(String customername) {
        this.customername = customername;
    }

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
    @Column(name = "oid")
    public String getOid() {
        return oid;
    }

    public void setOid(String oid) {
        this.oid = oid;
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
    @Column(name = "kiskey")
    public String getKiskey() {
        return kiskey;
    }

    public void setKiskey(String kiskey) {
        this.kiskey = kiskey;
    }

    @Basic
    @Column(name = "logisticsid")
    public String getLogisticsid() {
        return logisticsid;
    }

    public void setLogisticsid(String logisticsid) {
        this.logisticsid = logisticsid;
    }

    @Basic
    @Column(name = "logistics")
    public Float getLogistics() {
        return logistics;
    }

    public void setLogistics(Float logistics) {
        this.logistics = logistics;
    }

    @Basic
    @Column(name = "placetime")
    public Timestamp getPlacetime() {
        return placetime;
    }

    public void setPlacetime(Timestamp placetime) {
        this.placetime = placetime;
    }

    @Basic
    @Column(name = "confirmtime")
    public Timestamp getConfirmtime() {
        return confirmtime;
    }

    public void setConfirmtime(Timestamp confirmtime) {
        this.confirmtime = confirmtime;
    }

    @Basic
    @Column(name = "address")
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
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
    @Column(name = "city")
    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Basic
    @Column(name = "distributionprice")
    public Double getDistributionprice() {
        return distributionprice;
    }

    public void setDistributionprice(Double distributionprice) {
        this.distributionprice = distributionprice;
    }

    @Basic
    @Column(name = "orderstauts")
    public String getOrderstauts() {
        return orderstauts;
    }

    public void setOrderstauts(String orderstauts) {
        this.orderstauts = orderstauts;
    }

    @Basic
    @Column(name = "ifexpired")
    public Integer getIfexpired() {
        return ifexpired;
    }

    public void setIfexpired(Integer ifexpired) {
        this.ifexpired = ifexpired;
    }

    @Basic
    @Column(name = "ifexpireddistributed")
    public Integer getIfexpireddistributed() {
        return ifexpireddistributed;
    }

    public void setIfexpireddistributed(Integer ifexpireddistributed) {
        this.ifexpireddistributed = ifexpireddistributed;
    }

    @Basic
    @Column(name = "allprice")
    public Double getAllprice() {
        return allprice;
    }

    public void setAllprice(Double allprice) {
        this.allprice = allprice;
    }

    @Basic
    @Column(name = "logisticsprice")
    public Integer getLogisticsprice() {
        return logisticsprice;
    }

    public void setLogisticsprice(Integer logisticsprice) {
        this.logisticsprice = logisticsprice;
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
    @Column(name = "`status`")
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

        OrderEntity that = (OrderEntity) o;

        if (id != that.id) return false;
        if (oid != that.oid) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (kiskey != null ? !kiskey.equals(that.kiskey) : that.kiskey != null) return false;
        if (logisticsid != null ? !logisticsid.equals(that.logisticsid) : that.logisticsid != null) return false;
        if (logistics != null ? !logistics.equals(that.logistics) : that.logistics != null) return false;
        if (placetime != null ? !placetime.equals(that.placetime) : that.placetime != null) return false;
        if (confirmtime != null ? !confirmtime.equals(that.confirmtime) : that.confirmtime != null) return false;
        if (address != null ? !address.equals(that.address) : that.address != null) return false;
        if (province != null ? !province.equals(that.province) : that.province != null) return false;
        if (city != null ? !city.equals(that.city) : that.city != null) return false;
        if (distributionprice != null ? !distributionprice.equals(that.distributionprice) : that.distributionprice != null)
            return false;
        if (orderstauts != null ? !orderstauts.equals(that.orderstauts) : that.orderstauts != null) return false;
        if (ifexpired != null ? !ifexpired.equals(that.ifexpired) : that.ifexpired != null) return false;
        if (ifexpireddistributed != null ? !ifexpireddistributed.equals(that.ifexpireddistributed) : that.ifexpireddistributed != null)
            return false;
        if (allprice != null ? !allprice.equals(that.allprice) : that.allprice != null) return false;
        if (logisticsprice != null ? !logisticsprice.equals(that.logisticsprice) : that.logisticsprice != null)
            return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createby != null ? !createby.equals(that.createby) : that.createby != null) return false;
        if (updateby != null ? !updateby.equals(that.updateby) : that.updateby != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (customername != null ? !customername.equals(that.customername) : that.customername != null) return false;
        if (expresscompany != null ? !expresscompany.equals(that.expresscompany) : that.expresscompany != null)
            return false;
        if (forderid != null ? !forderid.equals(that.customername) : that.customername != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (oid != null ? oid.hashCode() : 0);
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (kiskey != null ? kiskey.hashCode() : 0);
        result = 31 * result + (logisticsid != null ? logisticsid.hashCode() : 0);
        result = 31 * result + (logistics != null ? logistics.hashCode() : 0);
        result = 31 * result + (placetime != null ? placetime.hashCode() : 0);
        result = 31 * result + (confirmtime != null ? confirmtime.hashCode() : 0);
        result = 31 * result + (address != null ? address.hashCode() : 0);
        result = 31 * result + (province != null ? province.hashCode() : 0);
        result = 31 * result + (city != null ? city.hashCode() : 0);
        result = 31 * result + (distributionprice != null ? distributionprice.hashCode() : 0);
        result = 31 * result + (orderstauts != null ? orderstauts.hashCode() : 0);
        result = 31 * result + (ifexpired != null ? ifexpired.hashCode() : 0);
        result = 31 * result + (ifexpireddistributed != null ? ifexpireddistributed.hashCode() : 0);
        result = 31 * result + (allprice != null ? allprice.hashCode() : 0);
        result = 31 * result + (logisticsprice != null ? logisticsprice.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createby != null ? createby.hashCode() : 0);
        result = 31 * result + (updateby != null ? updateby.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (customername != null ? customername.hashCode() : 0);
        result = 31 * result + (expresscompany != null ? expresscompany.hashCode() : 0);
        result = 31 * result + (forderid != null ? forderid.hashCode() : 0);
        return result;
    }
}
