package org.ciencialabart.journeyplanner.place.service;

import java.util.Optional;

import org.ciencialabart.journeyplanner.place.Place;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTO;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTOAssembler;
import org.ciencialabart.journeyplanner.place.repository.PlaceDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PlaceDefaultService implements PlaceService {
    
    private PlaceDAO placeDAO;
    private PlaceDTOAssembler placeDTOAssembler;

    @Autowired
    public PlaceDefaultService(PlaceDAO placeDAO, PlaceDTOAssembler placeDTOAssembler) {
        this.placeDAO = placeDAO;
        this.placeDTOAssembler = placeDTOAssembler;
    }

    @Override
    public long createForName(String name) {
        return placeDAO.create(new Place(name));
    }

    @Override
    public void rename(long id, String newName) {
        placeDAO.update(new Place(id, newName));
    }

    @Override
    public Optional<PlaceDTO> get(long id) {
        Optional<Place> optionalPlace = placeDAO.get(id);
        
        if (optionalPlace.isPresent()) {
            return Optional.of(placeDTOAssembler.assemble(optionalPlace.get()));
        }
        
        return Optional.empty();
    }

    @Override
    public void delete(long id) {
        placeDAO.delete(id);
    }

}
