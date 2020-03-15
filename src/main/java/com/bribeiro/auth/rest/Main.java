package com.bribeiro.auth.rest;

import com.bribeiro.auth.rest.controllers.LoginController;
import com.bribeiro.auth.rest.services.LoginService;
import com.bribeiro.auth.rest.services.impl.LoginServiceImpl;
import com.bribeiro.auth.rest.services.impl.UserServiceImpl;
import com.bribeiro.auth.rest.controllers.UserController;
import com.bribeiro.auth.rest.services.UserService;

public class Main {
    public static void main(String[] args) {
        // Declaration Services
        UserService userService = new UserServiceImpl();
        LoginService loginService = new LoginServiceImpl(userService);

        // Declaration controllers
        new LoginController(loginService);
        new UserController(userService);
    }
}
