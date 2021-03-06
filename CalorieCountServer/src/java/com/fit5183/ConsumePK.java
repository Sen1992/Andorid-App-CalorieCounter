/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fit5183;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;

/**
 *
 * @author 王森
 */
@Embeddable
public class ConsumePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "uid")
    private int uid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fid")
    private int fid;
    @Basic(optional = false)
    @NotNull
    @Column(name = "datetime")
    @Temporal(TemporalType.TIMESTAMP)
    private Date datetime;

    public ConsumePK() {
    }

    public ConsumePK(int uid, int fid, Date datetime) {
        this.uid = uid;
        this.fid = fid;
        this.datetime = datetime;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public int getFid() {
        return fid;
    }

    public void setFid(int fid) {
        this.fid = fid;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) uid;
        hash += (int) fid;
        hash += (datetime != null ? datetime.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConsumePK)) {
            return false;
        }
        ConsumePK other = (ConsumePK) object;
        if (this.uid != other.uid) {
            return false;
        }
        if (this.fid != other.fid) {
            return false;
        }
        if ((this.datetime == null && other.datetime != null) || (this.datetime != null && !this.datetime.equals(other.datetime))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fit5183.ConsumePK[ uid=" + uid + ", fid=" + fid + ", datetime=" + datetime + " ]";
    }
    
}
