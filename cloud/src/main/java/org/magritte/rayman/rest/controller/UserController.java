package org.magritte.rayman.rest.controller;

import org.jetbrains.annotations.NotNull;
import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.mail.SenderMail;
import org.magritte.rayman.rest.request.AlertRequest;
import org.magritte.rayman.rest.request.MedicRequest;
import org.magritte.rayman.rest.request.PatientRequest;
import org.magritte.rayman.rest.response.MedicResponse;
import org.magritte.rayman.rest.response.PatientResponse;
import org.magritte.rayman.rest.response.SimulationResponse;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.transaction.Transactional;
import javax.validation.Valid;
import java.util.*;

import static org.magritte.rayman.data.entity.Patient.PATIENT;

/**
 * EndPoints para atender peticiones asociadas a usuarios (Pacientes y Medicos)
 */
@RestController
@RequestMapping(name = "/user")
@Transactional(rollbackOn = Exception.class)
public class UserController {

    @Autowired
    private UserService userService;

    /**
     * Obtiene un listado de todos los pacientes y medicos registrados.
     *
     * @return Una lista con todos los usuarios, o una lista vacia en caso de no existir ninguno.
     */
    @GetMapping("/users")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    // Hay que especificar /users, sino da error -> "User not found!".
    List<UserResponse> all() {
        return userService.allUsers();
    }

    /**
     * Obtiene el usuario correspondiente al ID dado.
     *
     * @param id id del usuario a obtener
     * @return Si existe retorna el usuario (medico o paciente), sino NOT FOUND.
     */
    @GetMapping("/user/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return user.getUserType() == PATIENT ? new PatientResponse(user) : new MedicResponse(user);
    }

    /**
     * Obtiene el usuario correspondiente al DNI dado.
     *
     * @param dni dni del usuario a obtener
     * @return Si existe retorna el usuario (medico o paciente), sino NOT FOUND.
     */
    @GetMapping("/user")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUserByDni(@RequestParam String dni) {
        User user = userService.getUserByDni(dni);
        return user.getUserType() == PATIENT ? new PatientResponse(user) : new MedicResponse(user);
    }

    /**
     * Obtiene el usuario correspondiente al nickname dado.
     *
     * @param nickname Nickname del usuario a filtrar
     * @return Si existe retorna el usuario (medico o paciente), sino NOT FOUND.
     */
    @GetMapping("/user/nickname")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUserByNickname(@RequestParam String nickname) {
        User user = userService.getUserByNickname(nickname);
        return user.getUserType() == PATIENT ? new PatientResponse(user) : new MedicResponse(user);
    }

    /**
     * Inicia sesion de un usuario en el sistema.
     *
     * @param nickname Nickname del usuario
     * @param password Contraseña del usuario
     * @return Si existe retorna el usuario logueado (medico o paciente), sino NOT FOUND.
     */
    @GetMapping("/user/login")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse login(@RequestParam String nickname, @RequestParam String password) {
        UserResponse userResponse = userService.login(nickname, password);
        if (Objects.isNull(userResponse))
            throw new NoSuchElementException();
        return userResponse;
    }

    /**
     * Obtiene todos los pacientes
     *
     * @return Lista de pacientes, o una lista vacia si no existe ninguno.
     */
    @GetMapping("/patients")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientResponse> getPatients() {
        return userService.getPatients();
    }

    /**
     * Obtiene un paciente a partir de su id
     *
     * @param id id del paciente
     * @return Si existe retorna el paciente, sino NOT FOUND.
     */
    @GetMapping("/patient/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public PatientResponse getPatient(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return new PatientResponse(user);
    }

    /**
     * Obtiene todos los medicos
     *
     * @return Lista de medicos, una lista vacia si no existe ninguno.
     */
    @GetMapping("/medics")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<MedicResponse> getMedics() {
        return userService.getMedics();
    }

