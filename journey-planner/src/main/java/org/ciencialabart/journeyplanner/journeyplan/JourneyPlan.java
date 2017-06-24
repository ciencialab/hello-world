package org.ciencialabart.journeyplanner.journeyplan;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class JourneyPlan {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String name = "";

    public JourneyPlan() {}

    public JourneyPlan(String name) {
        this.name = name;
    }

    public JourneyPlan(long id, String name) {
        this(name);
        
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof JourneyPlan) {
            JourneyPlan otherJourneyPlan = (JourneyPlan) other;
            
            if (id == null) {
                return name.equals(otherJourneyPlan.name);
            } else {
                return id.equals(otherJourneyPlan.id);
            }
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        if (id == null) {
            return name.hashCode();
        }
        
        return id.hashCode();
    }
    
}
