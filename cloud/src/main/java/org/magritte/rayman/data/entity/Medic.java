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
import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true, callSuper = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Medic extends User {

    public static final char MEDIC = 'M';
    public static final String NAME_TABLE = "medic";

    @ToString.Include
    private String specialization;

    @ToString.Include
    private int license;

    @ToString.Include
    private boolean availability;

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, mappedBy = NAME_TABLE)
    private Set<Patient> patients;

    public Medic(String dni, String nickname, String name, String lastname, String password,
                 String email, String nativeLanguage, String city, String specialization, int license, boolean available) {
        super(dni, nickname, name, lastname, password, email, MEDIC, nativeLanguage, city);
        this.specialization = specialization;
        this.license = license;
        this.availability = available;
        this.patients = new HashSet<>();
    }

    public Medic(String dni, String nickname, String name, String lastname,
                 String email, String nativeLanguage, String city, String specialization, int license, boolean available) {
        this(dni, nickname, name, lastname, null, email, nativeLanguage, city, specialization, license, available);
    }

    public void addPatient(Patient patient) {
        this.patients.add(patient);
    }
}