package org.ciencialabart.journeyplanner.place.controller;

import java.util.Optional;

import org.ciencialabart.journeyplanner.exception.RemovedResourceInUseException;
import org.ciencialabart.journeyplanner.exception.http.ResourceInConflictingStateException;
import org.ciencialabart.journeyplanner.exception.http.ResourceNotFoundException;
import org.ciencialabart.journeyplanner.place.dto.PlaceDTO;
import org.ciencialabart.journeyplanner.place.service.PlaceService;
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
@RequestMapping(path = "/places")
public class PlaceController {
    
    private PlaceService placeService;

    @Autowired
    public PlaceController(PlaceService placeService) {
        this.placeService = placeService;
    }

    @PostMapping
    @ResponseBody
    public String createForName(@RequestParam String name) {
        return String.valueOf(placeService.createForName(name));
    }

    @PostMapping(path = "/{id}")
    public void rename(@PathVariable long id, @RequestParam String name) {
        placeService.rename(id, name);
    }
    
    @GetMapping(path = "/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    public PlaceDTO getById(@PathVariable long id) {
        Optional<PlaceDTO> optionalPlaceDTO = placeService.get(id);
        
        if (optionalPlaceDTO.isPresent()) {
            return optionalPlaceDTO.get();
        }
        
        throw new ResourceNotFoundException();
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        try {
            placeService.delete(id);
        } catch (RemovedResourceInUseException e) {
            throw new ResourceInConflictingStateException();
        }
    }
    
}
