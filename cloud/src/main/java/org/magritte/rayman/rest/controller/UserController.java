package org.magritte.rayman.rest.controller;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.magritte.rayman.exceptions.UserNotValidException;
import org.magritte.rayman.rest.request.MedicRequest;
import org.magritte.rayman.rest.request.PatientRequest;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.util.Calendar;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.magritte.rayman.data.entity.Patient.PATIENT;

/**
 * EndPoints
 */
@RestController
@RequestMapping(name = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene un usuario a partir de su id
     *
     * @param id id del usuario a filtrar
     * @return usuario: medico o paciente
     */
    @GetMapping("/user/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return user.getUserType() == PATIENT ? new PatientResponse(user) : new MedicResponse(user);
    }

    /**
     * Sign in user
     *
     * @param dni      user identifier
     * @param password password corresponding to the user
     * @return User: medic or patient depending on the case
     */
    @GetMapping("/user/login")
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse login(@RequestParam String dni, @RequestParam String password) {
        UserResponse userResponse = userService.login(dni, password);
        if (Objects.isNull(userResponse)) throw new UserNotFoundException();
        return userResponse;
    }

    /**
     * By default /user return all users
     *
     * @return List of all users
     */
    @GetMapping("/users")
    // Hay que especificar /users, sino da error -> "User not found!".
    List<UserResponse> all() {
        return userService.allUsers();
    }

    /**
     * Agrega un medico
     *
     * @param request el cuerpo json del medico
     */
    @PostMapping("/medic")
    public void addMedic(@RequestBody @NotNull @Valid MedicRequest request) {
        userService.save(request.toNewEntity());
    }

    /**
     * Agrega un paciente
     *
     * @param request el cuerpo json del paciente
     */
    @PostMapping("/patient")
    public void addPatient(@RequestBody @NotNull @Valid PatientRequest request) {
        userService.save(request.toNewEntity());
    }

    /**
     * Obtiene un paciente a partir de su id
     *
     * @param id id del paciente a filtrar
     * @return paciente
     */
    @GetMapping("/patient/{id}")
    public PatientResponse getPatient(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return new PatientResponse((Patient) user);
    }

    /**
     * Obtiene el medico a partir del id
     *
     * @param id id del medico a filtrar
     * @return medico
     */
    @GetMapping("/medic/{id}")
    public MedicResponse getMedic(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return new MedicResponse((Medic) user);
    }

    /**
     * Obtiene todos los pacientes
     *
     * @return Lista de pacientes
     */
    @GetMapping("/patients")
    public List<PatientResponse> getPatients() {
        return userService.getPatients();
    }

    /**
     * Obtiene todos los medicos
     *
     * @return Lista de medicos
     */
    @GetMapping("/medics")
    public List<MedicResponse> getMedics() {
        return userService.getMedics();
    }

    /**
     * Obtener los pacientes asociados al medico correspondiente
     *
     * @param id id del medico a filtrar
     * @return Lista de pacientes
     */
    @GetMapping("/medic/patients/{id}")
    public List<PatientResponse> getPatientsFromMedic(@PathVariable Integer id) {
        return userService.getPatientsFromMedic(id);
    }

    /**
     * Setear el medico al paciente a partir de un id
     *
     * @param idPatient id del paciente a filtrar
     * @param idMedic   id del medico a setear
     */
    @PostMapping("/patient/{idPatient}")
    @ResponseStatus(code = HttpStatus.OK)
    public void setMedicToPatient(@PathVariable Integer idPatient, @RequestParam Integer idMedic) {
        userService.setMedicToPatient(idPatient, idMedic);
    }

    /**
     * Borrar un usuario a partir de su id
     *
     * @param id id del usuario a filtrar
     */
    @DeleteMapping("/user/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User userToDelete = Optional.of(id)
                .map(userService::getUserById)
                .orElseThrow(UserNotFoundException::new);
        userService.delete(userToDelete);
    }

    /**
     * Obtengo el usuario dado un dni
     *
     * @param dni dni del usuario a filtrar
     * @return UserResponse
     */
    @GetMapping("/user")
    public UserResponse getUserByDni(@RequestParam String dni) {
        User user = userService.getUserByDni(dni);
        return user.getUserType() == PATIENT ? new PatientResponse(user) : new MedicResponse(user);
    }

    @GetMapping("/sim")
    public double getHeartRate(@RequestParam Integer id, @RequestParam float time){
        User user = Optional.of(id)
                .map(userService::getUserById)
                .orElseThrow(UserNotFoundException::new);

        if (user.getUserType() == PATIENT){
            int year = Calendar.getInstance().get(Calendar.YEAR);
            int age = year - new PatientResponse(user).getBirthdate().getYear();

            if (time < 600)
                return Math.random() * (0.6 * (211 - 0.64 * age))+ 60;
            else
                return Math.random() * (0.9 * (211 - 0.64 * age)) + 90;
        }
        else
            throw new UserNotFoundException();
    }
}
