package org.magritte.rayman.data.entity;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.*;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class Session {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @ToString.Include
    @JoinColumn(name = "idroutine")
    private Routine routine;

    @ToString.Include
    private String name;

    @ToString.Include
    @Column(name = "numberofseries")
    private int numberOfSeries;

    @ToString.Include
    @Column(name = "numberofrepetitions")
    private int numberOfRepetitions;

    @ToString.Include
    @Column(name = "exercisetime")
    private int exerciseTime;

    @ToString.Include
    @Column(name = "breaktime")
    private int breakTime;

    public Session(Routine routine, String name, int numberOfSeries,
                   int numberOfRepetitions, int exerciseTime,
                   int breakTime) {
        this.routine = routine;
        this.name = name;
        this.numberOfSeries = numberOfSeries;
        this.numberOfRepetitions = numberOfRepetitions;
        this.exerciseTime = exerciseTime;
        this.breakTime = breakTime;
    }
}
