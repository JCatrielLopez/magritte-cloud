package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
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
    private Routine routine;

    @ToString.Include
    private String name;

    @ToString.Include
    private int numberOfSeries;

    @ToString.Include
    private int numberOfRepetitions;

    @ToString.Include
    private LocalTime exerciseTime;

    @ToString.Include
    private LocalTime breakTime;
}
