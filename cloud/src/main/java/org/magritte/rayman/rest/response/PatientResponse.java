package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Patient;

import java.util.Date;

@Getter
@Setter
@NoArgsConstructor
public class PatientResponse extends UserResponse {

    private Date birthdate;

    private char gender;

    private int height;

    private float weight;

    public PatientResponse(Patient patient) {
        super(patient);
        this.birthdate = patient.getBirthdate();
        this.gender = patient.getGender();
        this.height = patient.getHeight();
        this.weight = patient.getWeight();
    }
}
