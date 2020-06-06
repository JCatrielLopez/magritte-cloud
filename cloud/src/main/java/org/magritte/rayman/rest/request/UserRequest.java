package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public abstract class UserRequest {

    @ToString.Include
    private String dni;

    @ToString.Include
    private String name;

    @ToString.Include
    private String lastname;

    @ToString.Include
    private String email;
}
