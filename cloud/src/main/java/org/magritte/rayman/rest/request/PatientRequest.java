package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@NoArgsConstructor
public class PatientRequest extends UserRequest {

    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}$", message = "El formato de la fecha deberia ser: yyyy-MM-dd")
    @Size(min = 8, max = 10)
    @NotNull
    private String birthdate;

    @Pattern(regexp = "^[FM]$", message = "Los valores posibles son M o F")
    @Size(min = 1, max = 1)
    @NotNull
    private String gender;

    @Min(100)
    @Max(250)
    @NotNull
    @Getter
    private int height;

    @Min(40)
    @Max(300)
    @NotNull
    @Getter
    private float weight;

    @Min(1)
    @Getter
    private Integer medic_id;

    public Patient toNewEntity(Medic medic) {
        return new Patient(getDni(), getFirstname(), getLastname(), getPassword(), getEmail(),
                getBirthdate(), getGender(), getHeight(), getWeight(), medic);
    }

    public char getGender() {
        return gender.charAt(0);
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