    /**
     * Obtiene los medicos con una cierta especialidad
     *
     * @return Lista de medicos, o una lista vacia si no existe ninguno.
     */
    @GetMapping("/medics/specialization")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<MedicResponse> getMedicsBySpecialization(@RequestParam String specialization){
        return userService.getMedicsBySpecialization(specialization);
    }

    /**
     * Obtiene el medico a partir del id
     *
     * @param id id del medico
     * @return Si existe retorna el paciente, sino NOT FOUND.
     */
    @GetMapping("/medic/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public MedicResponse getMedic(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return new MedicResponse(user);
    }

    /**
     * Obtiene los pacientes asociados al medico dado
     *
     * @param id id del medico
     * @return Lista de pacientes, o una lista vacia si no existe ninguno.
     */
    @GetMapping("/medic/patients/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<PatientResponse> getPatientsFromMedic(@PathVariable Integer id) {
        return userService.getPatientsFromMedic(id);
    }

    /**
     * Obtiene los medicos asociados al paciente dado
     *
     * @param id id del paciente
     * @return Lista de medicos, o una lista vacia si no existe ninguno.
     */
    @GetMapping("/patient/medics/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public List<MedicResponse> getMedicsFromPatient(@PathVariable Integer id) {
        return userService.getMedicsFromPatient(id);
    }

    /**
     * Verifica el estado del dispositivo
     *
     * @return SimulationResponse
     */
    @GetMapping("/checkStatus")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public SimulationResponse getHeartRate() {
        return new SimulationResponse();

    }

    /**
     * Agrega un medico
     *
     * @param request Informacion del medico a agregar
     * @return El medico agregado
     */
    @PostMapping("/medic")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public MedicResponse addMedic(@RequestBody @NotNull @Valid MedicRequest request) {
        Medic user = request.toNewEntity();
        userService.save(user);
        return new MedicResponse(user);
    }

    /**
     * Agrega un paciente
     *
     * @param request Informacion del paciente a agregar
     * @return El paciente agregado
     */
    @PostMapping("/patient")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public PatientResponse addPatient(@RequestBody @NotNull @Valid PatientRequest request) {
        Integer medic_id = request.getMedic_id();
        Medic medic = null;
        if (Objects.nonNull(medic_id)) {
            medic = (Medic) userService.getUserById(medic_id);
        }
        Patient patient = request.toNewEntity(medic);
        userService.save(patient);
        return new PatientResponse(patient);
    }

    /**
     * Asigna un medico a un paciente
     *
     * @param idPatient id del paciente
     * @param idMedic   id del medico
     * @return El paciente con el id dado, sino NOT FOUND.
     */
    @PostMapping("/patient/{idPatient}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public PatientResponse setMedicToPatient(@PathVariable Integer idPatient, @RequestParam Integer idMedic) {
        return userService.setMedicToPatient(idPatient, idMedic);
    }

    /**
     * Elimina un usuario a partir de su id
     *
     * @param id id del usuario
     */
    @DeleteMapping("/user/{id}")
    @ResponseStatus(code = HttpStatus.OK)
    public void deleteUser(@PathVariable Integer id) {
        User userToDelete = Optional.of(id)
                .map(userService::getUserById)
                .orElseThrow(NoSuchElementException::new);
        userService.delete(userToDelete);
    }

    /**
     * Cambia la disponibilidad de un medico
     * @param id Id del medico
     * @return El medico con el id dado, sino NOT FOUND.
     */
    @PostMapping("/medic/available/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public MedicResponse changeAvailability(@PathVariable Integer id){
        return new MedicResponse(userService.changeAvailability(id));
    }


