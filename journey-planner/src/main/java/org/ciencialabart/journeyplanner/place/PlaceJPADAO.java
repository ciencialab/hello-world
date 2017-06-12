package org.ciencialabart.journeyplanner.place;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class PlaceJPADAO implements PlaceDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    public long create(Place place) {
        entityManager.persist(place);
        
        return place.getId();
    }

    @Override
    public void update(Place place) {
        entityManager.merge(place);
    }

    public Place get(long id) {
        return entityManager.find(Place.class, id);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(Place.class, id));
    }
    
}
