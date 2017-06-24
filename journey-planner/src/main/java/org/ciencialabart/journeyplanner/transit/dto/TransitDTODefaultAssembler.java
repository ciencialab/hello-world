package org.ciencialabart.journeyplanner.transit.dto;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTOAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitDTODefaultAssembler implements TransitDTOAssembler {

    private TransitEventDTOAssembler transitEventDTOAssembler;

    @Autowired
    public TransitDTODefaultAssembler(TransitEventDTOAssembler transitEventDTOAssembler) {
        this.transitEventDTOAssembler = transitEventDTOAssembler;
    }

    @Override
    public TransitDTO assemble(Transit transit) {
        return new TransitDTO(transit.getId(),
                transitEventDTOAssembler.assemble(transit.getDepartureEvent()),
                transitEventDTOAssembler.assemble(transit.getArrivalEvent()));
    }

}
