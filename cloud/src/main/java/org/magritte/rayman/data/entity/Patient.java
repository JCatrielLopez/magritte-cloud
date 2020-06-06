package org.magritte.rayman.data.entity;

import lombok.*;
import org.magritte.rayman.data.repository.UserRepository;

import javax.persistence.*;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Patient extends User {

    @ToString.Include
    private Date birthdate;

    @ToString.Include
    private char gender;

    @ToString.Include
    private int height;

    @ToString.Include
    private float weight;

    @ManyToMany(fetch = FetchType.LAZY)
    @ToString.Include
    private Set<Medic> medics;

//    @ManyToOne(fetch = FetchType.LAZY)
//    @ToString.Include
//    private Medic medic;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "patient")
    private Set<PatientRoutineDataSet> patientRoutineDataSets;

    public Patient(String dni, String name, String lastname, String password, String email,
                   Date birthdate, char gender, int height, float weight) {
        super(dni, name, lastname, password, email, 'p');
        this.birthdate = birthdate;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }
}
