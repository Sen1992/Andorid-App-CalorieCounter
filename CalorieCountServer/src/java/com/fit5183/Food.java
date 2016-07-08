/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.fit5183;

import java.io.Serializable;
import java.util.Collection;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author 王森
 */
@Entity
@Table(name = "food")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Food.findAll", query = "SELECT f FROM Food f"),
    @NamedQuery(name = "Food.findByFid", query = "SELECT f FROM Food f WHERE f.fid = :fid"),
    @NamedQuery(name = "Food.findByFname", query = "SELECT f FROM Food f WHERE f.fname = :fname"),
    @NamedQuery(name = "Food.findByCalorie", query = "SELECT f FROM Food f WHERE f.calorie = :calorie"),
    @NamedQuery(name = "Food.findByFat", query = "SELECT f FROM Food f WHERE f.fat = :fat"),
    @NamedQuery(name = "Food.findByCategory", query = "SELECT f FROM Food f WHERE f.category = :category"),
    @NamedQuery(name = "Food.findByServing", query = "SELECT f FROM Food f WHERE f.serving = :serving")})
public class Food implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "fid")
    private Integer fid;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 20)
    @Column(name = "fname")
    private String fname;
    @Basic(optional = false)
    @NotNull
    @Column(name = "calorie")
    private int calorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "fat")
    private int fat;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "serving")
    private String serving;
    @Column(name = "category")
    private String category;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "food")
    private Collection<Consume> consumeCollection;

    public Food() {
    }

    public Food(Integer fid) {
        this.fid = fid;
    }

//    public Food(Integer fid, String fname, int calorie, int fat, String serving) {
//        this.fid = fid;
//        this.fname = fname;
//        this.calorie = calorie;
//        this.fat = fat;
//        this.serving = serving;
//    }

    public Food(Integer fid, String fname, int calorie, int fat, String serving, String category, Collection<Consume> consumeCollection) {
        this.fid = fid;
        this.fname = fname;
        this.calorie = calorie;
        this.fat = fat;
        this.serving = serving;
        this.category = category;
        this.consumeCollection = consumeCollection;
    }
    

    public Integer getFid() {
        return fid;
    }

    public void setFid(Integer fid) {
        this.fid = fid;
    }

    public String getFname() {
        return fname;
    }

    public void setFname(String fname) {
        this.fname = fname;
    }

    public int getCalorie() {
        return calorie;
    }

    public void setCalorie(int calorie) {
        this.calorie = calorie;
    }

    public int getFat() {
        return fat;
    }

    public void setFat(int fat) {
        this.fat = fat;
    }

    public String getServing() {
        return serving;
    }

    public void setServing(String serving) {
        this.serving = serving;
    }

    @XmlTransient
    public Collection<Consume> getConsumeCollection() {
        return consumeCollection;
    }

    public void setConsumeCollection(Collection<Consume> consumeCollection) {
        this.consumeCollection = consumeCollection;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fid != null ? fid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Food)) {
            return false;
        }
        Food other = (Food) object;
        if ((this.fid == null && other.fid != null) || (this.fid != null && !this.fid.equals(other.fid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fit5183.Food[ fid=" + fid + " ]";
    }
    
}
