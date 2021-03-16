package dev.group1.services;

import dev.group1.entities.User;

import java.util.Set;

public interface UserService {
    User registerUser(User newUser);
    User getUserByUserId(int userId);
    Set<User> getAllUsers();
    User updateUser(User updatedUser);
    boolean deleteUserByUserId(int userId);
}
