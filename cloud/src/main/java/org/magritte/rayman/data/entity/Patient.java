package org.magritte.rayman.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Patient extends User {

    public static final char PATIENT = 'P';
    public static final String NAME_TABLE = "patient";
    public static final String ID = "idPatient";

    @ToString.Include
    private Date birthdate;

    @ToString.Include
    private char gender;

    @ToString.Include
    private int height;

    @ToString.Include
    private float weight;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Include
    private Medic medic;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
//    @JoinColumn(referencedColumnName = ID)
    private Set<PatientRoutineDataSet> patientRoutineDataSet;

    public Patient(String dni, String name, String lastname, String password, String email,
                   Date birthdate, char gender, int height, float weight) {
        super(dni, name, lastname, password, email, PATIENT);
        this.birthdate = birthdate;
        this.gender = gender;
        this.height = height;
        this.weight = weight;
    }

    public Patient(String dni, String name, String lastname, String email,
                   Date birthdate, char gender, int height, float weight) {
        this(dni, name, lastname, null, email, birthdate, gender, height, weight);
    }
}
