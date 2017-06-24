package org.ciencialabart.journeyplanner.place.dto;

import org.ciencialabart.journeyplanner.place.Place;
import org.springframework.stereotype.Service;

@Service
public class PlaceDTODefaultAssembler implements PlaceDTOAssembler {

    @Override
    public PlaceDTO assemble(Place place) {
        return new PlaceDTO(
                place.getId(),
                place.getName());
    }

}
