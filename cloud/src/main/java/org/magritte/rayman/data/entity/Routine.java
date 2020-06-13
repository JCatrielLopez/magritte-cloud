package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Routine {

    public static final String ID = "idroutine";
    public static final String NAME_TABLE = "routine";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = Routine.ID)
    @EqualsAndHashCode.Include
    private Integer idRoutine;

    @ToString.Include
    @ManyToOne
    private User creator;

    @ToString.Include
    @Column(name = "name")
    private String name;

    @ToString.Include
    @Column(name = "totaltime")
    private int totalTime; // https://jdbc.postgresql.org/documentation/head/8-date-time.html

    @ToString.Include
    private int difficulty;

    @ManyToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<Accessory> accessories;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<Session> sessions;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<DataSet> patientRoutineDataSet;

    public Routine(User creator, String name, int totalTime, int difficulty) {
        this.creator = creator;
        this.name = name;
        this.totalTime = totalTime;
        this.difficulty = difficulty;
    }
}
