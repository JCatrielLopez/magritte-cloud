package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Data;
import org.magritte.rayman.data.repository.DataRepository;
import org.magritte.rayman.exceptions.DataNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class DataService {

    @Autowired
    private DataRepository dataRepository;

    public Data getDataById(@NotNull Integer idData) {
        return dataRepository
                .findById(idData)
                .orElseThrow(DataNotFoundException::new);
    }

    public void delete(@NotNull Data data) {
        dataRepository.delete(data);
    }

    public Optional<Data> getDataByDataType(@NotNull String dataType) {
        return dataRepository.getDataByDataType(dataType);
    }

    public void save(@NotNull Data newData) {
        dataRepository.save(newData);
    }
}
