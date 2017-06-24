package org.ciencialabart.journeyplanner.transit.event.dto;

import org.ciencialabart.journeyplanner.transit.event.TransitEvent;
import org.springframework.stereotype.Service;

@Service
public class TransitEventDTODefaultAssembler implements TransitEventDTOAssembler {

    @Override
    public TransitEventDTO assemble(TransitEvent transitEvent) {
        return new TransitEventDTO(
                transitEvent.getPlace().getId(),
                transitEvent.getTime());
    }

}
