package org.magritte.rayman.rest.controller;


import org.magritte.rayman.data.entity.Medic;
import org.magritte.rayman.data.entity.Patient;
import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.request.MedicRequest;
import org.magritte.rayman.rest.request.PatientRequest;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.exceptions.UserNotFoundException;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(name = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    //ENDPOINT
    @GetMapping("/{id}")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUser(@PathVariable Integer id){
        User user = userService.getUserById(id);
        return new UserResponse(user);
    }

    @GetMapping("/user/login")
    public ResponseEntity<Character> login(@RequestParam String dni, @RequestParam String password){
        //Va a devolver la clase la cual pertenece este usuario si es que coincide la password con el parametro
        //Esto en la aplicacion desprende a una u otra actividad segun la clase.
        Character c = userService.login(dni, password);
        if (c == null){
            throw new UserNotFoundException("Wrong password for user " + dni);
        }
        else
            return new ResponseEntity<>(c, HttpStatus.ACCEPTED);

    }

    @GetMapping("/users")
    List<User> all() {
        return userService.findAll();
    }

//    @PostMapping("/user")
//    public void createUser(@Valid @RequestBody User new_user){
//        userService.save(new_user);
//    }

    @PostMapping("/medic")
    public void createMedic(@RequestBody MedicRequest medic){
        Medic m = new Medic(medic.getDni(), medic.getName(), medic.getLastname(), medic.getPassword(), medic.getEmail(),medic.getSpecialization(),medic.getLicense());
        userService.save(m);
    }

    @PostMapping("/patient")
    public void createPatient(@RequestBody PatientRequest p){
        DateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        try{
            Date date = df.parse(p.getBirthdate());
            Patient patient = new Patient(p.getDni(), p.getName(), p.getLastname(), p.getPassword(), p.getEmail(), date, p.getGender(), p.getHeight(), p.getWeight());
            userService.save(patient);
        }
        catch(java.text.ParseException e){System.out.println("La concha de tu madre all boys! " + e);}

    }

    @PostMapping("/user/medic/{id}")
    public ResponseEntity<String> registerMedic(@PathVariable Integer id, @RequestParam String dni){
//        if (userService.registerMedic(id, dni))
//            return new ResponseEntity<>("Medic was successfully registered!", HttpStatus.ACCEPTED);
//        else
//            return new ResponseEntity<>("Not a valid user", HttpStatus.BAD_REQUEST);
        return null;
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User userToDelete = Optional.of(id)
                .map(userService::getUserById)
                .orElseThrow(() -> new UserNotFoundException("User not found!"));
        userService.delete(userToDelete);
    }
}
