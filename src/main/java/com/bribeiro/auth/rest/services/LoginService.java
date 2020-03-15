package com.bribeiro.auth.rest.services;

import com.bribeiro.auth.rest.application.exceptions.IncorrectLoginException;

public interface LoginService {

    String login(String username, String password) throws IncorrectLoginException;

    boolean verifyJwt(String jwt);

}
