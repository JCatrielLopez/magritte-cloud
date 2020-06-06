package org.magritte.rayman.service;

public class UserNotValidException extends RuntimeException {

    UserNotValidException(){ super("Wrong dni or password"); }
}
