package org.magritte.rayman.exceptions;

public class UserNotValidException extends RuntimeException {

    UserNotValidException() {
        super("Wrong dni or password");
    }
}
