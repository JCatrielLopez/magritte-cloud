package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.magritte.rayman.data.entity.Data;

@Getter
@Setter
@NoArgsConstructor
public class DataResponse {

    private Integer idData;
    private String dataType;
    private String unit;

    public DataResponse(Data data) {
        this.idData = data.getIdData();
        this.dataType = data.getDataType();
        this.unit = data.getUnit();
    }
}
