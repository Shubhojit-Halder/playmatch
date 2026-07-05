package com.playmatch.playmatch.exception;

public class MatchNotFoundException extends RuntimeException {
    public MatchNotFoundException(String id) {
        super("Match not found with id: " + id);
    }
}
