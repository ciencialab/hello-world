package org.ciencialabart.profilesbrowser.facebook.controller;

import java.util.Map;
import java.util.Set;

import org.ciencialabart.profilesbrowser.exception.http.ResourceNotFoundException;
import org.ciencialabart.profilesbrowser.facebook.Facebook;
import org.ciencialabart.profilesbrowser.facebook.service.FacebookService;
import org.ciencialabart.profilesbrowser.facebook.service.exception.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class FacebookController {
    
    private FacebookService profileService;

    @Autowired
    public FacebookController(FacebookService profileService) {
        this.profileService = profileService;
    }

    @PutMapping(path = "/profiles", consumes = MediaType.APPLICATION_JSON_VALUE)
    public void create(@RequestBody Facebook profile) {
        profileService.create(profile);
    }

    @GetMapping(path = "/profiles/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Facebook findById(@PathVariable String id) {
        try {
            return profileService.findById(id);
        } catch (NotFoundException e) {
            throw new ResourceNotFoundException();
        }
    }

    @GetMapping(path = "/words", produces = MediaType.APPLICATION_JSON_VALUE)
    public Map<String, Long> findMostCommonWords() {
        return profileService.findMostCommonWords();
    }

    @GetMapping(path = "/posts/{word}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<String> findPostIdsByKeyword(@PathVariable String word) {
        return profileService.findPostIdsByKeyword(word);
    }

    @GetMapping(path = "/profiles", produces = MediaType.APPLICATION_JSON_VALUE)
    public Set<Facebook> findAll() {
        return profileService.findAll();
    }
    
}
