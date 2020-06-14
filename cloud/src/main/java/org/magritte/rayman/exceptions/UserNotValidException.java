package org.magritte.rayman.exceptions;

public class UserNotValidException extends RuntimeException {

    public UserNotValidException() {
        super("Wrong dni or password");
    }
}
