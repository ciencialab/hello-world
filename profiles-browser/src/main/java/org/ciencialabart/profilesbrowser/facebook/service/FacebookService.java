package org.ciencialabart.profilesbrowser.facebook.service;

import java.util.Map;
import java.util.Set;

import org.ciencialabart.profilesbrowser.facebook.Facebook;
import org.ciencialabart.profilesbrowser.facebook.service.exception.NotFoundException;

public interface FacebookService {

    void create(Facebook profile);

    Facebook findById(String id) throws NotFoundException;

    Map<String, Long> findMostCommonWords();

    Set<String> findPostIdsByKeyword(String word);

    Set<Facebook> findAll();
    
}
