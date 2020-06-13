package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Table(name = "Usuario")
@Entity
public abstract class User {

    private static final String GENERATOR = "usuario_seq_gen";
    private static final String SEQUENCE = "usuario_id_seq";

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = GENERATOR)
    @SequenceGenerator(sequenceName = SEQUENCE, name = GENERATOR, allocationSize = 1)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @ToString.Include
    private String dni;

    @ToString.Include
    private String firstname;

    @ToString.Include
    private String lastname;

    @ToString.Include
    private String password;

    @ToString.Include
    private String email;

    @ToString.Include
    private char userType;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "creator")
    private Set<Routine> rutinasCreadas = new HashSet<>();

    public User(String dni, String name, String lastname, String password, String email, char userType) {
        this.dni = dni;
        this.firstname = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.userType = userType;
    }

    public User(String dni, String name, String lastname, String email, char userType) {
        this.dni = dni;
        this.firstname = name;
        this.lastname = lastname;
        this.email = email;
        this.userType = userType;
    }
}



