package org.ciencialabart.journeyplanner.journeyplan.service;

import java.util.Optional;

import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;

public interface JourneyPlanService {

    long createForName(String name);

    void rename(long id, String newName);

    Optional<JourneyPlan> get(long id);

    void delete(long id);

}
