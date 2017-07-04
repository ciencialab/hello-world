package org.ciencialabart.profilesbrowser.city;

import org.ciencialabart.profilesbrowser.city.coordinates.Coordinates;

import com.fasterxml.jackson.annotation.JsonProperty;

public class City {
    
    private String countryName;
    private String cityName;
    private String stateName;
    
    @JsonProperty("coords")
    private Coordinates coordinations;

    public String getCountryName() {
        return countryName;
    }

    public String getCityName() {
        return cityName;
    }

    public String getStateName() {
        return stateName;
    }

    public Coordinates getCoordinations() {
        return coordinations;
    }
    
}
