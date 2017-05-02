package org.ciencialabart.meower.exception;

public class UserNotFoundException extends Exception {

    private static final long serialVersionUID = -7086666585990751991L;

    private String userName;
    
    public UserNotFoundException(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

}
