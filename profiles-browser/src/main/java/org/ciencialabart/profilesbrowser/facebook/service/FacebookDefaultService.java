package org.ciencialabart.profilesbrowser.facebook.service;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.SortedMap;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.stream.Collectors;

import org.ciencialabart.profilesbrowser.facebook.Facebook;
import org.ciencialabart.profilesbrowser.facebook.service.exception.NotFoundException;
import org.ciencialabart.profilesbrowser.post.Post;
import org.springframework.stereotype.Service;
import org.springframework.web.context.annotation.ApplicationScope;

@ApplicationScope
@Service
public class FacebookDefaultService implements FacebookService {

    private SortedMap<String, Facebook> profiles = new TreeMap<>();
    
    public void create(Facebook profile) {
        profiles.put(profile.getId(), profile);
    }

    public Facebook findById(String id) throws NotFoundException {
        Facebook facebook = profiles.get(id);
        
        if (facebook == null) {
            throw new NotFoundException();
        }
        
        return facebook;
    }

    public Map<String, Long> findMostCommonWords() {
        return profiles.values().stream()
                .map(Facebook::getPosts)
                .flatMap(List::stream)
                .map(Post::getMessage)
                .map(message -> Arrays.asList(message.split("[\\s\\.,;:\\-\\!\\?\\(\\)\\$/]+")))
                .flatMap(List::stream)
                .filter(token -> !token.matches("^[\\d\']*$"))
                .collect(Collectors.groupingBy(word -> word, Collectors.counting()));
    }

    public Set<String> findPostIdsByKeyword(String word) {
        return profiles.values().stream()
                .filter(profile -> profile.getPosts().stream()
                        .map(Post::getMessage)
                        .anyMatch(message -> message.contains(word)))
                .map(Facebook::getId)
                .collect(Collectors.toSet());
    }

    public Set<Facebook> findAll() {
        Comparator<Facebook> firstThenLastNameComparator = (profile1, profile2) -> {
            int firstNameResult = profile1.getFirstName().compareTo(profile2.getFirstName());
            
            return firstNameResult == 0 ? profile1.getLastName().compareTo(profile2.getLastName())
                    : firstNameResult;
        };
        Set<Facebook> sortedProfiles = new TreeSet<>(firstThenLastNameComparator);
        
        sortedProfiles.addAll(profiles.values());
        
        return sortedProfiles;
    }

}
