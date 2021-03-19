package dev.group1.controllers;

import dev.group1.aspects.Authorize;
import dev.group1.entities.User;
import dev.group1.services.UserService;
import dev.group1.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Component
@RestController
public class UserController {

    @Autowired
    UserService userService;

    @GetMapping("/user/users")
    @Authorize
    // likely not necessary but following along with tutorial
    public Set<User> retrieveAllUsers(User user){
        return this.userService.getAllUsers();
    }


    @PostMapping("/user/registration")
    public String registerUser(@RequestBody User newUser){
        User user = this.userService.registerUser(newUser);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration attempt failed, please choose a different username");
        }
        return JwtUtil.generate(user);
    }

    @GetMapping("/user")
    @Authorize
    public User getUser(User user){
        return this.userService.getUserByUserId(user.getUserId());
    }

    @PatchMapping("/user")
    @Authorize
    public User getUser(User user, @RequestBody User updatedUser){
        updatedUser.setUserId(user.getUserId());
        return this.userService.updateUser(updatedUser);
    }

    @DeleteMapping("/user")
    @Authorize
    public boolean deleteUser(User user){
        return this.userService.deleteUserByUserId(user.getUserId());
    }

    @PostMapping("/user/login")
    public String login(@RequestBody User loginAttempt) {
        String check = this.userService.login(loginAttempt);
        if(check == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login Attempt failed, please try again");
        }
        return check;
    }
}
