package org.ciencialabart.profilesbrowser.city.coordinates;

import java.math.BigDecimal;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Coordinates {
    
    @JsonProperty("lon")
    private BigDecimal longitude;
    
    @JsonProperty("lat")
    private BigDecimal latitude;

    public BigDecimal getLongitude() {
        return longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }
    
}
