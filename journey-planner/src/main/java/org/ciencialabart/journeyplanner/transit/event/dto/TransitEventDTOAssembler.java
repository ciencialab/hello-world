package org.ciencialabart.journeyplanner.transit.event.dto;

import org.ciencialabart.journeyplanner.transit.event.TransitEvent;

public interface TransitEventDTOAssembler {

    TransitEventDTO assemble(TransitEvent transitEvent);

}
