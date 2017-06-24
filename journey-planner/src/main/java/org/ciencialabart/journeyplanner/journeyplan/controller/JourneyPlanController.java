package org.ciencialabart.journeyplanner.journeyplan.controller;

import java.util.Optional;

import org.ciencialabart.journeyplanner.exception.http.BadRequestException;
import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.journeyplan.JourneyPlan;
import org.ciencialabart.journeyplanner.journeyplan.service.JourneyPlanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/journey-plans")
public class JourneyPlanController {
    
    private JourneyPlanService journeyPlanService;

    @Autowired
    public JourneyPlanController(JourneyPlanService journeyPlanService) {
        this.journeyPlanService = journeyPlanService;
    }

    @PostMapping
    @ResponseBody
    public String createForName(@RequestParam String name) {
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        
        return String.valueOf(journeyPlanService.createForName(name));
    }

    @PostMapping(path = "/{id}")
    public void rename(@PathVariable long id, @RequestParam String name) {
        if (name.isEmpty()) {
            throw new BadRequestException();
        }
        
        try {
            journeyPlanService.rename(id, name);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public JourneyPlan getById(@PathVariable long id) {
        Optional<JourneyPlan> optionalJourneyPlan = journeyPlanService.get(id);
        
        if (optionalJourneyPlan.isPresent()) {
            return optionalJourneyPlan.get();
        }
        
        throw new ResourceNotFoundException();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        
        try {
            journeyPlanService.delete(id);
        } catch (ResourceNotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }
    
}
