package org.ciencialabart.journeyplanner.journeyplan.repository;

import java.util.Optional;

import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;

public interface JourneyPlanDAO {

    long create(JourneyPlan journeyPlan);

    void update(JourneyPlan journeyPlan);
    
    Optional<JourneyPlan> get(long id);

    void delete(long id);
    
}
