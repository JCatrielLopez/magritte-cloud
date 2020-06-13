package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.rest.request.DataSetRequest;
import org.magritte.rayman.rest.response.DataSetResponse;
import org.magritte.rayman.service.DataSetService;
import org.magritte.rayman.service.RoutineService;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/dataset")
public class DataSetController {

    @Autowired
    private DataSetService dataSetService;

    @Autowired
    private UserService userService;

    @Autowired
    private RoutineService routineService;

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

    @GetMapping("/dataset/patient/id")
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetByPatient(@PathVariable Integer id){
        Patient patient = (Patient) userService.getUserById(id);
        return dataSetService.getDataSetByPatient(patient);
    }

    @PostMapping("/dataset")
    @ResponseStatus(code = HttpStatus.OK)
    public void addDataSet(@RequestBody DataSetRequest dsr){
        Patient p = (Patient) userService.getUserById(dsr.getIdPaciente());
        Routine r = routineService.getRoutineById(dsr.getIdRoutine());
        dataSetService.save(dsr.toNewEntity(p,r));
    }



}
