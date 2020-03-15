package com.bribeiro.auth.rest.controllers;

import com.bribeiro.auth.rest.application.exceptions.IncorrectLoginException;
import com.bribeiro.auth.rest.services.LoginService;
import com.bribeiro.auth.rest.utils.JsonUtil;
import com.bribeiro.auth.rest.utils.ResponseError;
import com.bribeiro.auth.rest.application.model.User;

import static spark.Spark.*;
import static spark.Spark.after;

public class LoginController {
    private static final String CONTEXT = "/login";

    private JsonUtil jsonUtil;

    public LoginController(final LoginService loginService) {

        jsonUtil = new JsonUtil();

        post(CONTEXT, (req, res) -> {
            User inputUser = jsonUtil.fromJson(req.body(), User.class);
            try {
                return loginService.login(inputUser.getUsername(),
                        inputUser.getPassword());
            } catch (IncorrectLoginException e) {
                res.status(403);
                return new ResponseError("Wrong username/password");
            }
        }, jsonUtil.json());

        after((req, res) -> res.type("application/json"));
    }
}
