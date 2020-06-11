package org.magritte.rayman.exceptions;

public class RoutineNotFoundException extends RuntimeException {

    public RoutineNotFoundException() {
        super("Routine not found!");
    }
}
