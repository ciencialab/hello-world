package org.ciencialabart.meower.client.frontend;

import java.io.Serializable;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.Conversation;
import javax.enterprise.context.ConversationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.ciencialabart.meower.api.FollowedUserBean;
import org.ciencialabart.meower.api.UserBean;
import org.ciencialabart.meower.client.data.PostClientDTO;
import org.ciencialabart.meower.client.exception.PostTooLongException;
import org.ciencialabart.meower.client.rest.PostClientBean;
import org.ciencialabart.meower.client.rest.annotation.REST;
import org.ciencialabart.meower.data.PostDTO;
import org.ciencialabart.meower.exception.UserNotFoundException;
import org.ciencialabart.meower.util.JSFMessageIssuer;

@Named("JSFController")
@ConversationScoped
public class JSFController implements Serializable {

    private static final long serialVersionUID = 7288935896182026873L;
    
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";
    
    @Inject
    private Conversation conversation;
    
    @Inject
    private ConversationBean conversationBean;
    
    @Inject
    @REST
    private UserBean userBean;
    
    @Inject
    @REST
    private PostClientBean postBean;
    
    @Inject
    @REST
    private FollowedUserBean otherUserBean;
    
    private String userName = "";
    private String clientTimezoneOffset = "";
    private String newUserName = "";
    private String changeToUserName = "";
    private String postContent = "";
    private List<String> userNames = new ArrayList<>();
    private List<PostClientDTO> posts = new ArrayList<>();
    
    private String userNameToFollow = "";
    private List<String> followedUsers = new ArrayList<>();
    private String userNameToUnfollow = "";
    
    private List<PostClientDTO> followedPosts = new ArrayList<>();
    
    public String changeUser() {
        if (changeToUserName == null) {
            if (conversation.isTransient()) {
                return "index";
            }
        } else {
            if (conversation.isTransient()) {
                conversation.begin();
            }
            conversationBean.addConversation(conversation.getId(), changeToUserName);
            userName = changeToUserName;
        }
        
        return "index";
    }

    public String createPost() {
        if (newUserName.equals("")) {
            if (conversation.isTransient()) {
                return "index";
            }
        } else {
            if (conversation.isTransient()) {
                conversation.begin();
            }
            conversationBean.addConversation(conversation.getId(), newUserName);
            userName = newUserName;
        }
        
        try {
            postBean.createPost(getUserName(), postContent);
        } catch (PostTooLongException e) {
            JSFMessageIssuer.error("postContent", "postTooLong");
        }
        
        return "index";
    }

    public String follow() {
        if (!userNameToFollow.equals("")) {
            if (!conversation.isTransient() && getUserName().equals(userNameToFollow)) {
                JSFMessageIssuer.error("userNameToFollow", "enterOtherUser");
            } else {
                try {
                    otherUserBean.follow(getUserName(), userNameToFollow);
                } catch (UserNotFoundException e) {
                    JSFMessageIssuer.error("userNameToFollow", "userMissing");
                }
            }
        }
        
        return "index";
    }

    public String unfollow() {
        if (userNameToUnfollow != null) {
            try {
                otherUserBean.unfollow(getUserName(), userNameToUnfollow);
            } catch (UserNotFoundException e) {
            }
        }
        
        return "index";
    }
    
    public String getUserName() {
        return conversationBean.getUserForConversation(conversation.getId());
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getClientTimezoneOffset() {
        return clientTimezoneOffset;
    }

    public void setClientTimezoneOffset(String clientTimezoneOffset) {
        this.clientTimezoneOffset = clientTimezoneOffset;
    }

    public String getNewUserName() {
        newUserName = getUserName();
        
        return newUserName;
    }

    public void setNewUserName(String newUserName) {
        this.newUserName = newUserName;
    }

    public String getChangeToUserName() {
        changeToUserName = getUserName();
        
        return changeToUserName;
    }

    public void setChangeToUserName(String changeToUserName) {
        this.changeToUserName = changeToUserName;
    }

    public String getPostContent() {
        postContent = "";
        
        return postContent;
    }

    public void setPostContent(String postContent) {
        this.postContent = postContent;
    }

    public List<String> getUserNames() {
        userNames = userBean.getUserNames();
        
        return userNames;
    }

    public void setUserNames(List<String> userNames) {
        this.userNames = userNames;
    }

    public List<PostClientDTO> getPosts() {
        if (!conversation.isTransient()) {
            try {
                posts = postBean.getPosts(getUserName()).stream()
                        .map(this::mapPostDTOToPostClientDTO)
                        .collect(Collectors.toList());
            } catch (UserNotFoundException e) {
            }
        }
        
        return posts;
    }

    private PostClientDTO mapPostDTOToPostClientDTO(PostDTO post) {
        return new PostClientDTO(
                post.getAuthorName(),
                DateTimeFormatter.ofPattern(DATE_FORMAT)
                    .format(ZonedDateTime.parse(post.getDateTimeCreated())
                        .withZoneSameInstant(ZoneId.ofOffset("",
                            ZoneOffset.ofHours(-Integer.valueOf(clientTimezoneOffset) / 60)))),
                post.getContent());
    }
    
    public void setPosts(List<PostClientDTO> posts) {
        this.posts = posts;
    }

    public String getUserNameToFollow() {
        userNameToFollow = "";
        
        return userNameToFollow;
    }

    public void setUserNameToFollow(String userNameToFollow) {
        this.userNameToFollow = userNameToFollow;
    }

    public List<String> getFollowedUsers() {
        if (!conversation.isTransient()) {
            try {
                followedUsers = otherUserBean.getFollowedUsers(getUserName());
            } catch (UserNotFoundException e) {
            }
        }
        
        return followedUsers;
    }

    public void setFollowedUsers(List<String> followedUsers) {
        this.followedUsers = followedUsers;
    }

    public String getUserNameToUnfollow() {
        if (followedUsers.isEmpty()) {
            userNameToUnfollow = "";
        } else {
            userNameToUnfollow = followedUsers.get(0);
        }
        
        return userNameToUnfollow;
    }

    public void setUserNameToUnfollow(String userNameToUnfollow) {
        this.userNameToUnfollow = userNameToUnfollow;
    }

    public List<PostClientDTO> getFollowedPosts() {
        if (!conversation.isTransient()) {
            try {
                followedPosts = postBean.getFollowedPosts(getUserName()).stream()
                        .map(this::mapPostDTOToPostClientDTO)
                        .collect(Collectors.toList());
            } catch (UserNotFoundException e) {
            }
        }
        
        return followedPosts;
    }

    public void setFollowedPosts(List<PostClientDTO> followedPosts) {
        this.followedPosts = followedPosts;
    }
    
}
