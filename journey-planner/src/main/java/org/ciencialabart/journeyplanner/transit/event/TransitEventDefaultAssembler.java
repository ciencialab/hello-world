package org.ciencialabart.journeyplanner.transit.event;

import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;
import org.springframework.stereotype.Service;

@Service
public class TransitEventDefaultAssembler implements TransitEventAssembler {

    @Override
    public TransitEvent assemble(TransitEventDTO transitEventDTO) {
        return new TransitEvent(
                new Place(transitEventDTO.getPlaceId()),
                transitEventDTO.getTime());
    }

}
