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

@Component
@RestController
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/user/registration")
    public String registerUser(@RequestBody UserDTO newUser){
        // Using DTO object to guarantee that existing users won't be accidentally overwritten
        if(newUser == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body given for request");
        }
        if(newUser.getUsername() == null || newUser.getUsername().equals("") || newUser.getPassword() == null || newUser.getPassword().equals("")) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Username and Passowrd must be valid strings");
        }
        User userCheck = new User();
        userCheck.setUserId(0);
        userCheck.setUsername(newUser.getUsername());
        userCheck.setPassword(newUser.getPassword());
        userCheck.setDisplayName(newUser.getDisplayName());
        if(newUser.getDisplayName() == null || newUser.getDisplayName().equals("")) {
            userCheck.setDisplayName(newUser.getUsername());
        }

        User user = this.userService.registerUser(userCheck);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Registration attempt failed, please choose a different username");
        }
        return "{\"jwt\":\""+JwtUtil.generate(user)+"\"}";
    }

    @PatchMapping("/user")
    @Authorize
    public String updateUser(UserDTO user, @RequestBody UserDTO updatedUser){
        // using DTO object to guarantee that bad data won't get written to the database
        User update = new User();
        update.setUserId(user.getUserId());
        update.setUsername(user.getUsername());

        // can only update display name, password will get set in service
        update.setPassword(updatedUser.getPassword());
        update.setDisplayName(updatedUser.getDisplayName());
        if(updatedUser.getDisplayName() == null || updatedUser.getDisplayName().equals("")) {
            update.setDisplayName(user.getDisplayName());
        }

        return "{\"jwt\":\""+ JwtUtil.generate(this.userService.updateUser(update))+"\"}";
    }

    @DeleteMapping("/user")
    @Authorize
    public String deleteUser(UserDTO user){
        return "{\"jwt\":"+this.userService.deleteUserByUserId(user.getUserId())+"}";
    }

    @PostMapping("/user/login")
    public String login(@RequestBody UserDTO loginAttempt) {
        // using DTO object to guarantee bad data won't get written to the database
        User attempt = new User();
        attempt.setUserId(0);
        attempt.setDisplayName(null);

        if(loginAttempt.getUsername() == null || loginAttempt.getUsername().equals("") || loginAttempt.getPassword() == null || loginAttempt.getPassword().equals("")){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Must provide a valid username and password to log in");
        }
        // must search by username and password only
        attempt.setUsername(loginAttempt.getUsername());
        attempt.setPassword(loginAttempt.getPassword());

        String check = this.userService.login(attempt);
        if(check == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Login Attempt failed, please try again");
        }
        return "{\"jwt\":\""+check+"\"}";
    }
}
