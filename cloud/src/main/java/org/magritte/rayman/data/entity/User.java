package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class User {

    @Id
    @EqualsAndHashCode.Include
    @ToString.Include
    @GeneratedValue
    private Integer id;

    private String dni;
    private String name;
    private String lastname;
    private String password;
    private String email;
    private char clase; //Tipo de usuario(medico = m o paciente = p)

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Exercise> exercise; //esto creo qeu deberia estar en otra tabla.

    public User(String dni, String name, String lastname, String password,String email, char c){
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.clase = c;
    }
}



