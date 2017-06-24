package org.ciencialabart.journeyplanner.transit.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ciencialabart.journeyplanner.transit.Transit;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class TransitJPADAO implements TransitDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long create(Transit transit) {
        entityManager.persist(transit);
        
        return transit.getId();
    }

    @Override
    public Optional<Transit> get(long id) {
        return Optional.ofNullable(entityManager.find(Transit.class, id));
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(Transit.class, id));
    }
    
}
