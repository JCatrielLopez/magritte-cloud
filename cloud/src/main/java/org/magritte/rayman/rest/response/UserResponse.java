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
    private String nickname;
    private String firstname;
    private String lastname;
    private String email;
    private String user_type;
    private String nativeLanguage;
    private String city;

    public UserResponse(User user) {
        this.id = user.getId();
        this.dni = user.getDni();
        this.nickname = user.getNickname();
        this.firstname = user.getFirstname();
        this.lastname = user.getLastname();
        this.email = user.getEmail();
        this.user_type = String.valueOf(user.getUserType());
        this.nativeLanguage = user.getNativeLanguage();
        this.city = user.getCity();
    }
}
