package org.ciencialabart.journeyplanner.journeyplan;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/journey-plan")
public class JourneyPlanController {
    
    private JourneyPlanDAO journeyPlanDAO;
    
    @Autowired
    public JourneyPlanController(JourneyPlanDAO journeyPlanDAO) {
        this.journeyPlanDAO = journeyPlanDAO;
    }

    @PostMapping
    @ResponseBody
    public String createForName(@RequestParam String name) {
        return String.valueOf(journeyPlanDAO.create(new JourneyPlan(name)));
    }

    @PostMapping(path = "/{id}")
    public void rename(@PathVariable long id, @RequestParam String name) {
        journeyPlanDAO.update(new JourneyPlan(id, name));
    }
    
    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public JourneyPlan getById(@PathVariable long id) {
        return journeyPlanDAO.get(id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        journeyPlanDAO.delete(id);
    }
    
}
