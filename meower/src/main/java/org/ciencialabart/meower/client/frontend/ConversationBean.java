package org.ciencialabart.meower.client.frontend;

public interface ConversationBean {

    void addConversation(String conversationId, String userName);

    String getUserForConversation(String conversationId);

}
