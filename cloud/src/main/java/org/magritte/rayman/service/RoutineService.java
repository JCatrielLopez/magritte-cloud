package org.magritte.rayman.service;

import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.RoutineRepository;
import org.magritte.rayman.data.repository.SessionRepository;
import org.magritte.rayman.exceptions.RoutineNotFoundException;
import org.magritte.rayman.rest.response.RoutineResponse;
import org.magritte.rayman.rest.response.SessionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private SessionRepository sessionRepository;

    /**
     * Obtengo una rutina a partir de su id
     *
     * @param id filtra la rutina
     * @return Rutina
     */
    public Routine getRoutineById(Integer id) {
        return routineRepository
                .findById(id)
                .orElseThrow(RoutineNotFoundException::new);
    }

    /**
     * Obtengo todas las rutinas
     *
     * @return Lista de rutinas
     */
    public List<RoutineResponse> getRoutines() {
        return routineRepository.findAll().stream()
                .map(RoutineResponse::new)
                .collect(Collectors.toList());
    }

    /**
     * Obtengo las sesiones a partir de un id de rutina
     *
     * @param routine filtrar la rutina
     * @return lista de sesiones
     */
    public List<SessionResponse> getSessions(Routine routine) {
        return sessionRepository.findByRoutine(routine);
    }

    public List<RoutineResponse> getRoutinesByName(String name) {
        return routineRepository.findAllByName(name);
    }

    public List<RoutineResponse> getRoutinesByCreator(User name) {
        return routineRepository.findAllByCreator(name);
    }

    public void save(Routine routine) {
        routineRepository.save(routine);
    }

    public void save(Session session) {
        sessionRepository.save(session);
    }
}
