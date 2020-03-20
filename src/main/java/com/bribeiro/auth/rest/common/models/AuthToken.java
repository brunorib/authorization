package com.bribeiro.auth.rest.common.models;

public class AuthToken {

    private String token;

    public AuthToken(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
