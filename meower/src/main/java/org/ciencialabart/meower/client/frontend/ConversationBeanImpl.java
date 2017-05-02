package org.ciencialabart.meower.client.frontend;

import java.util.HashMap;
import java.util.Map;

import javax.ejb.Singleton;

@Singleton
public class ConversationBeanImpl implements ConversationBean {
    
    private Map<String, String> conversations = new HashMap<>();

    @Override
    public void addConversation(String conversationId, String userName) {
        conversations.put(conversationId, userName);
    }

    @Override
    public String getUserForConversation(String conversationId) {
        return conversations.getOrDefault(conversationId, "");
    }

}
