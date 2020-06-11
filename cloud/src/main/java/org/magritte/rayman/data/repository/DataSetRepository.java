package org.magritte.rayman.data.repository;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.rest.response.DataSetResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Integer> {

    @NotNull
    Optional<DataSet> findById(@NotNull Integer id);

    List<DataSetResponse> findByDataType(@NotNull String dataType);

    List<DataSetResponse> findByMeasurement(int measurement);

    List<DataSetResponse> findByUnit(String unit);
}
