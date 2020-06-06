package org.magritte.rayman.data.entity;

import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer idDataSet;

    @ToString.Include
    private String dataType;

    @ToString.Include
    private int measurement;

    @ToString.Include
    private String unit;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = "dataSet")
    private Set<PatientRoutineDataSet> patientRoutineDataSet;
}
