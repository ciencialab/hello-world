package org.ciencialabart.meower.api;

import java.util.List;

import org.ciencialabart.meower.data.PostDTO;
import org.ciencialabart.meower.exception.UserNotFoundException;

public interface PostBean {

    void createPost(String userName, String postContent);

    List<PostDTO> getPosts(String userName) throws UserNotFoundException;

    List<PostDTO> getFollowedPosts(String userName) throws UserNotFoundException;

}
