package dev.group1.revatureconnectplus;

import dev.group1.entities.Post;
import dev.group1.entities.User;
import dev.group1.repos.PostRepo;
import dev.group1.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
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
class PostTest {

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
        postRepo.save(post);
        Assertions.assertNotEquals(0, post.getPostId());
    }

    @Test
    @Rollback
    void get_post_by_post_id() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post);
        Post newPost = postRepo.findById(post.getPostId()).orElse(null);
        Assertions.assertNotNull(newPost);
        Assertions.assertEquals(post.getPostId(), newPost.getPostId());
        Assertions.assertEquals(post.getUserId(), newPost.getUserId());
        Assertions.assertEquals(post.getContent(), newPost.getContent());
        Assertions.assertEquals(post.getTimestamp(), newPost.getTimestamp());
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
        Assertions.assertNotEquals(0, allPosts.size());
    }

    @Test
    @Rollback
    void update_post() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post);
        Post newPost = new Post(post.getPostId(), post.getUserId(), post.getTimestamp(), "This content has been updated");
        postRepo.save(newPost);
        Post updatedPost = postRepo.findById(post.getPostId()).orElse(null);
        Assertions.assertNotNull(updatedPost);
        Assertions.assertEquals(updatedPost.getPostId(), post.getPostId());
        Assertions.assertEquals(updatedPost.getUserId(), post.getUserId());
        Assertions.assertEquals(updatedPost.getContent(), post.getContent());
        Assertions.assertEquals(updatedPost.getTimestamp(), post.getTimestamp());
    }

    @Test
    @Rollback
    void delete_post_by_post_id() {
        Post post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L, "The is a test content and should be rolledbacked!");
        postRepo.save(post);
        int postId = post.getPostId();
        postRepo.deleteById(post.getPostId());
        Assertions.assertFalse(postRepo.existsById(postId));
    }

    @Test
    void get_first_50_posts(){
        Set<Post> topPosts = postRepo.findFirst50ByOrderByTimestamp();
        Assertions.assertTrue(topPosts.size() <= 50);
    }

    @Test
    void find_next_50(){
        Set<Post> topPosts = postRepo.findBottom50ByTimestampBefore(1616529027L);
        System.out.println(topPosts);
        Assertions.assertTrue(topPosts.size() <= 50);
    }

    @Test
    void like_count() {
        Set<Post> posts = new HashSet<>((Collection<? extends Post>) postRepo.findAll());
        System.out.println(posts);
    }
}
