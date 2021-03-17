package dev.group1.services;

import dev.group1.entities.User;
import dev.group1.repos.UserRepo;
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
        this.userRepo.save(newUser);
        return newUser;
    }

    @Override
    public User getUserByUserId(int userId){
        return this.userRepo.findById(userId).get();
    }

    @Override
    public Set<User> getAllUsers() {
        Set<User> users = new HashSet<User>((Collection<? extends User>) this.userRepo.findAll());
        return users;
    }

    @Override
    public User updateUser(User updatedUser){
        this.userRepo.save(updatedUser);
        return updatedUser;
    }

    @Override
    public boolean deleteUserByUserId(int userId){
        this.userRepo.deleteById(userId);
        return !this.userRepo.existsById(userId);
    }

    @Override
    public User login(User loginAttempt) {
        //this.userRepo.findByUsernameAndPassword(loginAttempt.getUsername(), loginAttempt.getPassword()).get();
        return null;
    }

}
