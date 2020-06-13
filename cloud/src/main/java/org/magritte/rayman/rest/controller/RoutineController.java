package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.rest.request.RoutineRequest;
import org.magritte.rayman.rest.request.SessionRequest;
import org.magritte.rayman.rest.response.RoutineResponse;
import org.magritte.rayman.rest.response.SessionResponse;
import org.magritte.rayman.service.RoutineService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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

@RestController
@RequestMapping(name = "/routine")
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @GetMapping("/routine/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    // https://stackoverflow.com/questions/15359306/how-to-fetch-fetchtype-lazy-associations-with-jpa-and-hibernate-in-a-spring-cont
    public RoutineResponse getRoutine(@PathVariable Integer id) {
        Routine routine = routineService.getRoutineById(id);
        return new RoutineResponse(routine);
    }

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
        return routineService.getSessions(routine);
    }

    @GetMapping("/routines/name/{name}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<RoutineResponse> getRoutinesByName(@PathVariable String name) {
        return routineService.getRoutinesByName(name);
    }

    //TODO cambiar el creador por usuario. (Seria mejor implementarlo desde usercontroller y no desde rutina)
    @GetMapping("/routines/creator/{creator}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    @Transactional
    public List<RoutineResponse> getRoutinesByCreator(@PathVariable String creator) {
        return routineService.getRoutinesByCreator(creator);
    }

    @PostMapping("/routine")
    @ResponseStatus(code = HttpStatus.OK)
    public void addRoutine(@RequestBody @Valid RoutineRequest request) {
        routineService.save(request.toNewEntity());
    }

    @PostMapping("/routine/{idRoutine}/session")
    @ResponseStatus(code = HttpStatus.OK)
    public void addSession(@PathVariable Integer idRoutine, @RequestBody @Valid SessionRequest request) {
        Routine routine = routineService.getRoutineById(idRoutine);
        routineService.save(request.toNewEntity(routine));
    }
}
