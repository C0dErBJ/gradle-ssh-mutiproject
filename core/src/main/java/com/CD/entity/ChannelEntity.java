package com.CD.entity;

import com.CD.db.BaseDTO;

import javax.persistence.*;
import java.sql.Timestamp;

/**
 * ProjectName: QJZ
 * PackageName: com.CD.entity
 * User: C0dEr
 * Date: 2016-11-30
 * Time: 15:01
 * Description:
 */
@Entity
@Table(name = "channel", schema = "qjz", catalog = "")
public class ChannelEntity extends BaseDTO {
    private Long id;
    private String key;
    private String channelname;
    private Integer status;
    private Timestamp createtime;
    private Timestamp updatetime;
    private Long fitemid;

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
    @Column(name = "`key`")
    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    @Basic
    @Column(name = "channelname")
    public String getChannelname() {
        return channelname;
    }

    public void setChannelname(String channelname) {
        this.channelname = channelname;
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
    @Column(name = "createtime")
    public Timestamp getCreatetime() {
        return createtime;
    }

    public void setCreatetime(Timestamp createtime) {
        this.createtime = createtime;
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
    @Column(name = "fitemid")
    public Long getFitemid() {
        return fitemid;
    }

    public void setFitemid(Long fitemid) {
        this.fitemid = fitemid;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        ChannelEntity that = (ChannelEntity) o;

        if (id != that.id) return false;
        if (key != null ? !key.equals(that.key) : that.key != null) return false;
        if (channelname != null ? !channelname.equals(that.channelname) : that.channelname != null) return false;
        if (status != null ? !status.equals(that.status) : that.status != null) return false;
        if (createtime != null ? !createtime.equals(that.createtime) : that.createtime != null) return false;
        if (updatetime != null ? !updatetime.equals(that.updatetime) : that.updatetime != null) return false;
        if (fitemid != null ? !fitemid.equals(that.fitemid) : that.fitemid != null) return false;

        return true;
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + (key != null ? key.hashCode() : 0);
        result = 31 * result + (channelname != null ? channelname.hashCode() : 0);
        result = 31 * result + (status != null ? status.hashCode() : 0);
        result = 31 * result + (createtime != null ? createtime.hashCode() : 0);
        result = 31 * result + (updatetime != null ? updatetime.hashCode() : 0);
        result = 31 * result + (fitemid != null ? fitemid.hashCode() : 0);
        return result;
    }
}
