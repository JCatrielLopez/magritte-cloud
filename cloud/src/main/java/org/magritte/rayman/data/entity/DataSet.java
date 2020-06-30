package org.magritte.rayman.data.entity;

import lombok.AllArgsConstructor;
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
import javax.persistence.Table;
import java.util.Date;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@ToString(onlyExplicitlyIncluded = true)
@Entity
@Table(name = "dataset")
public class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false)
    @EqualsAndHashCode.Include
    private Integer id;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idpatient")
    private Patient patient;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "idroutine")
    private Routine routine;

    @ToString.Include
    @Column(name = "dateofrealization")
    private Date dateOfRealization;

    @ToString.Include
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "iddata")
    private Data data;

    @ToString.Include
    @Column(name = "dataofdata")
    private Date dateOfData;

    @ToString.Include
    private int measurement;

    public DataSet(Patient patient, Routine routine, Data data, Date dateOfRealization, int measurement, Date dateOfData) {
        this.patient = patient;
        this.routine = routine;
        this.dateOfRealization = dateOfRealization;
        this.data = data;
        this.dateOfData = dateOfData;
        this.measurement = measurement;
    }

}
