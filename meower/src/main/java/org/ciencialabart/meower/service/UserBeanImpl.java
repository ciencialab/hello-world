package org.ciencialabart.meower.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Singleton;

import org.ciencialabart.meower.data.User;
import org.ciencialabart.meower.exception.UserNotFoundException;
import org.ciencialabart.meower.service.annotation.Service;

@Service
@Singleton
public class UserBeanImpl implements UserBackendBean {

    private List<User> users = new ArrayList<>();

    @Override
    public User getOrCreateUser(String userName) {
        User user = new User(userName);
        int userIndex = Collections.binarySearch(users, user);
        
        if (userIndex < 0) {
            users.add(user);
        } else {
            user = users.get(userIndex);
        }
        
        return user;
    }
    
    @Override
    public User getUser(String userName) throws UserNotFoundException {
        User user = new User(userName);
        int userIndex = Collections.binarySearch(users, user);
        
        if (userIndex < 0) {
            throw new UserNotFoundException(userName);
        } else {
            user = users.get(userIndex);
        }
        
        return user;
    }

    @Override
    public List<String> getUserNames() {
        return users.stream().map(User::getName).collect(Collectors.toList());
    }

}
