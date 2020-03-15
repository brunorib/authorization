package com.bribeiro.auth.rest.application.repository;

import com.bribeiro.auth.rest.application.model.User;

public interface UserRepository {

    User getUserById(int id);

    User getUserByUsername(String username);

    User saveUser(User u);

    void deleteUser(String id);

    User updateUser(User u);
}
