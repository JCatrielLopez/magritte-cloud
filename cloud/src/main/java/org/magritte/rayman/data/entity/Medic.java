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

    @JsonIgnore
    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<Patient> patients;

    public Medic(String dni, String name, String lastname, String password,
                 String email, String specialization, int license) {
        super(dni, name, lastname, password, email, MEDIC);
        this.specialization = specialization;
        this.license = license;
    }

    public Medic(String dni, String name, String lastname,
                 String email, String specialization, int license) {
        this(dni, name, lastname, null, email, specialization, license);
    }
}