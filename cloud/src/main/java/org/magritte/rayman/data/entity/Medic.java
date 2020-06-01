package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Medic extends User {

    @Size(max = 30)
    @ToString.Include
    private String specialization;

    @ToString.Include
    private int license;

    public Medic(String dni, String name, String lastname, String password,
                 String email, String specialization, int license) {
        super(dni, name, lastname, password, email);
        this.specialization = specialization;
        this.license = license;
    }

}