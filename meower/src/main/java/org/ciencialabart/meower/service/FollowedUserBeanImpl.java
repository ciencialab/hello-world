package org.ciencialabart.meower.service;

import java.util.List;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.ciencialabart.meower.api.FollowedUserBean;
import org.ciencialabart.meower.data.User;
import org.ciencialabart.meower.exception.UserNotFoundException;
import org.ciencialabart.meower.service.annotation.Service;

@Service
@Stateless
public class FollowedUserBeanImpl implements FollowedUserBean{

    @Inject
    @Any
    private UserBackendBean userBean;

    @Override
    public void follow(String userName, String userNameToFollow) throws UserNotFoundException {
        User user = userBean.getUser(userName);
        User userToFollow = userBean.getUser(userNameToFollow);
        
        user.addFollowedUser(userToFollow);
    }

    @Override
    public List<String> getFollowedUsers(String userName) throws UserNotFoundException {
        return userBean.getUser(userName).getFollowedUserNames();
    }

    @Override
    public void unfollow(String userName, String followedUserName) throws UserNotFoundException {
        User user = userBean.getUser(userName);
        User followedUser = userBean.getUser(followedUserName);
        
        user.removeFollowedUser(followedUser);
    }

}
