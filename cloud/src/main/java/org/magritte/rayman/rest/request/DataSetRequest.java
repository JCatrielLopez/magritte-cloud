package org.magritte.rayman.rest.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class DataSetRequest {

    @Size(min = 3, max = 20)
    @NotNull
    private String dataType;

    @Min(0)
    @NotNull
    private int measurement;

    @Size(min = 1, max = 3)
    @NotNull
    private String unit;

    public DataSet toNewEntity() {
        return new DataSet(getDataType(), getMeasurement(), getUnit());
    }
}
