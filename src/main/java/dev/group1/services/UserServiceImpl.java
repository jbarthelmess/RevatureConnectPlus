package dev.group1.services;

import dev.group1.entities.User;
import dev.group1.repos.UserRepo;
import dev.group1.utils.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        Set<User> users = new HashSet<User>((Collection<? extends User>) this.userRepo.findAll());
        return users;
    }

    @Override
    public User updateUser(User updatedUser) {
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
