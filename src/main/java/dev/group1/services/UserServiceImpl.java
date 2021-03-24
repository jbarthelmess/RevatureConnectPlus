package dev.group1.services;

import dev.group1.entities.User;
import dev.group1.repos.UserRepo;
import dev.group1.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    UserRepo userRepo;

    @Override
    public User registerUser(User newUser) {
        User check = this.userRepo.findByUsername(newUser.getUsername());
        if(check == null) {
            this.userRepo.save(newUser);
            return newUser;
        }
        return null;
    }

    @Override
    public User getUserByUserId(int userId) {
        return this.userRepo.findById(userId).orElse(null);
    }

    @Override
    public Set<User> getAllUsers() {
        return new HashSet<>((Collection<? extends User>) this.userRepo.findAll());
    }

    @Override
    public User updateUser(User updatedUser) {
        User user = this.userRepo.findById(updatedUser.getUserId()).orElse(null);
        if(user == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempting to update a user that doesn't exist");
        }
        updatedUser.setPassword(user.getPassword()); // cannot change password for now
        this.userRepo.save(updatedUser);
        return updatedUser;
    }

    @Override
    public boolean deleteUserByUserId(int userId) {
        this.userRepo.deleteById(userId);
        return !this.userRepo.existsById(userId);
    }

    @Override
    public String login(User loginAttempt) {
        User user = this.userRepo.findByUsernameAndPassword(loginAttempt.getUsername(), loginAttempt.getPassword());
        if(user == null) {
            return null;
        }
        return JwtUtil.generate(user);
    }

}
