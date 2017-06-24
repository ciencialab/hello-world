package org.ciencialabart.journeyplanner.place;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

import org.ciencialabart.journeyplanner.transit.Transit;

@Entity
public class Place {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    private String name = "";
    
    @OneToMany(mappedBy = "departureEvent.place", cascade = CascadeType.ALL)
    private Set<Transit> departureTransits = new HashSet<>();
    
    @OneToMany(mappedBy = "arrivalEvent.place", cascade = CascadeType.ALL)
    private Set<Transit> arrivalTransits = new HashSet<>();
    
    public Place() {}

    public Place(long id) {
        this.id = id;
    }

    public Place(String name) {
        this.name = name;
    }

    public Place(long id, String name) {
        this(name);
        
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Set<Transit> getDepartureTransits() {
        return departureTransits;
    }

    public Set<Transit> getArrivalTransits() {
        return arrivalTransits;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Place) {
            Place otherPlace = (Place) other;
            
            if (id == null) {
                return name.equals(otherPlace.name);
            } else {
                return id.equals(otherPlace.id);
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
