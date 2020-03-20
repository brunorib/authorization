package com.bribeiro.auth.rest.services;

import com.bribeiro.auth.rest.application.exceptions.ServerException;
import com.bribeiro.auth.rest.application.exceptions.UserAlreadyExistsException;
import com.bribeiro.auth.rest.application.db.model.User;

public interface UserService {

    /**
     * gets a specific user by its id
     * @return
     */
    User getUser(int id);

    /**
     * creates a new user given its name and email
     * @param u User
     * @return
     */
    User createUser(User u) throws UserAlreadyExistsException, ServerException;

    /**
     * updates a specific user
     * @param u User
     * @return
     */
    User updateUser(User u);

    /**
     * Finds if user exists
     * @param userId
     * @return
     */
    boolean findById(int userId);

    /**
     * Finds user by username
     * @param username
     * @return
     */
    User findByUsername(String username);
}
