package org.ciencialabart.journeyplanner.journeyplan;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@Transactional
public class JourneyPlanDAOImpl implements JourneyPlanDAO {
    
    @PersistenceContext
    private EntityManager entityManager;

    public long create(JourneyPlan journeyPlan) {
        entityManager.persist(journeyPlan);
        
        return journeyPlan.getId();
    }

    @Override
    public void update(JourneyPlan journeyPlan) {
        entityManager.merge(journeyPlan);
    }

    public JourneyPlan get(long id) {
        return entityManager.find(JourneyPlan.class, id);
    }

    @Override
    public void delete(long id) {
        entityManager.remove(entityManager.find(JourneyPlan.class, id));
    }
    
}
