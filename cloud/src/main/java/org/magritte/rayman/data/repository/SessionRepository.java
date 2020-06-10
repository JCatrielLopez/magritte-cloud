package org.magritte.rayman.data.repository;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SessionRepository extends JpaRepository<Session, Integer> {

    @NotNull
    Optional<Session> findById(@NotNull Integer id);

    List<Session> findByRoutine(@NotNull Routine routine);

}