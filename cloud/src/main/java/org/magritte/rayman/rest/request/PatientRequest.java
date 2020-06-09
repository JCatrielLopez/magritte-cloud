package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Patient;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Digits;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import javax.validation.valueextraction.ExtractedValue;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@NoArgsConstructor
public class PatientRequest extends UserRequest {

    @Pattern(regexp = "^\\d{4}-\\d{2}-\\d{2}$", message = "El formato de la fecha deberia ser: yyyy-MM-dd")
    @Size(min = 10, max = 10)
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

    public Patient toNewEntity() {
        return new Patient(getDni(), getFirstname(), getLastname(), getPassword(), getEmail(),
                getBirthdate(), getGender(), getHeight(), getWeight());
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
