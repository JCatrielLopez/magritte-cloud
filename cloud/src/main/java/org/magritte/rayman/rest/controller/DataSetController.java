package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.rest.request.DataSetRequest;
import org.magritte.rayman.rest.response.DataSetResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.StatsResponse;
import org.magritte.rayman.service.DataSetService;
import org.magritte.rayman.service.RoutineService;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.naming.spi.DirObjectFactory;
import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.HashSet;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.DoubleStream;
import org.apache.commons.math3.stat.StatUtils;
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

    /**
     * Get a summary of the patient data filtered by unit
     *
     * @return StatsResponse
     */
    @GetMapping("/stats/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public StatsResponse getStatsByUnit(@PathVariable Integer id, @RequestParam String unit) {

        Predicate<DataSet> pr = a->(!a.getUnit().equalsIgnoreCase(unit));
        Patient patient = (Patient) userService.getUserById(id);

        List<DataSet> patient_dataset = dataSetService.getDataSetByPatient(patient);
        List<DataSet> global_dataset = dataSetService.getDataSets();
        patient_dataset.removeIf(pr);
        global_dataset.removeIf(pr);

        double[] global_array = global_dataset
                .stream()
                .mapToDouble(DataSet::getMeasurement)
                .toArray();

        double[] patient_array = patient_dataset
                .stream()
                .mapToDouble(DataSet::getMeasurement)
                .toArray();


        StatsResponse out = new StatsResponse();

        out.setPatient(id);
        out.setUnit(unit);
        out.setMean(StatUtils.mean(patient_array));
        out.setMax(StatUtils.max(patient_array));
        out.setMin(StatUtils.min(patient_array));
        out.setVariance(StatUtils.percentile(patient_array, out.getMean()));

        out.setGlobal_mean(StatUtils.mean(global_array));
        out.setGlobal_max(StatUtils.max(global_array));
        out.setGlobal_min(StatUtils.min(global_array));
        out.setGlobal_mode(StatUtils.mode(global_array));
        out.setGlobal_variance(StatUtils.variance(global_array, out.getGlobal_mean()));

        return out;
    }

    /**
     * Get a summary of all the patients linked to a doctor
     *
     * @return List of StatsResponse
     */
    @GetMapping("/stats/medic/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public HashSet<StatsResponse> getStatsByDoctor(@PathVariable Integer id, @RequestParam String unit) {

        List<PatientResponse> patients = userService.getPatientsFromMedic(id);
        HashSet<StatsResponse> out = new HashSet<>();

        for(PatientResponse p: patients){
            out.add(getStatsByUnit(p.getId(), unit));
        }

        return out;

    }

}
