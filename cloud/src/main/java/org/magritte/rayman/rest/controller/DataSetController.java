package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.rest.response.DataSetResponse;
import org.magritte.rayman.service.DataSetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping(name = "/dataset")
public class DataSetController {

    @Autowired
    private DataSetService dataSetService;

    //TODO implementar get de un paciente y una rutina.
    // Adaptar a la nueva BD implementando solo los metodos que sean necesarios.
    // GET historial de un paciente.
    // GET historial de un paciente que realizo cierta rutina.
    // POST nuevo conjunto de datos.

    @GetMapping("/datasets")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSets() {
        return dataSetService.getDataSets();
    }

    @GetMapping("/dataset/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public DataSetResponse getDataSetById(@PathVariable Integer id) {
        DataSet dataSetById = dataSetService.getDataSetById(id);
        return new DataSetResponse(dataSetById);
    }

    @GetMapping("/datasets/datatype/{dataType}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetsByDataType(@PathVariable String dataType) {
        return dataSetService.getDataSetsByDataType(dataType);
    }

    @GetMapping("/datasets/measurement/{measurement}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetsByMeasurement(@PathVariable int measurement) {
        return dataSetService.getDataSetsByMeasurement(measurement);
    }

    @GetMapping("/datasets/unit/{unit}")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetsByUnit(@PathVariable String unit) {
        return dataSetService.getDataSetsByUnit(unit);
    }

}
