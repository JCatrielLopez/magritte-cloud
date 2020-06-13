package org.magritte.rayman.data.repository;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.DataSet;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.rest.response.DataSetResponse;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface DataSetRepository extends JpaRepository<DataSet, Integer> {

    @NotNull
    Optional<DataSet> findById(@NotNull Integer id);

    List<DataSet> findAllByPatient(@NotNull Patient paciente);

    //CHEQUEAR CONVENCION DE COMO IMPLEMENTAR CUANDO SE BUSCA POR MAS DE UN CAMPO.
//    Set<DataSet> findByPatient(@NotNull Patient paciente, @NotNull Routine routine);
}
