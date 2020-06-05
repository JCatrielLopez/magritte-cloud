package org.magritte.rayman.rest.controller;


import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.request.MedicRequest;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.service.UserNotFoundException;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
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
    public char login(@RequestParam String dni, @RequestParam String password){
        //Va a devolver la clase la cual pertenece este usuario si es que coincide la password con el parametro
        //Esto en la aplicacion desprende a una u otra actividad segun la clase.
        return userService.login(dni, password);
    }

    @GetMapping("/users")
    List<User> all() {
        return userService.findAll();
    }

    @PostMapping("/user")
    public void createUser(@Valid @RequestBody User new_user){
        userService.save(new_user);
    }

//TODO
//    @PostMapping("/medic")
//    public void createMedic(@RequestBody MedicRequest request){
//        userService.save(request.toNewEntity());
//    }

    @DeleteMapping("/{id}")
    public void deleteUser(@PathVariable Integer id) {
        User userToDelete = Optional.of(id)
                .map(userService::getUserById)
                .orElseThrow(() -> new UserNotFoundException(id));
        userService.delete(userToDelete);
    }
}
