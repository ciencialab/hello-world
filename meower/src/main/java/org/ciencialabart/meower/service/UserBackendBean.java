package org.ciencialabart.meower.service;

import org.ciencialabart.meower.api.UserBean;
import org.ciencialabart.meower.data.User;
import org.ciencialabart.meower.exception.UserNotFoundException;

public interface UserBackendBean extends UserBean {

    User getOrCreateUser(String userName);

    User getUser(String userName) throws UserNotFoundException;

}
