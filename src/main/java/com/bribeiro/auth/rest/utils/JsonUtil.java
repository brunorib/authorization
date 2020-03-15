package com.bribeiro.auth.rest.utils;

import com.google.gson.Gson;
import spark.ResponseTransformer;

public class JsonUtil {

    private static final Gson GSON = new Gson();

    public String toJson(Object object) {
        return GSON.toJson(object);
    }

    public ResponseTransformer json() {
        return this::toJson;
    }

    public <T> T fromJson(String json, Class<T> clss) {
        return GSON.fromJson(json, clss);
    }
}
