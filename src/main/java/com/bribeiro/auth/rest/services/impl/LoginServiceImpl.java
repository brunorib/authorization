package com.bribeiro.auth.rest.services.impl;

import com.bribeiro.auth.rest.application.model.AuthToken;
import com.bribeiro.auth.rest.services.LoginService;
import com.bribeiro.auth.rest.application.exceptions.IncorrectLoginException;
import com.bribeiro.auth.rest.application.db.model.User;
import com.bribeiro.auth.rest.services.UserService;
import com.bribeiro.auth.rest.utils.HashUtil;
import com.bribeiro.auth.rest.utils.JwtUtil;

public class LoginServiceImpl implements LoginService {

    private UserService userService;

    public LoginServiceImpl(UserService userService) {
        this.userService = userService;
    }

    @Override
    public AuthToken login(String username, String password) throws IncorrectLoginException {
        // Calculate
        User u = userService.findByUsername(username);
        if (u == null) {
            throw new IncorrectLoginException();
        }
        // hash password and compare
        String hashedPassword = HashUtil.hashPassword(password, u.getSalt());
        if (hashedPassword == null || !hashedPassword.equals(u.getPassword())) {
            throw new IncorrectLoginException();
        }

        return new AuthToken(JwtUtil.createJwt(u.getId()));
    }

    @Override
    public boolean verifyJwt(String jwt) {
        return false;
    }
}
