package com.bribeiro.auth.rest.controllers;

import com.bribeiro.auth.rest.application.exceptions.ServerException;
import com.bribeiro.auth.rest.application.exceptions.UserAlreadyExistsException;
import com.bribeiro.auth.rest.application.db.model.User;
import com.bribeiro.auth.rest.services.UserService;
import com.bribeiro.auth.rest.utils.JsonUtil;
import com.bribeiro.auth.rest.utils.ResponseError;

import static spark.Spark.*;

public class UserController {

    private static final String CONTEXT = "/users";

    private static final String PARAM_ID = ":id";

    private static final String BY_ID = CONTEXT + "/" + PARAM_ID;

    private JsonUtil jsonUtil;

    public UserController(final UserService userService) {

        jsonUtil = new JsonUtil();

        get(BY_ID, (req, res) -> {
            int userId =  Integer.parseInt(req.params(PARAM_ID));
            User u = userService.getUser(userId);
            if (u != null) {
                return u.cleanSensitiveData();
            }
            res.status(404);
            return new ResponseError("No user with id %s found", userId);
        }, jsonUtil.json());

        post(CONTEXT, (req, res) -> {
            try {
                User u = userService.createUser(
                        jsonUtil.fromJson(req.body(), User.class))
                        .cleanSensitiveData();
                res.status(201);
                return u;
            } catch (UserAlreadyExistsException e) {
                res.status(500);
                return new ResponseError("Username or email already taken");
            } catch (ServerException e) {
                res.status(500);
                return new ResponseError("Server error");
            }
        }, jsonUtil.json());

        put(BY_ID, (req, res) -> {
            int userId =  Integer.parseInt(req.params(PARAM_ID));

            if (userService.findById(userId)) {
                User updated = jsonUtil.fromJson(req.body(), User.class);
                updated.setId(userId);

                User u = userService.updateUser(updated).cleanSensitiveData();
                if (u != null){
                    res.status(200);
                    return u;
                }

                res.status(500);
                return new ResponseError("Could not update user");
            }
            res.status(404);
            return new ResponseError("No user with id %s found", userId);
        }, jsonUtil.json());

        after((req, res) -> res.type("application/json"));
    }
}
