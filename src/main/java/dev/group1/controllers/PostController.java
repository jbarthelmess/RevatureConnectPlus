package dev.group1.controllers;

import dev.group1.entities.Post;
import dev.group1.entities.User;
import dev.group1.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;

import java.util.Set;

@Component
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/post")
    public Set<Post> retrieveVisiblePosts() {
        return postService.getFirst50Posts();
    }

    @GetMapping("/post/next/{timestamp}")
    public Set<Post> retrieveNextPosts(@PathVariable long timestamp){ return postService.getNext50Posts(timestamp);}

    @PostMapping("/post")
    public Post createPost(@RequestBody Post post) {
        return postService.registerPost(post);
    }

    @PostMapping("/post/{id}/like")
    // user info sent in  jwt
    // @Authorize // on top of everything
    public boolean likePost(User user, @PathVariable int id) {
        return false;
    }

    @PostMapping("/post/{id}/comment")
    public Object /*Comment*/ commentPost(@PathVariable int id, @RequestBody Object /*Comment*/ comment) {
        return null;
    }

    @GetMapping("/post/{id}/comment")
    public Object /*Set<Comment>*/ postComments(@PathVariable int id) {
        return null;
    }

    @PutMapping("/post/{id}")
    public Post updatePost(@PathVariable int id, @RequestBody Post post) {
        return postService.updatePost(post);
    }

    @DeleteMapping("/post/{id}")
    public boolean deletePost(@PathVariable int id) {
        return postService.deletePost(id);
    }
}
