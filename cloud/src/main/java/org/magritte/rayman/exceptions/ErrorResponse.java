package org.magritte.rayman.exceptions;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
public class ErrorResponse {

    private List<String> errors;

    public ErrorResponse(String error) {
        errors = new ArrayList<>();
        errors.add(error);
    }

    public ErrorResponse(List<String> errors) {
        this.errors = errors;
    }
}
