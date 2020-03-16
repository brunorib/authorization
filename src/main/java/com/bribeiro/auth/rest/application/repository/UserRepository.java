package com.bribeiro.auth.rest.application.repository;

import com.bribeiro.auth.rest.application.exceptions.ServerException;
import com.bribeiro.auth.rest.application.exceptions.UserAlreadyExistsException;
import com.bribeiro.auth.rest.application.model.User;

public interface UserRepository {

    User getUserById(int id);

    User getUserByUsername(String username);

    User saveUser(User u) throws UserAlreadyExistsException, ServerException;

    void deleteUser(String id);

    User updateUser(User u);
}
