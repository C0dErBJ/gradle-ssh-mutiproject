package com.CD.entity;

import com.CD.db.BaseDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * Created by Administrator on 2016/11/15.
 */
@Entity
@Table(name = "iocertificate", schema = "qjz", catalog = "")
public class IocertificateEntity extends BaseDTO {
    private Long id;
    private Integer inorout;
    private Integer type;
    private Timestamp dotime;
    private String logisticsid;
    private String cid;
    private Long oid;
    private Long coid;
    private Long pid;
    private Long custid;
    private String comments;
    private String kiskey;
    private Integer num;
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
    @Column(name = "inorout")
    public Integer getInorout() {
        return inorout;
    }

    public void setInorout(Integer inorout) {
        this.inorout = inorout;
    }

    @Basic
    @Column(name = "type")
    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    @Basic
    @Column(name = "dotime")
    public Timestamp getDotime() {
        return dotime;
    }

    public void setDotime(Timestamp dotime) {
        this.dotime = dotime;
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
    @Column(name = "status")
    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
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
    @Column(name = "oid")
    public Long getOid() {
        return oid;
    }

    public void setOid(Long oid) {
        this.oid = oid;
    }

    @Basic
    @Column(name = "coid")
    public Long getCoid() {
        return coid;
    }

    public void setCoid(Long coid) {
        this.coid = coid;
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
    @Column(name = "custid")
    public Long getCustid() {
        return custid;
    }

    public void setCustid(Long custid) {
        this.custid = custid;
    }

    @Basic
    @Column(name = "comments")
    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
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
    @Column(name = "num")
    public Integer getNum() {
        return num;
    }

    public void setNum(Integer num) {
        this.num = num;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        IocertificateEntity that = (IocertificateEntity) o;

        if (id != that.id) return false;
        if (inorout != null ? !inorout.equals(that.inorout) : that.inorout != null) return false;
        if (type != null ? !type.equals(that.type) : that.type != null) return false;
        if (dotime != null ? !dotime.equals(that.dotime) : that.dotime != null) return false;
        if (logisticsid != null ? !logisticsid.equals(that.logisticsid) : that.logisticsid != null) return false;
        if (cid != null ? !cid.equals(that.cid) : that.cid != null) return false;
        if (oid != null ? !oid.equals(that.oid) : that.oid != null) return false;
        if (coid != null ? !coid.equals(that.coid) : that.coid != null) return false;
        if (pid != null ? !pid.equals(that.pid) : that.pid != null) return false;
        if (custid != null ? !custid.equals(that.custid) : that.custid != null) return false;
        if (comments != null ? !comments.equals(that.comments) : that.comments != null) return false;
        if (kiskey != null ? !kiskey.equals(that.kiskey) : that.kiskey != null) return false;
        if (num != null ? !num.equals(that.num) : that.num != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (createby != null ? !createby.equals(that.createby) : that.createby != null) return false;
        if (updateby != null ? !updateby.equals(that.updateby) : that.updateby != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;
        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (inorout != null ? inorout.hashCode() : 0);
        result = 31 * result + (type != null ? type.hashCode() : 0);
        result = 31 * result + (dotime != null ? dotime.hashCode() : 0);
        result = 31 * result + (logisticsid != null ? logisticsid.hashCode() : 0);
        result = 31 * result + (cid != null ? cid.hashCode() : 0);
        result = 31 * result + (oid != null ? oid.hashCode() : 0);
        result = 31 * result + (coid != null ? coid.hashCode() : 0);
        result = 31 * result + (pid != null ? pid.hashCode() : 0);
        result = 31 * result + (custid != null ? custid.hashCode() : 0);
        result = 31 * result + (comments != null ? comments.hashCode() : 0);
        result = 31 * result + (kiskey != null ? kiskey.hashCode() : 0);
        result = 31 * result + (num != null ? num.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (createby != null ? createby.hashCode() : 0);
        result = 31 * result + (updateby != null ? updateby.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        return result;
    }
}
