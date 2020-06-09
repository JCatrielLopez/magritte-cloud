package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.AssertTrue;
import javax.validation.constraints.Email;
import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserRequest {

    @Size(min = 7, max = 9)
    @Pattern(regexp = "\\d{7,9}")
    @NotNull
    private String dni;

    @Size(max = 15)
    @NotNull
    private String firstname;

    @Size(max = 15)
    @NotNull
    private String lastname;

    @Size(max = 13)
    @NotNull
    private String password;

    @Size(max = 50)
    @Email
    @NotNull
    private String email;

}
