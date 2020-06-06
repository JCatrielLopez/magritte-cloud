package org.magritte.rayman.rest.controller;

import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.request.MedicRequest;
import org.magritte.rayman.rest.request.PatientRequest;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.service.UserNotFoundException;
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
    public UserResponse getUser(@PathVariable Integer id) {
        User user = userService.getUserById(id);
        return new UserResponse(user);
    }

    /**
     * Sign in user
     *
     * @param dni      user identifier
     * @param password password corresponding to the user
     * @return User: medic or patient depending on the case
     */
    @GetMapping("/user/login")
    public User login(@RequestParam String dni, @RequestParam String password) {
        return userService.login(dni, password);
    }

    /**
     * By default /user return all users
     *
     * @return List of all users
     */
    @GetMapping
    List<User> all() {
        return userService.findAll();
    }

    @PostMapping("/medic")
    public void createMedic(@RequestBody MedicRequest request) {
        userService.save(request.toNewEntity());
    }

    @PostMapping("/patient")
    public void createPatient(@RequestBody PatientRequest request) {
        userService.save(request.toNewEntity());
    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User userToDelete = Optional.of(id)
                .map(userService::getUserById)
                .orElseThrow(() -> new UserNotFoundException(id));
        userService.delete(userToDelete);
    }
}
