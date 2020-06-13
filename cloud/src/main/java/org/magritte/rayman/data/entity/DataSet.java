package org.magritte.rayman.data.entity;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "patientroutinedataset")
public class DataSet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(nullable = false, name = "iddataset")
    @EqualsAndHashCode.Include
    private Integer idDataSet;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "idpatient")
    private Patient patient;

    @ToString.Include
    @ManyToOne
    @JoinColumn(name = "idroutine")
    private Routine routine;

    @ToString.Include
    private Date fecha_realizacion;

    @ToString.Include
    @Column(name = "datatype")
    private String dataType;

    @ToString.Include
    private String unit;

    @ToString.Include
    private int measurement;

    public DataSet(Patient patient, Routine routine, Date fecha_realizacion, String dataType, String unit, int measurement){
        this.patient = patient;
        this.routine = routine;
        this.fecha_realizacion = fecha_realizacion;
        this.dataType = dataType;
        this.unit = unit;
        this.measurement = measurement;
    }

   }
