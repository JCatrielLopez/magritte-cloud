package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
public abstract class User {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "user_seq_gen")
    @SequenceGenerator(sequenceName = "user_iduser_seq", name = "user_seq_gen")
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;
    
    @ToString.Include
    @NotNull
    @Size(max = 9)
    private String dni;

    @Size(max = 15)
    @ToString.Include
    private String name;

    @Size(max = 15)
    @ToString.Include
    private String lastname;

    @Size(max = 13)
    @ToString.Include
    private String password;

    @Size(max = 50)
    @ToString.Include
    private String email;

    public User(String dni, String name, String lastname, String password, String email) {
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
    }

    //    @NonNull
//    private char userType;

}



