package org.ciencialabart.journeyplanner.transit;

import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.event.TransitEventAssembler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransitDefaultAssembler implements TransitAssembler {

    private TransitEventAssembler transitEventAssembler;
    
    @Autowired
    public TransitDefaultAssembler(TransitEventAssembler transitEventAssembler) {
        this.transitEventAssembler = transitEventAssembler;
    }

    @Override
    public Transit assemble(TransitDTO transitDTO) {
        return new Transit(
                transitEventAssembler.assemble(transitDTO.getDepartureEvent()),
                transitEventAssembler.assemble(transitDTO.getArrivalEvent()));
    }

}
