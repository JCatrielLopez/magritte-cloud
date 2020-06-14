package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.request.RoutineRequest;
import org.magritte.rayman.rest.request.SessionRequest;
import org.magritte.rayman.rest.response.RoutineResponse;
import org.magritte.rayman.rest.response.SessionResponse;
import org.magritte.rayman.service.RoutineService;
import org.magritte.rayman.service.SessionService;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping(name = "/routine")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    @GetMapping("/routines")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<RoutineResponse> getRoutines() {
        return routineService.getRoutines();
    }

    @GetMapping("/routine/{idRoutine}/sessions")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<SessionResponse> getSessions(@PathVariable Integer idRoutine) {
        Routine routine = routineService.getRoutineById(idRoutine);
        return sessionService.getSessions(routine);
    }

    @GetMapping("/routine/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    // https://stackoverflow.com/questions/15359306/how-to-fetch-fetchtype-lazy-associations-with-jpa-and-hibernate-in-a-spring-cont
    public RoutineResponse getRoutine(@PathVariable Integer id) {
        Routine routine = routineService.getRoutineById(id);
        return new RoutineResponse(routine);
    }

    @GetMapping("/routines/name/{name}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<RoutineResponse> getRoutinesByName(@PathVariable String name) {
        return routineService.getRoutinesByName(name);
    }

    @GetMapping("/routines/idUser/{idUser}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<RoutineResponse> getRoutinesByCreator(@PathVariable Integer idUser) {
        User user = userService.getUserById(idUser);
        return routineService.getRoutinesByCreator(user);
    }

    @PostMapping("/routine") //TODO
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void addRoutine(@RequestBody @Valid RoutineRequest request) {
        User user = userService.getUserById(request.getIdUser());
        Optional<Routine> optionalRoutine = routineService.getRoutineByUserAndName(user, request.getName());
        Routine routine = optionalRoutine.orElseGet(() -> {
            Routine r = request.toNewEntity(user);
            routineService.save(r);
            return r;
        });
        routineService.save(routine, request.getSessions());
    }

    @PostMapping("/routine/{idRoutine}/session")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void addSession(@PathVariable Integer idRoutine, @RequestBody @Valid SessionRequest request) {
        Routine routine = routineService.getRoutineById(idRoutine);
        routineService.save(routine, Set.of(request));
    }

    @DeleteMapping("/routine/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeRoutine(@PathVariable Integer id) {
        Routine routine = routineService.getRoutineById(id);
        routineService.delete(routine);
    }

    @DeleteMapping("/routine/{id}/session/{name}")
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public void removeSession(@PathVariable Integer id, @PathVariable String name) {
        Routine routine = routineService.getRoutineById(id);
        Set<Session> sessionSet = routine.getSessions().stream()
                .filter(session -> !Objects.equals(session.getName(), name))
                .collect(Collectors.toSet());
        routine.setSessions(sessionSet);
        routineService.save(routine);
    }
}
