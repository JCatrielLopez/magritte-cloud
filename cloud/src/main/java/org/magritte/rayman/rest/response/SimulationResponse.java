package org.magritte.rayman.rest.response;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.HashSet;
import java.util.Set;


@Getter
@Setter
public class SimulationResponse{

    private Integer speed;
    private Integer status_code;
    private Set<DataSetSimulation> sensors;

    @Getter
    @Setter
    @NoArgsConstructor
    private class DataSetSimulation{
        private Integer id;
        private Integer measurement;
        private String unit;
    }

    public SimulationResponse() {
        this.speed = 5; // TODO Endpoint: /setSpeed
        this.status_code = 2; // TODO
        this.sensors = new HashSet<>();
        DataSetSimulation dr = new DataSetSimulation();
        dr.setMeasurement((int) (Math.random() * (0.6 * (211 - 0.64 * 45)) + 60));
        dr.setUnit("bpm");
        dr.setId(1);
        this.sensors.add(dr);
    }
}
