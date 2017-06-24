package org.ciencialabart.journeyplanner.transit.controller;

import java.util.Optional;

import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.transit.dto.TransitDTO;
import org.ciencialabart.journeyplanner.transit.service.TransitService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/transits")
public class TransitController {
    
    private TransitService transitService;
    
    @Autowired
    public TransitController(TransitService transitService) {
        this.transitService = transitService;
    }

    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public String create(@RequestBody TransitDTO transitDTO) {
        return String.valueOf(transitService.create(transitDTO));
    }
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public TransitDTO getById(@PathVariable long id) {
        Optional<TransitDTO> optionalTransitDTO = transitService.get(id);
        
        if (optionalTransitDTO.isPresent()) {
            return optionalTransitDTO.get();
        }
        
        throw new ResourceNotFoundException();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        transitService.delete(id);
    }
    
}
