package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.repository.DataSetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;

@Service
public class DataSetService {

    @Autowired
    private DataSetRepository dataSetRepository;

    public DataSet getDataSetById(@NotNull Integer id) {
        return dataSetRepository
                .findById(id)
                .orElseThrow(NoSuchElementException::new);
    }

    public List<DataSet> getDataSets() {
        return dataSetRepository.findAll();
    }

    public void save(@NotNull DataSet dataset) {
        Patient patient = dataset.getPatient();
        Routine routine = dataset.getRoutine();
        patient.addExercise(dataset);
        routine.addRealization(dataset);
        dataSetRepository.save(dataset);
    }

    public List<DataSet> getDataSetByPatient(@NotNull Patient patient) {
        return dataSetRepository.findByPatient(patient);
    }

    public List<DataSet> getDataSetByPatientAndRoutine(@NotNull Patient patient, @NotNull Routine routine) {
        return dataSetRepository.findByPatientAndRoutine(patient, routine);
    }
}
