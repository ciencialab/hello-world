package org.ciencialabart.meower.data;

import java.time.ZonedDateTime;

public class Post implements Comparable<Post> {
    private User author;
    private ZonedDateTime dateTimeCreated;
    private String content;
    
    public Post(User author, String content) {
        this.author = author;
        this.dateTimeCreated = ZonedDateTime.now();
        this.content = content;
    }
    public User getAuthor() {
        return author;
    }
    public void setAuthor(User author) {
        this.author = author;
    }
    public ZonedDateTime getDateTimeCreated() {
        return dateTimeCreated;
    }
    public void setDateTimeCreated(ZonedDateTime dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }
    
    @Override
    public int compareTo(Post other) {
        return -dateTimeCreated.compareTo(other.dateTimeCreated);
    }
    
}
