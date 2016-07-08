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
@Table(name = "report")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Report.findAll", query = "SELECT r FROM Report r"),
    @NamedQuery(name = "Report.findByUid", query = "SELECT r FROM Report r WHERE r.reportPK.uid = :uid"),
    @NamedQuery(name = "Report.findByDatetime", query = "SELECT r FROM Report r WHERE r.reportPK.datetime = :datetime"),
    @NamedQuery(name = "Report.findByTotalCalorie", query = "SELECT r FROM Report r WHERE r.totalCalorie = :totalCalorie"),
    @NamedQuery(name = "Report.findByAimCalorie", query = "SELECT r FROM Report r WHERE r.aimCalorie = :aimCalorie"),
    @NamedQuery(name = "Report.findByRemaining", query = "SELECT r FROM Report r WHERE r.remaining = :remaining"),
    @NamedQuery(name = "Report.findByConsumeCalorie", query = "SELECT r FROM Report r WHERE r.consumeCalorie = :consumeCalorie"),
    @NamedQuery(name = "Report.findByUsernameAndDate", query = "SELECT r.aimCalorie FROM Report r JOIN User u on r.reportPK.uid = u.uid WHERE u.username = :username AND r.reportPK.datetime =:date"),
    @NamedQuery(name = "Report.findByNameAndDatetime", query = "SELECT r FROM Report r JOIN User u on r.reportPK.uid = u.uid WHERE r.reportPK.datetime = :datetime AND u.uname = :uname"),
    @NamedQuery(name = "Report.findByTotalSteps", query = "SELECT r FROM Report r WHERE r.totalSteps = :totalSteps")})
public class Report implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ReportPK reportPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_calorie")
    private double totalCalorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "aim_calorie")
    private double aimCalorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "remaining")
    private double remaining;
    @Basic(optional = false)
    @NotNull
    @Column(name = "consume_calorie")
    private double consumeCalorie;
//    @Basic(optional = false)
//    @NotNull
//    @Column(name = "burned_calorie")
//    private double burnedCalorie;
    @Basic(optional = false)
    @NotNull
    @Column(name = "total_steps")
    private int totalSteps;
    @JoinColumn(name = "uid", referencedColumnName = "uid", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private User user;

    public Report() {
    }

    public Report(ReportPK reportPK) {
        this.reportPK = reportPK;
    }

    public Report(ReportPK reportPK, double totalCalorie, double aimCalorie, double remaining, double consumeCalorie, int totalSteps) {
        this.reportPK = reportPK;
        this.totalCalorie = totalCalorie;
        this.aimCalorie = aimCalorie;
        this.remaining = remaining;
        this.consumeCalorie = consumeCalorie;
//        this.burnedCalorie = burnedCalorie;
        this.totalSteps = totalSteps;
    }

    public Report(int uid, Date datetime) {
        this.reportPK = new ReportPK(uid, datetime);
    }

    public ReportPK getReportPK() {
        return reportPK;
    }

    public void setReportPK(ReportPK reportPK) {
        this.reportPK = reportPK;
    }

    public double getTotalCalorie() {
        return totalCalorie;
    }

    public void setTotalCalorie(double totalCalorie) {
        this.totalCalorie = totalCalorie;
    }

    public double getAimCalorie() {
        return aimCalorie;
    }

    public void setAimCalorie(double aimCalorie) {
        this.aimCalorie = aimCalorie;
    }

    public double getRemaining() {
        return remaining;
    }

    public void setRemaining(double remaining) {
        this.remaining = remaining;
    }

    public double getConsumeCalorie() {
        return consumeCalorie;
    }

    public void setConsumeCalorie(double consumeCalorie) {
        this.consumeCalorie = consumeCalorie;
    }

 /*   public double getBurnedCalorie() {
        return burnedCalorie;
    }

    public void setBurnedCalorie(int burnedCalorie) {
        this.burnedCalorie = burnedCalorie;
    }
*/
    public int getTotalSteps() {
        return totalSteps;
    }

    public void setTotalSteps(int totalSteps) {
        this.totalSteps = totalSteps;
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
        hash += (reportPK != null ? reportPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Report)) {
            return false;
        }
        Report other = (Report) object;
        if ((this.reportPK == null && other.reportPK != null) || (this.reportPK != null && !this.reportPK.equals(other.reportPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "com.fit5183.Report[ reportPK=" + reportPK + " ]";
    }
    
}
