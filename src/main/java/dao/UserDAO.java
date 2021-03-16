package dao;

import entities.User;

public interface UserDAO {
    User createUser(User user);
    User login(User user);
    User deleteAccount(User user);
}
