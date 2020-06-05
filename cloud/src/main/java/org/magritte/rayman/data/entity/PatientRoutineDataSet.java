package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class PatientRoutineDataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @ToString.Include
    @ManyToOne
    private Patient patient;

    @ToString.Include
    @ManyToOne
    private Routine routine;

    @ToString.Include
    @ManyToOne
    private DataSet dataSet;
}
