package org.magritte.rayman.rest.controller;

import org.apache.commons.math3.stat.descriptive.summary.Sum;
import org.magritte.rayman.data.entity.Data;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.rest.request.DataSetRequest;
import org.magritte.rayman.rest.response.*;
import org.magritte.rayman.service.DataService;
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
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    @Autowired
    private DataService dataService;

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
        Data data = dataService.getDataById(request.getIdData());
        DataSet dataSet = request.toNewEntity(patient, routine, data);
        dataSetService.save(dataSet);
        return new DataSetResponse(dataSet);
    }

    @GetMapping("/datasets/date")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<DataSetResponse> getLatestDataset(@RequestParam Integer idPatient, @RequestParam String date) {
        Patient patient = (Patient) userService.getUserById(idPatient);
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        try {
            Date limitDate = df.parse(date);
            return dataSetService.getLatestDataSetByDate(patient, limitDate).stream()
                    .map(DataSetResponse::new)
                    .collect(Collectors.toList());
        } catch (ParseException e) {
            e.getStackTrace();
        }
        return null;
    }

    private SummaryResponse getSummary(Collection<DataSet> collection ){
        SummaryResponse out = new SummaryResponse();

        double[] values_array = collection
                .stream()
                .mapToDouble(DataSet::getMeasurement)
                .toArray();

        out.setAvg(StatUtils.mean(values_array));
        out.setMax(StatUtils.max(values_array));
        out.setMin(StatUtils.min(values_array));
        out.setVariance(StatUtils.variance(values_array, out.getAvg()));

        return out;
    }

    /**
     * Get a summary of the patient data filtered by unit (all routines)
     *
     * @return SummaryResponse
     */
    @GetMapping("/stats/patient/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public SummaryResponse getPatientSummary(@PathVariable Integer id, @RequestParam String unit, @RequestParam Integer days) {

        Date today = new Date();
        Predicate<DataSet> pr_max_time = a->(!(TimeUnit.DAYS.convert(Math.abs(today.getTime() - a.getDateOfRealization().getTime()), TimeUnit.MILLISECONDS) < days));
        Predicate<DataSet> pr = a->(!a.getData().getUnit().equalsIgnoreCase(unit));

        Patient patient = (Patient) userService.getUserById(id);

        List<DataSet> patient_dataset = dataSetService.getDataSetByPatient(patient);
        patient_dataset.removeIf(pr);
        patient_dataset.removeIf(pr_max_time);

        SummaryResponse sum = this.getSummary(patient_dataset);
        SummaryPatientResponse out = new SummaryPatientResponse();

        out.setIdPatient(id);
        out.setUnit(unit);
        out.setAvg(sum.getAvg());
        out.setMax(sum.getMax());
        out.setMin(sum.getMin());
        out.setVariance(sum.getVariance());
        out.setDatasets(patient_dataset.stream().map(DataSetResponse::new).collect(Collectors.toList()));

        return out;
    }

    /**
     * Get a summary of the patients data filtered by routine
     *
     * @return SummaryResponse
     */
    @GetMapping("/stats/routine/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public SummaryResponse getRoutineSummary(@PathVariable Integer id, @RequestParam String unit, @RequestParam Integer days) {

        Date today = new Date();
        Predicate<DataSet> pr_max_time = a->(!(TimeUnit.DAYS.convert(Math.abs(today.getTime() - a.getDateOfRealization().getTime()), TimeUnit.MILLISECONDS) < days));
        Predicate<DataSet> pr = a->(!a.getData().getUnit().equalsIgnoreCase(unit));
        Routine routine = routineService.getRoutineById(id);

        Set<DataSet> routine_dataset = routine.getDataSets();
        routine_dataset.removeIf(pr);
        routine_dataset.removeIf(pr_max_time);

        SummaryResponse sum = this.getSummary(routine_dataset);
        SummaryRoutineResponse out = new SummaryRoutineResponse();

        out.setIdRoutine(id);
        out.setUnit(unit);
        out.setAvg(sum.getAvg());
        out.setMax(sum.getMax());
        out.setMin(sum.getMin());
        out.setVariance(sum.getVariance());
        out.setDatasets(routine_dataset.stream().map(DataSetResponse::new).collect(Collectors.toList()));
        return out;
    }

    /**
     * Get a summary of all the patients linked to a doctor
     *
     * @return List of SummaryResponse
     */
    @GetMapping("/stats/medic/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public HashSet<SummaryResponse> getStatsByDoctor(@PathVariable Integer id, @RequestParam String unit, @RequestParam Integer days) {

        List<PatientResponse> patients = userService.getPatientsFromMedic(id);
        HashSet<SummaryResponse> out = new HashSet<>();

        for(PatientResponse p: patients){
            out.add(getPatientSummary(p.getId(), unit, days));
        }

        return out;

    }

    /**
     * Get a summary of all the patients
     *
     * @return SummaryResponse
     */

    @GetMapping("/stats/global")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public SummaryResponse getStatsGlobal(@RequestParam String unit) {

        Predicate<DataSet> pr = a->(!a.getData().getUnit().equalsIgnoreCase(unit));
        List<DataSet> all_datasets = dataSetService.getDataSets();
        all_datasets.removeIf(pr);

        return this.getSummary(all_datasets);

    }

}
