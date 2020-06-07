package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Routine {

    public static final String ID = "idRoutine";
    public static final String NAME_TABLE = "routine";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer idRoutine;

    @ToString.Include
    private String creatorName;

    @ToString.Include
    private String routineName;

    @ToString.Include
    private LocalTime totalTime; // https://jdbc.postgresql.org/documentation/head/8-date-time.html

    @ToString.Include
    private int difficulty;


    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<Accessory> accessories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<Session> sessions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<PatientRoutineDataSet> patientRoutineDataSet;
}
