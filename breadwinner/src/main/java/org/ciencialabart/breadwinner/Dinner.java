package org.ciencialabart.breadwinner;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.apache.commons.beanutils.BeanUtils;

@Entity
@Table
public class Dinner implements Serializable, Cloneable {

    @Id
    @Temporal(TemporalType.DATE)
    private Date dinnerDate;
    @ManyToOne(optional=false)
    @JoinColumn(name="soup", referencedColumnName = "id")
    private DinnerComponent soup;
    @ManyToOne(optional=false)
    @JoinColumn(name="meat", referencedColumnName = "id")
    private DinnerComponent meat;
    @ManyToOne(optional=false)
    @JoinColumn(name="starch", referencedColumnName = "id")
    private DinnerComponent starch;
    @ManyToOne(optional=false)
    @JoinColumn(name="vegetables", referencedColumnName = "id")
    private DinnerComponent vegetables;
    
    public Date getDinnerDate() {
        return dinnerDate;
    }

    public void setDinnerDate(Date dinnerDate) {
        this.dinnerDate = dinnerDate;
    }

    public DinnerComponent getSoup() {
        return soup;
    }

    public void setSoup(DinnerComponent soup) {
        this.soup = soup;
    }

    public DinnerComponent getMeat() {
        return meat;
    }

    public void setMeat(DinnerComponent meat) {
        this.meat = meat;
    }

    public DinnerComponent getStarch() {
        return starch;
    }

    public void setStarch(DinnerComponent starch) {
        this.starch = starch;
    }

    public DinnerComponent getVegetables() {
        return vegetables;
    }

    public void setVegetables(DinnerComponent vegetables) {
        this.vegetables = vegetables;
    }

    @Override
    public Dinner clone() throws CloneNotSupportedException {
        try {
            return (Dinner) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return "Dinner{"
                + ", soup=" + soup
                + ", meat=" + meat 
                + ", starch=" + starch 
                + ", vegetables=" + vegetables 
                + ", date=" + dinnerDate 
                + '}';
    }

}
