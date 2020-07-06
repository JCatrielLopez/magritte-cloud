package org.magritte.rayman.data.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @ToString.Include
    @JoinColumn(name = Routine.ID)
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
