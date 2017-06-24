package org.ciencialabart.journeyplanner.place.service;

import java.util.Optional;

import org.ciencialabart.journeyplanner.place.dto.PlaceDTO;

public interface PlaceService {

    Optional<PlaceDTO> get(long id);

    long createForName(String name);

    void rename(long id, String newName);

    void delete(long id);

}
