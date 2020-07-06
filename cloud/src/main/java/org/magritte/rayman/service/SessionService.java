package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.data.repository.SessionRepository;
import org.magritte.rayman.rest.response.SessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SessionService {

    @Autowired
    private SessionRepository sessionRepository;

    public Optional<Session> getSessionByRoutineAndName(@NotNull Routine routine, @NotNull String name) {
        return sessionRepository.findByRoutineAndName(routine, name);
    }

    /**
     * Obtengo las sesiones a partir de un id de rutina
     *
     * @param routine filtrar la rutina
     * @return lista de sesiones
     */
    public List<SessionResponse> getSessions(@NotNull Routine routine) {
        return sessionRepository.findByRoutine(routine);
    }

    public void save(@NotNull Session session) {
        sessionRepository.save(session);
    }
}
