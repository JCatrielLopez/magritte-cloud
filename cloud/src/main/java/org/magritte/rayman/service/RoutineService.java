package org.magritte.rayman.service;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Accessory;
import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.data.repository.AccessoryRepository;
import org.magritte.rayman.data.repository.RoutineRepository;
import org.magritte.rayman.data.repository.SessionRepository;
import org.magritte.rayman.exceptions.RoutineNotFoundException;
import org.magritte.rayman.rest.request.AccessoryRequest;
import org.magritte.rayman.rest.request.SessionRequest;
import org.magritte.rayman.rest.response.RoutineResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RoutineService {

    @Autowired
    private RoutineRepository routineRepository;

    @Autowired
    private SessionRepository sessionRepository;

    @Autowired
    private AccessoryRepository accessoryRepository;

    /**
     * Obtengo una rutina a partir de su id
     *
     * @param id filtra la rutina
     * @return Rutina
     */
    public Routine getRoutineById(@NotNull Integer id) {
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

    public List<RoutineResponse> getRoutinesByName(@NotNull String name) {
        return routineRepository.findByName(name);
    }

    public List<RoutineResponse> getRoutinesByCreator(@NotNull User user) {
        return routineRepository.findByUser(user);
    }

    public void save(@NotNull Routine routine) {
        routineRepository.save(routine);
    }

    public void delete(@NotNull Routine routine) {
        routineRepository.delete(routine);
    }

    public Optional<Routine> getRoutineByUserAndName(@NotNull User user, @NotNull String name) {
        return routineRepository.findByUserAndName(user, name);
    }

    public void save(@NotNull Routine routine, @NotNull Set<SessionRequest> requests) {
        requests.forEach(request -> {
            Optional<Session> optionalSession = sessionRepository.findByRoutineAndName(routine, request.getName());
            Session session = optionalSession.orElseGet(() -> request.toNewEntity(routine));
            routine.add(session);
            if (optionalSession.isEmpty()) sessionRepository.save(session);
        });
    }

    public void saveAccessory(@NotNull Routine routine, @NotNull Set<AccessoryRequest> requests) {
        requests.forEach(request -> {
            Optional<Accessory> optionalAccessory = accessoryRepository.findByName(request.getName());
            Accessory accessory = optionalAccessory.orElseGet(request::toNewEntity);
            accessory.add(routine);
            routine.add(accessory);
            if (optionalAccessory.isEmpty()) accessoryRepository.save(accessory);
        });
        routineRepository.save(routine);
    }
}
