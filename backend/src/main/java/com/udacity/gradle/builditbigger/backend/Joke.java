package com.udacity.gradle.builditbigger.backend;

/** The object model for the data we are sending through endpoints */
public class Joke {

    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String data) {
        value = data;
    }
}