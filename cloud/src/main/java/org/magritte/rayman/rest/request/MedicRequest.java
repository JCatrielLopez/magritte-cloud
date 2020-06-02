package org.magritte.rayman.rest.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;

import javax.validation.constraints.NotNull;

@Getter
@Setter
@NoArgsConstructor
public class MedicRequest extends UserRequest {

    @NotNull
    private String specialization;

    @NotNull
    private int license;
//TODO
//    public Medic toNewEntity() {
//        return new Medic(getSpecialization(), getLicense());
//    }
}
