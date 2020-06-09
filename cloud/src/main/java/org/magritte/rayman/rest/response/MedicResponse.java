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

    public MedicResponse(User user) {
        super(user);
        Medic medic = (Medic) user;
        this.specialization = medic.getSpecialization();
        this.license = medic.getLicense();
    }
}
