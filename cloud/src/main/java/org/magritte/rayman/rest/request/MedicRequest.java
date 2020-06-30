package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
public class MedicRequest extends UserRequest {

    @Size(max = 30)
    @NotNull
    private String specialization;

    @Min(0)
    @NotNull
    private int license;

    @NotNull
    private boolean availability;

    public Medic toNewEntity() {
        return new Medic(getDni(), getNickname(), getFirstname(), getLastname(), getPassword(),
                getEmail(), getNativeLanguage(), getCity(), getSpecialization(), getLicense(), isAvailability());
    }
}
