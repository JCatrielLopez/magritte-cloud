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
    private String firstname;
    private String lastname;
    private String email;
    private String user_type;

    public UserResponse(User user) {
        this.id = user.getId();
        this.dni = user.getDni();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.user_type = String.valueOf(user.getUserType());
    }
}
