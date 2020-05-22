package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.User;

@Getter
@Setter
@NoArgsConstructor
public class UserResponse {

    private Integer id;
    private String dni;
    private String name;
    private String lastname;
//    private Set<ExerciseResponse> exercises//TODO este no tiene que mostrar los datos del usuario LOOP ALERT!


    public UserResponse(User user) {
        this.id = user.getId();
        this.dni = user.getDni();
        this.name = user.getName();
        this.lastname = user.getLastname();
        //this.exericises = //mapear exercises a Set<ExerciseResponse>
    }
}
