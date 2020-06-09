package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;

import javax.validation.constraints.NotNull;
import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class PatientResponse extends UserResponse {

    private Date birthdate;
    private char gender;
    private int height;
    private float weight;
    private MedicResponse medic;

    public PatientResponse(Patient patient) {
        super(patient);
        this.birthdate = patient.getBirthdate();
        this.gender = patient.getGender();
        this.height = patient.getHeight();
        this.weight = patient.getWeight();
        Medic medic = patient.getMedic();
        if (Objects.nonNull(medic)) {
            this.medic = new MedicResponse(medic);
        }
    }
}
