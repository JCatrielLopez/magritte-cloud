package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.time.LocalTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSetRequest {

    @Min(1)
    @NotNull
    private Integer idPatient;

    @Min(1)
    @NotNull
    private Integer idRoutine;

    @Size(min = 3, max = 20)
    @NotNull
    private String dataType;

    @Size(min = 1, max = 3)
    @NotNull
    private String unit;

    @Min(0)
    @NotNull
    private int measurement;

    public DataSet toNewEntity(Patient patient, Routine routine) {
        return new DataSet(patient, routine, LocalTime.now(), getDataType(), getUnit(), getMeasurement());
    }
}
