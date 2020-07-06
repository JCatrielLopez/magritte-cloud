package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Data;
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

    @Min(1)
    @NotNull
    @Getter
    private Integer idData;

    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2} \\d{2}:\\d{2}:\\d{2}$", message = "El formato de la fecha deberia ser: yyyy-MM-dd hh:mm:ss")
    @NotNull
    private String dateOfData;

    @Min(0)
    @NotNull
    @Getter
    private int measurement;

    public DataSet toNewEntity(Patient patient, Routine routine, Data data) {
        return new DataSet(patient, routine, data, getDateOfRealization(), getMeasurement(), getDateOfData());
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

    public Date getDateOfData() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        try {
            return dateFormat.parse(dateOfData);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
