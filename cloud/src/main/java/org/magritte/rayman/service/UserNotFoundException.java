package org.magritte.rayman.service;

class UserNotFoundException extends RuntimeException {

    UserNotFoundException(Integer id) {
        super("Could not find user " + id);
    }

    UserNotFoundException(String dni){ super("Could not find user " + dni); }
}