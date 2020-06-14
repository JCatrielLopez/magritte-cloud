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

import javax.transaction.Transactional;
import javax.validation.Valid;
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

    @GetMapping("/datasets")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSets() {
        return dataSetService.getDataSets();
    }

    @GetMapping("/dataset/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public DataSetResponse getDataSetById(@PathVariable Integer id) {
        DataSet dataSetById = dataSetService.getDataSetById(id);
        return new DataSetResponse(dataSetById);
    }

    @GetMapping("/dataset/patient/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetByPatient(@PathVariable Integer id){
        Patient patient = (Patient) userService.getUserById(id);
        return dataSetService.getDataSetByPatient(patient);
    }

    @GetMapping("/dataset/patient/{idPatient}/routine/{idRoutine}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetByPatientAndRoutine(@PathVariable Integer idPatient, @PathVariable Integer idRoutine) {
        Patient patient = (Patient) userService.getUserById(idPatient);
        Routine routine = routineService.getRoutineById(idRoutine);
        return dataSetService.getDataSetByPatientAndRoutine(patient, routine);
    }

    @PostMapping("/dataset")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void addDataSet(@RequestBody @Valid DataSetRequest request){
        Patient patient = (Patient) userService.getUserById(request.getIdPatient());
        Routine routine = routineService.getRoutineById(request.getIdRoutine());
        dataSetService.save(request.toNewEntity(patient, routine));
    }

}
