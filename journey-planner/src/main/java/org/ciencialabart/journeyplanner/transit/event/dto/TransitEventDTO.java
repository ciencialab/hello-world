package org.ciencialabart.journeyplanner.transit.event.dto;

import java.time.OffsetDateTime;

import javax.validation.constraints.NotNull;

import org.ciencialabart.journeyplanner.binding.OffsetDateTimeFromStringDeserializer;
import org.ciencialabart.journeyplanner.binding.OffsetDateTimeToStringSerializer;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;

public class TransitEventDTO {

    private long placeId;
    
    @NotNull
    @JsonDeserialize(using = OffsetDateTimeFromStringDeserializer.class)
    @JsonSerialize(using = OffsetDateTimeToStringSerializer.class)
    private OffsetDateTime time;

    public TransitEventDTO() {}
    
    public TransitEventDTO(long placeId, OffsetDateTime time) {
        this.placeId = placeId;
        this.time = time;
    }

    public long getPlaceId() {
        return placeId;
    }

    public OffsetDateTime getTime() {
        return time;
    }
    
    @Override
    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (other instanceof TransitEventDTO) {
            TransitEventDTO otherTransitEventDTO = (TransitEventDTO) other;
            
            return placeId == otherTransitEventDTO.placeId &&
                    time.equals(otherTransitEventDTO.time);
        }
        
        return false;
    }
    
    @Override
    public int hashCode() {
        int result = (int) placeId;
        
        result = 31 * result + time.hashCode();
        
        return result;
    }

}
