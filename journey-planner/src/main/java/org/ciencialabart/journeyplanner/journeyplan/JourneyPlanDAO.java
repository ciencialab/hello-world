package org.ciencialabart.journeyplanner.journeyplan;

public interface JourneyPlanDAO {

    long create(JourneyPlan journeyPlan);

    void update(JourneyPlan journeyPlan);
    
    JourneyPlan get(long id);

    void delete(long id);
    
}
