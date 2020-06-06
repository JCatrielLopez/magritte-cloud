package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Patient;

import javax.validation.constraints.NotNull;
import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PatientRequest extends UserRequest {

    @NotNull
    private Date birthdate;

    @NotNull
    private char gender;

    @NotNull
    private int height;

    @NotNull
    private float weight;

    public Patient toNewEntity() {
        return new Patient(getDni(), getName(), getLastname(), getEmail(),
                getBirthdate(), getGender(), getHeight(), getWeight());
    }
}
