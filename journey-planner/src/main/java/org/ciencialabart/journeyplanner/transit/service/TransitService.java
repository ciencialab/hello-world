package org.ciencialabart.journeyplanner.transit.service;

import java.util.Optional;

import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;

public interface TransitService {

    long create(TransitDTO transitDTO);

    Optional<TransitDTO> get(long id);

    void delete(long id);

}
