package dev.group1.repos;


import dev.group1.entities.User;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface UserRepo extends CrudRepository<User,Integer> {
    User findByUsernameAndPassword(String username, String password);
    User findByUsername(String username);
}
