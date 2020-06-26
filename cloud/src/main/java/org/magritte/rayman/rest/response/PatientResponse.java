package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;

import java.util.Date;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

@Getter
@Setter
@NoArgsConstructor
public class PatientResponse extends UserResponse {

    public static final int NO_VALUE = -1;

    private Date birthdate;
    private char gender;
    private int height;
    private float weight;
    private Set<MedicResponse> medic;

    public PatientResponse(User user) {
        super(user);
        Patient patient = (Patient) user;
        this.birthdate = patient.getBirthdate();
        this.gender = patient.getGender();
        this.height = patient.getHeight();
        this.weight = patient.getWeight();
        this.medic = patient.getMedic().stream()
                    .map(MedicResponse::new)
                    .collect(Collectors.toSet());
    }
}
