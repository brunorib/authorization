package com.bribeiro.auth.rest.services;

import com.bribeiro.auth.rest.application.exceptions.IncorrectLoginException;
import com.bribeiro.auth.rest.common.models.AuthToken;

public interface LoginService {

    AuthToken login(String username, String password) throws IncorrectLoginException;

    boolean verifyJwt(String jwt);

}
