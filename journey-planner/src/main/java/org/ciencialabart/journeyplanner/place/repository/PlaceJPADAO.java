package org.ciencialabart.journeyplanner.place.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ciencialabart.journeyplanner.exception.RemovedResourceInUseException;
import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.place.Place;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PlaceJPADAO implements PlaceDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long create(Place place) {
        entityManager.persist(place);
        
        return place.getId();
    }

    @Override
    public void update(Place place) {
        if (entityManager.find(Place.class, place.getId()) == null) {
            throw new ResourceNotFoundException();
        }
        
        entityManager.merge(place);
    }

    @Override
    public Optional<Place> get(long id) {
        return Optional.ofNullable(entityManager.find(Place.class, id));
    }

    @Override
    public void delete(long id) {
        Place place = entityManager.find(Place.class, id);
        
        if (place == null) {
            throw new ResourceNotFoundException();
        }
        
        if (place.getDepartureTransits().isEmpty() &&
                place.getArrivalTransits().isEmpty()) {
            entityManager.remove(place);
        } else {
            throw new RemovedResourceInUseException();
        }
        
    }
    
}
