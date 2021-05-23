package com.orientdb;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.Serializable;

public class GsonConverter {

    private static final Gson gson;

    static {
        gson = new GsonBuilder()
                .setFieldNamingStrategy(FieldNamingPolicy.LOWER_CASE_WITH_UNDERSCORES)
                .create();
    }

    public static <T extends Serializable> String serialize(T t) {
        if (t == null) {
            throw new NullPointerException();
        }

        return gson.toJson(t);
    }

    public static <T extends Serializable> T deserialize(String content, Class<T> type) {
        if (content == null || type == null) {
            throw new NullPointerException();
        }

        if (content.isEmpty()) {
            throw new IllegalArgumentException();
        }

        return gson.fromJson(content, type);
    }

}
