package org.ciencialabart.journeyplanner.transit.repository;

import java.util.Optional;

import org.ciencialabart.journeyplanner.transit.Transit;

public interface TransitDAO {

    long create(Transit transit);
    
    Optional<Transit> get(long id);

    void delete(long id);
    
}
