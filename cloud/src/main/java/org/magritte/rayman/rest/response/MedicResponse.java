package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;

@Getter
@Setter
@NoArgsConstructor
public class MedicResponse extends UserResponse {

    private String specialization;
    private int license;

    public MedicResponse(Medic medic) {
        super(medic);
        this.specialization = medic.getSpecialization();
        this.license = medic.getLicense();
    }
}
