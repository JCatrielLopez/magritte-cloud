package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class MedicResponse extends UserResponse {

    private String specialization;

    private int license;

    public MedicResponse(User medic) {
        super(medic);
        if (medic.getUserType() == 'M') {
            Medic m = (Medic) medic;
            this.specialization = m.getSpecialization();
            this.license = m.getLicense();
        }
    }
}
