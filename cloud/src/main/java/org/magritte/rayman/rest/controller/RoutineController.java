package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.Routine;
import org.magritte.rayman.data.entity.Session;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.request.AccessoriesRequest;
import org.magritte.rayman.rest.request.RoutineRequest;
import org.magritte.rayman.rest.request.SessionsRequest;
import org.magritte.rayman.rest.response.*;
import org.magritte.rayman.service.RoutineService;
import org.magritte.rayman.service.SessionService;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

/**
 * EndPoints para atender peticiones asociadas a rutinas
 */
@RestController
@RequestMapping(name = "/routine")
@Transactional(rollbackOn = Exception.class)
public class RoutineController {

    @Autowired
    private RoutineService routineService;

    @Autowired
    private SessionService sessionService;

    @Autowired
    private UserService userService;

    /**
     * Obtiene todas las rutines.
     *
     * @return Una lista con todas las rutinas del sistema, o una lista vacia si no existe ninguna.
     */
    @GetMapping("/routines")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<RoutineResponse> getRoutines() {
        return routineService.getRoutines();
    }

    /**
     * Obtiene todas las sesiones de una rutina.
     *
     * @param idRoutine Id de la rutina.
     * @return Una lista con todas las sesiones, o una lista vacia si no existe ninguna. En caso de que
     * la rutina no exista retorna NOT FOUND.
     */
    @GetMapping("/routine/{idRoutine}/sessions")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<SessionResponse> getSessions(@PathVariable Integer idRoutine) {
        Routine routine = routineService.getRoutineById(idRoutine);
        return sessionService.getSessions(routine);
    }

    /**
     * Obtiene la informacion de una rutina.
     *
     * @param id Id de la rutina.
     * @return La informacion de la rutina, o si no existe NOT FOUND.
     */
    @GetMapping("/routine/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public RoutineResponse getRoutine(@PathVariable Integer id) {
        Routine routine = routineService.getRoutineById(id);
        return new RoutineResponse(routine);
    }

    /**
     * Obtiene la rutina con el nombre dado.
     *
     * @param name Nombre de las rutinas.
     * @return Si existe retorna la rutina, sino NOT FOUND.
     */
    @GetMapping("/routines/name/{name}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<RoutineResponse> getRoutinesByName(@PathVariable String name) {
        return routineService.getRoutinesByName(name);
    }

    /**
     * Obtiene todas las rutinas asociadas al id del creador dado.
     *
     * @param idUser Id del usuario creador de las rutinas.
     * @return La lista de rutinas, o NOT FOUND si no encuentra el creador.
     */
    @GetMapping("/routines/idUser/{idUser}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<RoutineResponse> getRoutinesByCreator(@PathVariable Integer idUser) {
        User user = userService.getUserById(idUser);
        return routineService.getRoutinesByCreator(user);
    }

    /**
     * Obtiene todas las rutinas asociadas al nickname del creador dado.
     *
     * @param nickname Nickname del usuario creador de las rutinas.
     * @return La lista de rutinas, o NOT FOUND si no encuentra el creador.
     */
    @GetMapping("/routines/userNickname")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<RoutineResponse> getRoutinesByCreatorNickname(@RequestParam String nickname) {
        User user = userService.getUserByNickname(nickname);
        return routineService.getRoutinesByCreator(user);
    }

    /**
     * Obtiene la rutina con nombre y usuario creador dados.
     *
     * @param idUser Id del usuario creador.
     * @param name   Nombre de la rutina.
     * @return La rutina solicitada, o si no existe NOT FOUND.
     */
    @GetMapping("/routines/idUserAndName")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public RoutineResponse getRoutinesByCreatorAndName(@RequestParam Integer idUser, @RequestParam String name) {
        User user = userService.getUserById(idUser);
        Optional<Routine> routine = routineService.getRoutineByUserAndName(user, name);
        Routine r = routine.orElseThrow(NoSuchElementException::new);
        return new RoutineResponse(r);
    }

    /**
     * Obtiene la rutina con nickname de usuario y nombre dados.
     *
     * @param nickname Nickname del usuario creador.
     * @param name     Nombre de la rutina.
     * @return La rutina solicitada, o si no existe NOT FOUND.
     */
    @GetMapping("/routines/userNicknameAndName")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public RoutineResponse getRoutinesByNicknameAndName(@RequestParam String nickname, @RequestParam String name) {
        User user = userService.getUserByNickname(nickname);
        Optional<Routine> routine = routineService.getRoutineByUserAndName(user, name);
        Routine r = routine.orElseThrow(NoSuchElementException::new);
        return new RoutineResponse(r);
    }

    /**
     * Agrega una rutina.
     *
     * @param request Informacion necesaria para agregar la rutina.
     * @return La rutina creada.
     */
    @PostMapping("/routine")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public RoutineResponse addRoutine(@RequestBody @Valid RoutineRequest request) {
        User user = userService.getUserById(request.getIdUser());
        Optional<Routine> optionalRoutine = routineService.getRoutineByUserAndName(user, request.getName());
        Routine routine = optionalRoutine.orElseGet(() -> {
            Routine r = request.toNewEntity(user);
            routineService.save(r);
            return r;
        });
        routineService.save(routine, request.getSessions());
        return new RoutineResponse(routine);
    }

    /**
     * Agrega una sesion a una rutina.
     *
     * @param idRoutine Id de la rutina para agregar la sesion.
     * @param request   Informacion de la sesion a ser agregada a la rutina.
     * @return La informacion de la sesion agregada. En caso de que la rutina no exista NOT FOUND.
     */
    @PostMapping("/routine/{idRoutine}/sessions")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public SessionsResponse addSession(@PathVariable Integer idRoutine, @RequestBody @Valid SessionsRequest request) {
        Routine routine = routineService.getRoutineById(idRoutine);
        Set<SessionResponse> sessions = routineService.save(routine, request.getSessions());
        return new SessionsResponse(sessions);
    }

    /**
     * Agrega un accesorio a una rutina.
     *
     * @param idRoutine Id de la rutina
     * @param request   Informacion del accesorio a agregar a la rutina.
     * @return La informacion del accesorio agregado. En caso de que la rutina no exista NOT FOUND.
     */
    @PostMapping("/routine/{idRoutine}/accessories")
    @ResponseStatus(code = HttpStatus.OK)
    public AccessoriesResponse addAccessory(@PathVariable Integer idRoutine, @RequestBody @Valid AccessoriesRequest request) {
        Routine routine = routineService.getRoutineById(idRoutine);
        Set<AccessoryResponse> accessories = routineService.saveAccessory(routine, request.getAccessories());
        return new AccessoriesResponse(accessories);
    }

    /**
     * Elimina una rutina.
     *
     * @param id Id de la rutina a eliminar.
     */
    @DeleteMapping("/routine/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeRoutine(@PathVariable Integer id) {
        Routine routine = routineService.getRoutineById(id);
        routineService.delete(routine);
    }

    /**
     * Elimina una sesion con nombre dado de una rutina.
     *
     * @param id   Id de la rutina.
     * @param name Nombre de la sesion.
     */
    @DeleteMapping("/routine/{id}/session/{name}")
    @ResponseStatus(code = HttpStatus.OK)
    public void removeSession(@PathVariable Integer id, @PathVariable String name) {
        Routine routine = routineService.getRoutineById(id);
        Set<Session> sessionSet = routine.getSessions().stream()
                .filter(session -> !Objects.equals(session.getName(), name))
                .collect(Collectors.toSet());
        routine.setSessions(sessionSet);
        routineService.save(routine);
    }
}
