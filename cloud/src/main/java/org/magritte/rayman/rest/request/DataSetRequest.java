package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.springframework.web.bind.annotation.GetMapping;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSetRequest {

    @NotNull
    @Getter
    private int idPaciente;

    @NotNull
    @Getter
    private int idRoutine;

    @Pattern(regexp = "^\\d{4}-\\d{1,2}-\\d{1,2}$", message = "El formato de la fecha deberia ser: yyyy-MM-dd")
    @Size(min = 8, max = 10)
    @NotNull
    private String fechaRealizacion;

    @Size(min = 3, max = 20)
    @Getter
    @NotNull
    private String dataType;

    @Min(0)
    @Getter
    @NotNull
    private int measurement;

    @Size(min = 1, max = 3)
    @Getter
    @NotNull
    private String unit;

    public DataSet toNewEntity(Patient patient, Routine routine) {
        return new DataSet(patient, routine, getFechaRealizacion(), getDataType(), getUnit(), getMeasurement());
    }

    public Date getFechaRealizacion() {
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            return dateFormat.parse(fechaRealizacion);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
