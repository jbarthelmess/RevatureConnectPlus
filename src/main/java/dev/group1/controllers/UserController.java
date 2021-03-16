package dev.group1.controllers;

import dev.group1.entities.User;
import dev.group1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Component
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/users")
    // likely not necessary but following along with tutorial
    public Set<User> retrieveAllUsers(){
        return this.userService.getAllUsers();
    }


    @PostMapping("/user/registration")
    public User registerUser(@RequestBody User newUser){
        return this.userService.registerUser(newUser);
    }

    @GetMapping("/user/{userId}")
    public User getUser(@PathVariable int userId){
        return this.userService.getUserByUserId(userId);
    }

    @PatchMapping("/user/{userId}/update")
    public User getUser(@PathVariable int userId, @RequestBody User updatedUser){
        updatedUser.setUserId(userId);
        return this.userService.updateUser(updatedUser);
    }

    @DeleteMapping("/user/{userId}")
    public boolean deleteUser(@PathVariable int userId){
        return this.userService.deleteUserByUserId(userId);
    }

}
