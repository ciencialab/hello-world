package org.ciencialabart.breadwinner;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import org.apache.commons.beanutils.BeanUtils;

@Entity
@Table
public class DinnerComponent implements Serializable, Cloneable {
    
    @Id
    @GeneratedValue
    private Integer id;
    @Column(nullable=false)
    @Enumerated(EnumType.STRING)
    private DinnerComponentKind kind;
    @Column(nullable=false)
    private String name = "";
    
    public static enum DinnerComponentKind {
        SOUP,
        MEAT,
        STARCH,
        VEGETABLES
    }
    
    public Integer getId() {
        return id;
    }
    
    public void setId(Integer id) {
        this.id = id;
    }
    
    public DinnerComponentKind getKind() {
        return kind;
    }
    
    public void setKind(DinnerComponentKind kind) {
        this.kind = kind;
    }
    
    public String getName() {
        return name;
    }
    
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public DinnerComponent clone() throws CloneNotSupportedException {
        try {
            return (DinnerComponent) BeanUtils.cloneBean(this);
        } catch (Exception ex) {
            throw new CloneNotSupportedException();
        }
    }

    @Override
    public String toString() {
        return name;
    }
    
}
