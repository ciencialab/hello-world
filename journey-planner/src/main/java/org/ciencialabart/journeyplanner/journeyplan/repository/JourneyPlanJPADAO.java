package org.ciencialabart.journeyplanner.journeyplan.repository;

import java.util.Optional;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JourneyPlanJPADAO implements JourneyPlanDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public long create(JourneyPlan journeyPlan) {
        entityManager.persist(journeyPlan);
        
        return journeyPlan.getId();
    }

    @Override
    public void update(JourneyPlan journeyPlan) {
        if (entityManager.find(JourneyPlan.class, journeyPlan.getId()) == null) {
            throw new ResourceNotFoundException();
        }
        
        entityManager.merge(journeyPlan);
    }

    @Override
    public Optional<JourneyPlan> get(long id) {
        return Optional.ofNullable(entityManager.find(JourneyPlan.class, id));
    }

    @Override
    public void delete(long id) {
        JourneyPlan journeyPlan = entityManager.find(JourneyPlan.class, id);
        
        if (journeyPlan == null) {
            throw new ResourceNotFoundException();
        }
        
        entityManager.remove(journeyPlan);
    }
    
}
