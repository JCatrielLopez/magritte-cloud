package org.magritte.rayman.rest.controller;


import org.magritte.rayman.data.entity.User;
import org.magritte.rayman.rest.response.UserResponse;
import org.magritte.rayman.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(name = "/user")
public class UserController {

    @Autowired
    private UserService userService;

    //ENDPOINT
    @GetMapping
    @ResponseBody
    @ResponseStatus(code = HttpStatus.OK)
    public UserResponse getUser(@RequestParam Integer id){
        User user = userService.getUserById(id);
        return new UserResponse(user);
    }

    /*@PostMapping
    public void createUser(Integer id, @RequestBody UserRequest request){
        //TODO hacer
    }*/
}
