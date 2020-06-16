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
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSetRequest {

    @Min(1)
    @NotNull
    @Getter
    private Integer idPatient;

    @Min(1)
    @NotNull
    @Getter
    private Integer idRoutine;

    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}$", message = "El formato de la fecha deberia ser: yyyy-MM-dd hh:mm:ss")
    @NotNull
    private String dateOfRealization;

    @Size(min = 3, max = 20)
    @NotNull
    @Getter
    private String dataType;

    @Size(min = 1, max = 3)
    @NotNull
    @Getter
    private String unit;

    @Min(0)
    @NotNull
    @Getter
    private int measurement;

    public DataSet toNewEntity(Patient patient, Routine routine) {
        return new DataSet(patient, routine, getDateOfRealization(), getDataType(), getUnit(), getMeasurement());
    }

    public Date getDateOfRealization() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(dateOfRealization);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
