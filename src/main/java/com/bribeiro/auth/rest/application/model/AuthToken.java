package com.bribeiro.auth.rest.application.model;

public class AuthToken {

    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
