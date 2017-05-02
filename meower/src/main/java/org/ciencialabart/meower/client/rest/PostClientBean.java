package org.ciencialabart.meower.client.rest;

import java.util.List;

import org.ciencialabart.meower.client.exception.PostTooLongException;
import org.ciencialabart.meower.data.PostDTO;
import org.ciencialabart.meower.exception.UserNotFoundException;

public interface PostClientBean {

    void createPost(String userName, String postContent) throws PostTooLongException;

    List<PostDTO> getPosts(String userName) throws UserNotFoundException;

    List<PostDTO> getFollowedPosts(String userName) throws UserNotFoundException;

}
