package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.time.LocalTime;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Routine {

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

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Accessory> accessories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Set<Session> sessions;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(referencedColumnName = "idRoutine")
    private Set<PatientRoutineDataSet> patientRoutineDataSet;
}
