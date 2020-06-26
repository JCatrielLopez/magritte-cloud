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
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/dataset")
@Transactional(rollbackOn = Exception.class)
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
        return dataSetService.getDataSets()
                .stream()
                .map(DataSetResponse::new)
                .collect(Collectors.toList());
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
        return dataSetService.getDataSetByPatient(patient)
                .stream()
                .map(DataSetResponse::new)
                .collect(Collectors.toList());
    }

    @GetMapping("/dataset/patient/{idPatient}/routine/{idRoutine}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getDataSetByPatientAndRoutine(@PathVariable Integer idPatient, @PathVariable Integer idRoutine) {
        Patient patient = (Patient) userService.getUserById(idPatient);
        Routine routine = routineService.getRoutineById(idRoutine);
        return dataSetService.getDataSetByPatientAndRoutine(patient, routine)
                .stream()
                .map(DataSetResponse::new)
                .collect(Collectors.toList());
    }

    @PostMapping("/dataset")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public DataSetResponse addDataSet(@RequestBody @Valid DataSetRequest request){
        Patient patient = (Patient) userService.getUserById(request.getIdPatient());
        Routine routine = routineService.getRoutineById(request.getIdRoutine());
        DataSet dataSet = request.toNewEntity(patient, routine);
        dataSetService.save(dataSet);
        return new DataSetResponse(dataSet);
    }

    @GetMapping("/datasets/date")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getLatestDataset(@RequestParam Integer idPatient, @RequestParam String date){
        Patient patient = (Patient) userService.getUserById(idPatient);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try{
            Date limitDate = df.parse(date);
            return dataSetService.getLatestDataSetByDate(patient, limitDate).stream()
                    .map(DataSetResponse::new)
                    .collect(Collectors.toList());
        }
        catch(ParseException e){ e.getStackTrace();}
        return null;
    }

}
