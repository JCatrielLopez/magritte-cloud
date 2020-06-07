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
import javax.persistence.OneToMany;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
public class DataSet {

    public static final String NAME_TABLE = "dataSet";

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

    @OneToMany(fetch = FetchType.LAZY, cascade = CascadeType.ALL, mappedBy = NAME_TABLE)
    private Set<PatientRoutineDataSet> patientRoutineDataSet;
}
