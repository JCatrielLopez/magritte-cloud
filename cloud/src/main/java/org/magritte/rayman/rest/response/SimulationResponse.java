package org.magritte.rayman.rest.response;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class SimulationResponse{

    private Integer speed;
    private Integer status_code;
    private Set<DataSetResponse> sensors;

    public SimulationResponse() {
        this.speed = 5; // TODO Endpoint: /setSpeed
        this.status_code = 2; // TODO
        this.sensors = new HashSet<>();
        DataSetResponse dr = new DataSetResponse();
        dr.setMeasurement((int) (Math.random() * (0.6 * (211 - 0.64 * 45)) + 60));
        dr.setUnit("bpm");
        dr.setIdDataSet(1);
        dr.setDataType("heart rate monitor");
        this.sensors.add(dr);
    }
}
