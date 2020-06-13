package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class DataSetResponse {

    private Integer idDataSet;
    private String dataType;
    private int measurement;
    private String unit;

    public DataSetResponse(DataSet dataSet) {
        this.idDataSet = dataSet.getIdDataSet();
        this.dataType = dataSet.getDataType();
        this.measurement = dataSet.getMeasurement();
        this.unit = dataSet.getUnit();
    }
}
