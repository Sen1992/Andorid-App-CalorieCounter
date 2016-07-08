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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author 王森
 */
@Entity
@Table(name = "consume")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Consume.findAll", query = "SELECT c FROM Consume c"),
    @NamedQuery(name = "Consume.findByUid", query = "SELECT c FROM Consume c WHERE c.consumePK.uid = :uid"),
    @NamedQuery(name = "Consume.findByFid", query = "SELECT c FROM Consume c WHERE c.consumePK.fid = :fid"),
    @NamedQuery(name = "Consume.findByNum", query = "SELECT c FROM Consume c WHERE c.num = :num"),
    @NamedQuery(name = "Consume.findByJoin", query = "SELECT c FROM Consume c JOIN c.user u JOIN c.food f on c.user.uid = u.uid and c.food.fid = f.fid"),
    @NamedQuery(name = "Consume.findByDatetime", query = "SELECT c FROM Consume c WHERE c.consumePK.datetime = :datetime")})
public class Consume implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConsumePK consumePK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "num")
    private int num;
    @JoinColumn(name = "fid", referencedColumnName = "fid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Food food;
    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Consume() {
    }

    public Consume(ConsumePK consumePK) {
        this.consumePK = consumePK;
    }

    public Consume(ConsumePK consumePK, int num) {
        this.consumePK = consumePK;
        this.num = num;
    }

    public Consume(int uid, int fid, Date datetime) {
        this.consumePK = new ConsumePK(uid, fid, datetime);
    }

    public ConsumePK getConsumePK() {
        return consumePK;
    }

    public void setConsumePK(ConsumePK consumePK) {
        this.consumePK = consumePK;
    }

    public int getNum() {
        return num;
    }

    public void setNum(int num) {
        this.num = num;
    }

    public Food getFood() {
        return food;
    }

    public void setFood(Food food) {
        this.food = food;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consumePK != null ? consumePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Consume)) {
            return false;
        }
        Consume other = (Consume) object;
        if ((this.consumePK == null && other.consumePK != null) || (this.consumePK != null && !this.consumePK.equals(other.consumePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fit5183.Consume[ consumePK=" + consumePK + " ]";
    }
    
}
