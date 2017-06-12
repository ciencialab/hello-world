package org.ciencialabart.journeyplanner.place;

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
@RequestMapping(path = "/place")
public class PlaceController {
    
    private PlaceDAO placeDAO;
    
    @Autowired
    public PlaceController(PlaceDAO placeDAO) {
        this.placeDAO = placeDAO;
    }

    @PostMapping
    @ResponseBody
    public String createForName(@RequestParam String name) {
        return String.valueOf(placeDAO.create(new Place(name)));
    }

    @PostMapping(path = "/{id}")
    public void rename(@PathVariable long id, @RequestParam String name) {
        placeDAO.update(new Place(id, name));
    }
    
    @GetMapping(path = "/{id}", produces = "application/json")
    @ResponseBody
    public Place getById(@PathVariable long id) {
        return placeDAO.get(id);
    }

    @DeleteMapping(path = "/{id}")
    public void delete(@PathVariable long id) {
        placeDAO.delete(id);
    }
    
}
