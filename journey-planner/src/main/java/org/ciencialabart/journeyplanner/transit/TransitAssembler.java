package org.ciencialabart.journeyplanner.transit;

import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;

public interface TransitAssembler {

    Transit assemble(TransitDTO transitDTO);

}