    /**
     * Se envia una alerta a los medicos para un paciente en particular.
     * El orden de prioridad de los medicos a contactar es el siguiente:
     * 1) Notifica a los medicos asociados al paciente que se encuentren disponibles, en la misma ciudad y con la especialidad solicitada.
     * 2) Si ningun medico asociado al paciente puediera ser contactado, se buscan medicos en la ciudad y especialidad solicitadas.
     * 3) Si ningun medico de la especialidad solicitada pudiera contactarse, se buscan medicos disponibles de cualquier especialidad en la ciudad.
     * 4) Como ultima alternativa, se intenta notificar a los medicos de la ciudad
     *
     * @param alertRequest Informacion necesaria para realizar la notificacion de alerta para un paciente.
     * @return El medico que fue alertado.
     */
    @PostMapping("/emergencyAlert")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public MedicResponse searchAvailableMedic(@RequestBody AlertRequest alertRequest){
        Patient patient = (Patient) userService.getUserById(alertRequest.getIdUser());
        Set<Medic> medicosAsociados = patient.getMedic();
        String firstname = patient.getFirstname();
        String lastname = patient.getLastname();
        String causa = alertRequest.getRazon();
        String fecha = alertRequest.getFecha();
        String direccion = alertRequest.getDireccion();

        //Notifica a los medicos asociados al paciente que se encuentren disponibles, en la misma ciudad y con la especialidad solicitada
        for (Medic medic : medicosAsociados){
            if (medic.isAvailability() &&
                    medic.getCity().equals(patient.getCity()) &&
                    medic.getSpecialization().equals(alertRequest.getEspecialista())){
                SenderMail senderMail = new SenderMail();
                senderMail.addRecepient(medic.getEmail());
                String message = "Su paciente " + firstname + " " + lastname + " solicita de su asistencia medica debido a "
                        + causa + ". Se requiere de su presencia en la direccion: " + direccion + ".\nHora de alerta: " + fecha;
                senderMail.sendMail(message);
                return new MedicResponse(medic);
            }
        }

        //Si ningun medico asociado al paciente puediera ser contactado, se buscan medicos en la ciudad y especialidad solicitadas
        List<MedicResponse> medicosEspecializados = userService.getMedicsBySpecialization(alertRequest.getEspecialista());
        for (MedicResponse medic : medicosEspecializados){
            if (medic.isAvailability() &&
                    medic.getCity().equals(patient.getCity())){
                SenderMail senderMail = new SenderMail();
                senderMail.addRecepient(medic.getEmail());
                String message = "Un paciente llamado " + firstname + " " + lastname + " solicita asistencia medica debido a "
                        + causa + ". Como su medico de cabecera no se encuentra disponible en este momento, solicitamos su ayuda " +
                        "ya que figura como disponible y se trata de una emergencia de su rubro. Creemos que su servicio " +
                        "es de vital importancia. Se requiere de su presencia en la direccion: " + direccion + ".\nHora de alerta: " + fecha;
                senderMail.sendMail(message);
                return medic;
            }
        }

        //Si ningun medico de la especialidad solicitada pudiera contactarse, se buscan medicos disponibles de cualquier especialidad en la ciudad
        List<MedicResponse> medicosEnCiudad = userService.getMedicsByCity(patient.getCity());
        for (MedicResponse medic : medicosEnCiudad){
            if (medic.isAvailability()){
                SenderMail senderMail = new SenderMail();
                senderMail.addRecepient(medic.getEmail());
                String message = "Un paciente llamado " + firstname + " " + lastname + " solicita asistencia medica debido a "
                        + causa + ". Como no se encuentran medicos disponibles que cubran esta causa, se solicita su presencia " +
                        "en la direccion: " + direccion + ".\nHora de alerta: " + fecha;
                senderMail.sendMail(message);
                return medic;
            }
        }

        //Como ultima alternativa, se intenta notificar a los medicos de la ciudad
        for (MedicResponse medic : medicosEnCiudad){
                SenderMail senderMail = new SenderMail();
                senderMail.addRecepient(medic.getEmail());
                String message = "Un paciente llamado" + firstname + " " + lastname + " solicita asistencia medica debido a "
                        + causa + ". Se requiere la presencia  de algun medico en la direccion: " + direccion + ". " +
                        "Por favor comunicarse con la central para coordinar y confirmar su asistencia.\nHora de alerta: " + fecha;
                senderMail.sendMail(message);
        }
        MedicResponse salida = new MedicResponse();
        salida.setFirstname("Todos");
        salida.setLastname("Todos");
        return salida;
    }
}
