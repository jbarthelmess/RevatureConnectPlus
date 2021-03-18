package dev.group1.RevatureConnectPlus;

import dev.group1.entities.Post;
import dev.group1.entities.User;
import dev.group1.repos.PostRepo;
import dev.group1.repos.UserRepo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@SpringBootTest
@Transactional
public class PostTest {

    @Autowired
    PostRepo postRepo;
    @Autowired
    UserRepo userRepo;

    User user;

    @BeforeEach
    void setup() {
        user = new User(0, "brooke", "Kiser", "Brooskiey");
        userRepo.save(user);
    }


    @Test
    @Rollback
    void create_post() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        System.out.println(post);
    }

    @Test
    @Rollback
    void get_post_by_post_id() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post);
        Post newPost = postRepo.findById(post.getPostId()).get();
        System.out.println(newPost);
    }

    @Test
    @Rollback
    void get_all_posts() {
        Post post0 = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post0);
        Post post1 = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post1);
        Post post2 = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post2);
        Post post3 = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post3);

        Set<Post> allPosts = new HashSet<>((Collection<? extends Post>) postRepo.findAll());
        System.out.println(allPosts);
    }

    @Test
    @Rollback
    void update_post() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post);
        Post newPost = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "This content has been updated");
        postRepo.save(newPost);
        System.out.println(newPost);
    }

    @Test
    @Rollback
    void delete_post_by_post_id() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post);
        postRepo.deleteById(post.getPostId());
    }

    @Test
    void get_first_50_posts(){
        Set<Post> top_posts = postRepo.findFirst50ByOrderByTimestamp();
        System.out.println(top_posts);
    }

    @Test
    void find_next_50(){
        Set<Post> top_posts = postRepo.findTop50ByTimestampAfter(1616075773L);
        System.out.println(top_posts);
    }
}
