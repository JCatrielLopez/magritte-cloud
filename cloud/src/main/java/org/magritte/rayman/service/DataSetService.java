package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.repository.DataSetRepository;
import org.magritte.rayman.exceptions.DataSetNotFoundException;
import org.magritte.rayman.rest.response.DataSetResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class DataSetService {

    @Autowired
    private DataSetRepository dataSetRepository;

    //TODO Adaptar metodos a la nueva BD. Implementar solo los metodos necesarios.

    public DataSet getDataSetById(@NotNull Integer id) {
        return dataSetRepository
                .findById(id)
                .orElseThrow(DataSetNotFoundException::new);
    }

    public List<DataSetResponse> getDataSets() {
        return dataSetRepository.findAll().stream()
                .map(DataSetResponse::new)
                .collect(Collectors.toList());
    }

    public List<DataSetResponse> getDataSetsByDataType(String dataType) {
        return dataSetRepository.findByDataType(dataType);
    }

    public List<DataSetResponse> getDataSetsByMeasurement(int measurement) {
        return dataSetRepository.findByMeasurement(measurement);
    }

    public List<DataSetResponse> getDataSetsByUnit(String unit) {
        return dataSetRepository.findByUnit(unit);
    }
}
