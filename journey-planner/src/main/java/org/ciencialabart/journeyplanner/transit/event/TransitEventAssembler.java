package org.ciencialabart.journeyplanner.transit.event;

import org.ciencialabart.journeyplanner.transit.event.dto.TransitEventDTO;

public interface TransitEventAssembler {

    TransitEvent assemble(TransitEventDTO transitEventDTO);

}
