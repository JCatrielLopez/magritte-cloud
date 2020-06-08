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
@Table(name = "dataset")
public class DataSet {

    public static final String NAME_TABLE = "dataSet";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "iddataset")
    @EqualsAndHashCode.Include
    private Integer idDataSet;

    @ToString.Include
    @Column(name = "datatype")
    private String dataType;

    @ToString.Include
    private int measurement;

    @ToString.Include
    private String unit;

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<PatientRoutineDataSet> patientRoutineDataSet;
}
