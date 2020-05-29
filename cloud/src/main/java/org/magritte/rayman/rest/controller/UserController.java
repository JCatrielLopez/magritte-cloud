package org.magritte.rayman.rest.controller;


import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping(name = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    //ENDPOINT
    @GetMapping("/user")
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUser(@RequestParam Integer id){
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
    public void createUser(Integer id, @RequestBody User new_user){
        userService.save(new_user);
    }

    @DeleteMapping("/user")
    public void deleteEmployee(@RequestParam Integer id) {
        userService.deleteById(id);
    }
}
