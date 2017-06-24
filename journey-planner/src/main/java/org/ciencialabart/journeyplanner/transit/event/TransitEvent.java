package org.ciencialabart.journeyplanner.transit.event;

import java.time.OffsetDateTime;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

import org.ciencialabart.journeyplanner.place.Place;

@Embeddable
public class TransitEvent {

    @ManyToOne
    private Place place;
    
    private OffsetDateTime time;

    public TransitEvent() {}
    
    public TransitEvent(Place place, OffsetDateTime time) {
        this.place = place;
        this.time = time;
    }

    public Place getPlace() {
        return place;
    }

    public OffsetDateTime getTime() {
        return time;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof TransitEvent) {
            TransitEvent otherTransitEvent = (TransitEvent) other;
            
            return place.equals(otherTransitEvent.place) &&
                    time.equals(otherTransitEvent.time);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = place.hashCode();
        
        result = 31 * result + time.hashCode();
        
        return result;
    }

}
