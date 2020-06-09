package org.magritte.rayman.data.repository;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Routine;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoutineRepository extends JpaRepository<Routine, Integer> {

    @NotNull
    Optional<Routine> findById(@NotNull Integer id);

    List<Routine> findAllByName(@NotNull String name);

    List<Routine> findAllByCreator(@NotNull String name);

}
