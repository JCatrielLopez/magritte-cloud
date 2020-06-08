package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.exceptions.UserNotFoundException;

import java.util.Date;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
public class PatientResponse extends UserResponse {

    public static final Integer NO_VALUE = -1;

    private Date birthdate;

    private char gender;

    private int height;

    private float weight;

    private Integer medic_id;

    public PatientResponse(User patient) {
        super(patient);
        if (patient.getUserType() == 'P') {
            Patient p = (Patient) patient;
            this.birthdate = p.getBirthdate();
            this.gender = p.getGender();
            this.height = p.getHeight();
            this.weight = p.getWeight();
            if(!Objects.nonNull(p.getMedic()))
                this.medic_id = NO_VALUE;
            else
                this.medic_id = p.getMedic().getId();
        }
        else throw new UserNotFoundException();
    }
}
