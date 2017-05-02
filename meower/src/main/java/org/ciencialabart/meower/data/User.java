package org.ciencialabart.meower.data;

import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class User implements Comparable<User> {
    private String name = "";
    private List<Post> posts = new LinkedList<>();
    private Set<User> followedUsers = new HashSet<>();

    public User(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Post> getPosts() {
        return posts;
    }

    public void setPosts(List<Post> posts) {
        this.posts = posts;
    }

    public Set<User> getFollowedUsers() {
        return followedUsers;
    }

    public List<String> getFollowedUserNames() {
        return followedUsers.stream().map(User::getName).collect(Collectors.toList());
    }

    public void setFollowedUsers(Set<User> followedUsers) {
        this.followedUsers = followedUsers;
    }

    @Override
    public int compareTo(User other) {
        return name.compareTo(other.name);
    }

    public void addFollowedUser(User userToFollow) {
        followedUsers.add(userToFollow);
    }

    public void removeFollowedUser(User followedUser) {
        followedUsers.remove(followedUser);
    }
    
}
