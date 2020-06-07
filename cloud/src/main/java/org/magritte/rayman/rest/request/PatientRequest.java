package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Patient;

import javax.validation.constraints.NotNull;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@NoArgsConstructor
public class PatientRequest extends UserRequest {

    @NotNull
    private String birthdate;

    @NotNull
    @Getter
    private char gender;

    @NotNull
    @Getter
    private int height;

    @NotNull
    @Getter
    private float weight;

    public Patient toNewEntity() {
        return new Patient(getDni(), getName(), getLastname(), getPassword(), getEmail(),
                getBirthdate(), getGender(), getHeight(), getWeight());
    }

    public Date getBirthdate() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(birthdate);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
