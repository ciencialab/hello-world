package org.ciencialabart.meower.api;

import java.util.List;

import org.ciencialabart.meower.exception.UserNotFoundException;

public interface FollowedUserBean {

    void follow(String userName, String userNameToFollow) throws UserNotFoundException;

    List<String> getFollowedUsers(String userName) throws UserNotFoundException;

    void unfollow(String userName, String followedUserName) throws UserNotFoundException;

}
