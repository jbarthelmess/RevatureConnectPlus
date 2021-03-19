package dev.group1.controllers;

import dev.group1.aspects.Authorize;
import dev.group1.dtos.UserDTO;
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
    public String registerUser(@RequestBody UserDTO newUser){
        // Using DTO object to guarantee that existing users won't be accidentally overwritten
        User userCheck = new User();
        userCheck.setUserId(0);
        userCheck.setUsername(newUser.getUsername());
        userCheck.setPassword(newUser.getPassword());
        userCheck.setDisplayName(newUser.getDisplayName());

        User user = this.userService.registerUser(userCheck);
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
    public User updateUser(User user, @RequestBody UserDTO updatedUser){
        // using DTO object to guarantee that bad data won't get written to the database
        User update = new User();
        update.setUserId(user.getUserId());
        update.setUsername(user.getUsername());

        // can only update password, or display name
        update.setPassword(updatedUser.getPassword());
        update.setDisplayName(updatedUser.getDisplayName());

        return this.userService.updateUser(update);
    }

    @DeleteMapping("/user")
    @Authorize
    public boolean deleteUser(User user){
        return this.userService.deleteUserByUserId(user.getUserId());
    }

    @PostMapping("/user/login")
    public String login(@RequestBody UserDTO loginAttempt) {
        // using DTO object to guarantee bad data won't get written to the database
        User attempt = new User();
        attempt.setUserId(0);
        attempt.setDisplayName(null);

        // must search by username and password only
        attempt.setUsername(loginAttempt.getUsername());
        attempt.setPassword(loginAttempt.getPassword());

        String check = this.userService.login(attempt);
        if(check == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login Attempt failed, please try again");
        }
        return check;
    }
}
