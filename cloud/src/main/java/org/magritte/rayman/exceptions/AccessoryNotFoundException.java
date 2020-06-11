package org.magritte.rayman.exceptions;

public class AccessoryNotFoundException extends RuntimeException {

    public AccessoryNotFoundException() {
        super("Accessory not found!");
    }
}
