package com.CD.entity;

import com.CD.db.BaseDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity
 * User: C0dEr
 * Date: 2016-11-11
 * Time: 12:52
 * Description:
 */
@Entity
@Table(name = "product", schema = "qjz", catalog = "")
public class ProductEntity extends BaseDTO {


    private Long id;
    private Long pid;
    private Long channelid;
    private String productname;
    private String describe;
    private String producttype;
    private Double price;
    private Double recommendedprice;
    private String picfile;
    private Timestamp createtime;
    private Integer createby;
    private Integer updateby;
    private Timestamp updatetime;
    private Integer status;
    private Integer isNew;
    private Integer inventory;
    private String fnumber;
    private String fmodel;
    private String fsourceaddress;
    private String fyear;
    private String fgrapes;
    private String fproductarea;
    private String fsupplyname;

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
    @Column(name = "pid")
    public Long getPid() {
        return pid;
    }

    public void setPid(Long pid) {
        this.pid = pid;
    }

    @Basic
    @Column(name = "channelid")
    public Long getChannelid() {
        return channelid;
    }

    public void setChannelid(Long channelid) {
        this.channelid = channelid;
    }

    @Basic
    @Column(name = "productname")
    public String getProductname() {
        return productname;
    }

    public void setProductname(String productname) {
        this.productname = productname;
    }

    @Basic
    @Column(name = "`describe`")
    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe;
    }

    @Basic
    @Column(name = "producttype")
    public String getProducttype() {
        return producttype;
    }

    public void setProducttype(String producttype) {
        this.producttype = producttype;
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
    @Column(name = "recommendedprice")
    public Double getRecommendedprice() {
        return recommendedprice;
    }

    public void setRecommendedprice(Double recommendedprice) {
        this.recommendedprice = recommendedprice;
    }

    @Basic
    @Column(name = "picfile")
    public String getPicfile() {
        return picfile;
    }

    public void setPicfile(String picfile) {
        this.picfile = picfile;
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

    @Basic
    @Column(name = "IsNew")
    public Integer getIsNew() {
        return isNew;
    }

    public void setIsNew(Integer isNew) {
        this.isNew = isNew;
    }

    @Basic
    @Column(name = "inventory")
    public Integer getInventory() {
        return inventory;
    }

    public void setInventory(Integer inventory) {
        this.inventory = inventory;
    }

    @Basic
    @Column(name = "fnumber")
    public String getFnumber() {
        return fnumber;
    }

    public void setFnumber(String fnumber) {
        this.fnumber = fnumber;
    }

    @Basic
    @Column(name = "fmodel")
    public String getFmodel() {
        return fmodel;
    }

    public void setFmodel(String fmodel) {
        this.fmodel = fmodel;
    }

    @Basic
    @Column(name = "fsourceaddress")
    public String getFsourceaddress() {
        return fsourceaddress;
    }

    public void setFsourceaddress(String fsourceaddress) {
        this.fsourceaddress = fsourceaddress;
    }

    @Basic
    @Column(name = "fyear")
    public String getFyear() {
        return fyear;
    }

    public void setFyear(String fyear) {
        this.fyear = fyear;
    }

    @Basic
    @Column(name = "fgrapes")
    public String getFgrapes() {
        return fgrapes;
    }

    public void setFgrapes(String fgrapes) {
        this.fgrapes = fgrapes;
    }

    @Basic
    @Column(name = "fproductarea")
    public String getFproductarea() {
        return fproductarea;
    }

    public void setFproductarea(String fproductarea) {
        this.fproductarea = fproductarea;
    }

    @Basic
    @Column(name = "fsupplyname")
    public String getFsupplyname() {
        return fsupplyname;
    }

    public void setFsupplyname(String fsupplyname) {
        this.fsupplyname = fsupplyname;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ProductEntity that = (ProductEntity) o;

        if (id != that.id) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (channelid != null ? !channelid.equals(that.channelid) : that.channelid != null) return false;
        if (productname != null ? !productname.equals(that.productname) : that.productname != null) return false;
        if (describe != null ? !describe.equals(that.describe) : that.describe != null) return false;
        if (producttype != null ? !producttype.equals(that.producttype) : that.producttype != null) return false;
        if (price != null ? !price.equals(that.price) : that.price != null) return false;
        if (recommendedprice != null ? !recommendedprice.equals(that.recommendedprice) : that.recommendedprice != null)
            return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createby != null ? !createby.equals(that.createby) : that.createby != null) return false;
        if (updateby != null ? !updateby.equals(that.updateby) : that.updateby != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (isNew != null ? !isNew.equals(that.isNew) : that.isNew != null) return false;
        if (inventory != null ? !inventory.equals(that.inventory) : that.inventory != null) return false;
        if (fnumber != null ? !fnumber.equals(that.fnumber) : that.fnumber != null) return false;
        if (fmodel != null ? !fmodel.equals(that.fmodel) : that.fmodel != null) return false;
        if (fsourceaddress != null ? !fsourceaddress.equals(that.fsourceaddress) : that.fsourceaddress != null)
            return false;
        if (fyear != null ? !fyear.equals(that.fyear) : that.fyear != null) return false;
        if (fgrapes != null ? !fgrapes.equals(that.fgrapes) : that.fgrapes != null) return false;
        if (fproductarea != null ? !fproductarea.equals(that.fproductarea) : that.fproductarea != null) return false;
        if (fsupplyname != null ? !fsupplyname.equals(that.fsupplyname) : that.fsupplyname != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (channelid != null ? channelid.hashCode() : 0);
        result = 31 * result + (productname != null ? productname.hashCode() : 0);
        result = 31 * result + (describe != null ? describe.hashCode() : 0);
        result = 31 * result + (producttype != null ? producttype.hashCode() : 0);
        result = 31 * result + (price != null ? price.hashCode() : 0);
        result = 31 * result + (recommendedprice != null ? recommendedprice.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createby != null ? createby.hashCode() : 0);
        result = 31 * result + (updateby != null ? updateby.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (isNew != null ? isNew.hashCode() : 0);
        result = 31 * result + (inventory != null ? inventory.hashCode() : 0);
        result = 31 * result + (fnumber != null ? fnumber.hashCode() : 0);
        result = 31 * result + (fmodel != null ? fmodel.hashCode() : 0);
        result = 31 * result + (fsourceaddress != null ? fsourceaddress.hashCode() : 0);
        result = 31 * result + (fyear != null ? fyear.hashCode() : 0);
        result = 31 * result + (fgrapes != null ? fgrapes.hashCode() : 0);
        result = 31 * result + (fproductarea != null ? fproductarea.hashCode() : 0);
        result = 31 * result + (fsupplyname != null ? fsupplyname.hashCode() : 0);
        return result;
    }
}
