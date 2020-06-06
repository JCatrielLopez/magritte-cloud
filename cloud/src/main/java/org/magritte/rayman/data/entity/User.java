package org.magritte.rayman.data.entity;

import lombok.*;
import org.magritte.rayman.data.repository.UserRepository;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Inheritance(strategy = InheritanceType.JOINED)
@Entity
@Table(name = "Usuarios", uniqueConstraints = @UniqueConstraint(columnNames = "dni"))
public class User {

    @Id
//    @SequenceGenerator(name = "seq")
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;
    
    @ToString.Include
    private String dni;

    @ToString.Include
    private String name;

    @ToString.Include
    private String lastname;

    @ToString.Include
    private String password;

    @ToString.Include
    @Email
    private String email;

    private char userType;

    public User(String dni, String name, String lastname, String password, String email, char c){
        this.dni = dni;
        this.name = name;
        this.lastname = lastname;
        this.password = password;
        this.email = email;
        this.userType = c;
    }

}



