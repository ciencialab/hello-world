package org.ciencialabart.journeyplanner.transit.dto;

import javax.validation.constraints.NotNull;

import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;

public class TransitDTO {
    
    private Long id;
    
    @NotNull
    private TransitEventDTO departureEvent;
    
    @NotNull
    private TransitEventDTO arrivalEvent;

    public TransitDTO() {}

    public TransitDTO(long id) {
        this.id = id;
    }

    public TransitDTO(TransitEventDTO departureEvent, TransitEventDTO arrivalEvent) {
        this.departureEvent = departureEvent;
        this.arrivalEvent = arrivalEvent;
    }

    public TransitDTO(long id, TransitEventDTO departureEvent, TransitEventDTO arrivalEvent) {
        this(departureEvent, arrivalEvent);
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public TransitEventDTO getDepartureEvent() {
        return departureEvent;
    }

    public TransitEventDTO getArrivalEvent() {
        return arrivalEvent;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof TransitDTO) {
            TransitDTO otherTransitDTO = (TransitDTO) other;
            
            return id == null ? otherTransitDTO.id == null : id.equals(otherTransitDTO.id) &&
                    departureEvent.equals(otherTransitDTO.departureEvent) &&
                    arrivalEvent.equals(otherTransitDTO.arrivalEvent);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = id == null ? 0 : id.hashCode();
        
        result = 31 * result + departureEvent.hashCode();
        result = 31 * result + arrivalEvent.hashCode();
        
        return result;
    }
    
}
