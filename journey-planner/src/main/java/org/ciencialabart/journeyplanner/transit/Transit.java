package org.ciencialabart.journeyplanner.transit;

import javax.persistence.AssociationOverride;
import javax.persistence.AttributeOverride;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;

import org.ciencialabart.journeyplanner.transit.event.TransitEvent;

@Entity
public class Transit {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;
    
    @AssociationOverride(name = "place", joinColumns=@JoinColumn(name="source"))
    @AttributeOverride(name = "time", column = @Column(name = "departureTime"))
    private TransitEvent departureEvent;
    
    @AssociationOverride(name = "place", joinColumns=@JoinColumn(name="destination"))
    @AttributeOverride(name = "time", column = @Column(name = "arrivalTime"))
    private TransitEvent arrivalEvent;

    public Transit() {}

    public Transit(long id) {
        this.id = id;
    }

    public Transit(TransitEvent departureEvent, TransitEvent arrivalEvent) {
        this.departureEvent = departureEvent;
        this.arrivalEvent = arrivalEvent;
    }

    public Long getId() {
        return id;
    }

    public TransitEvent getDepartureEvent() {
        return departureEvent;
    }

    public TransitEvent getArrivalEvent() {
        return arrivalEvent;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof Transit) {
            Transit otherTransit = (Transit) other;
            
            if (id == null) {
                return departureEvent.equals(otherTransit.departureEvent) &&
                        arrivalEvent.equals(otherTransit.arrivalEvent);
            } else {
                return id.equals(otherTransit.id);
            }
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        if (id == null) {
            int result = departureEvent.hashCode();
            
            result = 31 * result + arrivalEvent.hashCode();
            
            return result;
        }
        
        return id.hashCode();
    }
    
}
