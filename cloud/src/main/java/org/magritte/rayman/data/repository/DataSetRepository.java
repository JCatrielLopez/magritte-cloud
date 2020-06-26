package org.magritte.rayman.data.repository;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Integer> {

    @NotNull
    Optional<DataSet> findById(@NotNull Integer id);

    List<DataSet> findByPatient(@NotNull Patient paciente);

    List<DataSet> findByPatientAndRoutine(@NotNull Patient paciente, @NotNull Routine routine);

    List<DataSet> findByPatientAndDateOfRealizationBetween(@NotNull Patient patient, @NotNull Date baseDate, @NotNull Date limitDate);

    List<DataSet> findByDataType(@NotNull String dataType);

    List<DataSet> findByMeasurement(int measurement);

    List<DataSet> findByUnit(@NotNull String unit);
}
