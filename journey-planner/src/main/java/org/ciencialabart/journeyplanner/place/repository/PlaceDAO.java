package org.ciencialabart.journeyplanner.place.repository;

import java.util.Optional;

import org.ciencialabart.journeyplanner.place.Place;

public interface PlaceDAO {

    long create(Place place);

    void update(Place place);
    
    Optional<Place> get(long id);

    void delete(long id);
    
}
