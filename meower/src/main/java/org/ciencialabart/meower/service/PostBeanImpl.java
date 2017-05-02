package org.ciencialabart.meower.service;

import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.enterprise.inject.Any;
import javax.inject.Inject;

import org.ciencialabart.meower.api.PostBean;
import org.ciencialabart.meower.data.Post;
import org.ciencialabart.meower.data.PostDTO;
import org.ciencialabart.meower.data.User;
import org.ciencialabart.meower.exception.UserNotFoundException;
import org.ciencialabart.meower.service.annotation.Service;

@Service
@Stateless
public class PostBeanImpl implements PostBean {

    @Inject
    @Any
    private UserBackendBean userBean;
    
    @Override
    public void createPost(String userName, String postContent) {
        User user = userBean.getOrCreateUser(userName);
        
        user.getPosts()
            .add(0, new Post(user, postContent));
    }

    @Override
    public List<PostDTO> getPosts(String userName) throws UserNotFoundException {
        return userBean.getUser(userName)
                    .getPosts().stream()
                    .map(PostBeanImpl::mapPostToPostDTO)
                    .collect(Collectors.toList());
    }

    private static PostDTO mapPostToPostDTO(Post post) {
        return new PostDTO(
                post.getAuthor().getName(),
                post.getDateTimeCreated().toString(),
                post.getContent());
    }

    @Override
    public List<PostDTO> getFollowedPosts(String userName) throws UserNotFoundException {
        List<Post> followedPosts = userBean.getUser(userName)
                .getFollowedUsers().stream()
                    .map(User::getPosts)
                    .flatMap(List::stream)
                        .collect(Collectors.toList());
        
        Collections.sort(followedPosts);
        
        return followedPosts.stream()
                .map(PostBeanImpl::mapPostToPostDTO)
                .collect(Collectors.toList());
    }
}
