package org.ciencialabart.meower.client.data;

public class PostClientDTO {
    private String authorName;
    private String dateTimeCreated;
    private String content;

    
    public PostClientDTO(String authorName, String dateTimeCreated, String content) {
        this.authorName = authorName;
        this.dateTimeCreated = dateTimeCreated;
        this.content = content;
    }
    public String getAuthorName() {
        return authorName;
    }
    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }
    public String getDateTimeCreated() {
        return dateTimeCreated;
    }
    public void setDateTimeCreated(String dateTimeCreated) {
        this.dateTimeCreated = dateTimeCreated;
    }
    public String getContent() {
        return content;
    }
    public void setContent(String content) {
        this.content = content;
    }

}
