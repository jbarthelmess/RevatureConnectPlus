package dev.group1.revatureconnectplus;

import dev.group1.entities.User;
import dev.group1.repos.UserRepo;
//import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;

@Transactional
@SpringBootTest
class UserTest {
    @Autowired
    UserRepo repo;

    User user;

    @Test
    @Rollback
    void create_user() {
        user = new User(0, "jbart", "password", "Josh Barthelmess");
        repo.save(user);
        Assertions.assertNotEquals(0, user.getUserId());
    }

    @Test
    @Rollback
    void create_user_same_username() { // throws an exception when two with the same username try to get saved
        user = new User(0, "jbart", "password", "Josh Barthelmess");
        repo.save(user);
        user = new User(0, "jbart", "password", "Josh Barthelmess2");
        DataIntegrityViolationException e = Assertions.assertThrows(DataIntegrityViolationException.class, () -> repo.save(user));
    }

    @Test
    @Rollback
    void get_user_by_id() {
        user = new User(0, "jbart", "password", "Josh Barthelmess");
        repo.save(user);
        User newUser = repo.findById(user.getUserId()).get();
        Assertions.assertEquals(user.getDisplayName(), newUser.getDisplayName());
        Assertions.assertEquals(user.getUserId(), newUser.getUserId());
        Assertions.assertEquals(user.getPassword(), newUser.getPassword());
        Assertions.assertEquals(user.getUsername(), newUser.getUsername());
    }

    @Test
    @Rollback
    void get_user_by_username_and_password() {
        user = new User(0, "jbart", "password", "Josh Barthelmess");
        repo.save(user);
        User newUser = repo.findByUsernameAndPassword("jbart", "password");
        Assertions.assertEquals(user.getDisplayName(), newUser.getDisplayName());
        Assertions.assertEquals(user.getUserId(), newUser.getUserId());
        Assertions.assertEquals(user.getPassword(), newUser.getPassword());
        Assertions.assertEquals(user.getUsername(), newUser.getUsername());
        Assertions.assertNull(repo.findByUsernameAndPassword("jbart", "badpassword"));
        Assertions.assertNull(repo.findByUsernameAndPassword("badUsername", "badpassword"));
    }

    @Test
    @Rollback
    void get_user_by_username() {
        user = new User(0, "jbart", "password", "Josh Barthelmess");
        repo.save(user);
        User newUser = repo.findByUsername("jbart");
        Assertions.assertEquals(user.getDisplayName(), newUser.getDisplayName());
        Assertions.assertEquals(user.getUserId(), newUser.getUserId());
        Assertions.assertEquals(user.getPassword(), newUser.getPassword());
        Assertions.assertEquals(user.getUsername(), newUser.getUsername());
        Assertions.assertNull(repo.findByUsername("badUsername"));
    }
}
