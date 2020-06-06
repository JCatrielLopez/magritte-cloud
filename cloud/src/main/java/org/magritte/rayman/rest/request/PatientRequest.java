package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotNull;


@Getter
@Setter
@NoArgsConstructor
public class PatientRequest {

    @NotNull
    private String dni;

    @NotNull
    private String name;

    @NotNull
    private String lastname;

    @NotNull
    private String password;

    @NotNull
    private String email;

    @NotNull
    private String birthdate;

    @NotNull
    private char gender;

    @NotNull
    private Integer height;

    @NotNull
    private Float weight;
}
