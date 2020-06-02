package org.magritte.rayman.service;

public class UserNotValidException extends RuntimeException{

    UserNotValidException(String dni){ super("Wrong password for user " + dni); }
}
