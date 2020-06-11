package org.magritte.rayman.exceptions;

public class DataSetNotFoundException extends RuntimeException {

    public DataSetNotFoundException() {
        super("DataSet not found!");
    }
}
