package com.github.mihnead12.currrencycovert.web.exceptions;

public class FetchError extends RuntimeException {
    public FetchError(String url) {
        super("Error while fetching " + url);
    }
}
