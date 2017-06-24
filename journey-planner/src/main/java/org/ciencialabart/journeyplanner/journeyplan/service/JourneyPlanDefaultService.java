package org.ciencialabart.journeyplanner.journeyplan.service;

import java.util.Optional;

import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;
import org.ciencialabart.journeyplanner.journeyplan.repository.JourneyPlanDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JourneyPlanDefaultService implements JourneyPlanService {

    private JourneyPlanDAO journeyPlanDAO;
    
    @Autowired
    public JourneyPlanDefaultService(JourneyPlanDAO journeyPlanDAO) {
        this.journeyPlanDAO = journeyPlanDAO;
    }

    @Override
    public long createForName(String name) {
        return journeyPlanDAO.create(new JourneyPlan(name));
    }

    @Override
    public void rename(long id, String newName) {
        journeyPlanDAO.update(new JourneyPlan(id, newName));
    }

    @Override
    public Optional<JourneyPlan> get(long id) {
        return journeyPlanDAO.get(id);
    }

    @Override
    public void delete(long id) {
        journeyPlanDAO.delete(id);
    }

}
