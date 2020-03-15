package com.bribeiro.auth.rest.controllers;

import com.bribeiro.auth.rest.application.model.User;
import com.bribeiro.auth.rest.services.UserService;
import com.bribeiro.auth.rest.utils.JsonUtil;
import com.bribeiro.auth.rest.utils.ResponseError;

import static spark.Spark.*;

public class UserController {

    private static final String CONTEXT = "/users";

    private static final String PARAM_ID = ":userid";

    private static final String BY_ID = CONTEXT + "/" + PARAM_ID;

    private JsonUtil jsonUtil;

    public UserController(final UserService userService) {

        jsonUtil = new JsonUtil();

        get(BY_ID, (req, res) -> {
            int userId =  Integer.parseInt(req.params(PARAM_ID));
            User u = userService.getUser(userId);
            if (u != null) {
                return u;
            }
            res.status(404);
            return new ResponseError("No user with id %s found", userId);
        }, jsonUtil.json());

        post(CONTEXT, (req, res) -> {
            User u = jsonUtil.fromJson(req.body(), User.class);
            return userService.createUser(u).getId();
        }, jsonUtil.json());

        put(BY_ID, (req, res) -> {
            int userId =  Integer.parseInt(req.params(PARAM_ID));
            if (userService.findById(userId)) {
                User u = jsonUtil.fromJson(req.body(), User.class);
                u.setId(userId);
                return userService.updateUser(u);
            }
            res.status(404);
            return new ResponseError("No user with id %s found", userId);
        }, jsonUtil.json());

        after((req, res) -> res.type("application/json"));
    }
}
