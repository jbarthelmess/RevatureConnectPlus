package dev.group1.controllers;

import dev.group1.dtos.PostDTO;
import dev.group1.entities.Post;
import dev.group1.entities.User;
import dev.group1.services.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import dev.group1.aspects.Authorize;

import java.util.Set;

@Component
@RestController
public class PostController {

    @Autowired
    PostService postService;

    @GetMapping("/post")
    @Authorize
    public Set<Post> retrieveVisiblePosts(User user) {
        return postService.getFirst50Posts();
    }

    @GetMapping("/post/next/{timestamp}")
    @Authorize
    public Set<Post> retrieveNextPosts(User user, @PathVariable long timestamp){ return postService.getNext50Posts(timestamp);}

    @PostMapping("/post")
    @Authorize
    public Post createPost(User user, @RequestBody PostDTO postDTO) {
        // Using DTO object to prevent bad data from being written to database
        Post post = new Post();
        post.setPostId(0);
        post.setContent(postDTO.getContent());
        post.setUserId(user.getUserId());

        return postService.registerPost(post);
    }

    @PostMapping("/post/{id}/like")
    @Authorize
    public boolean likePost(User user, @PathVariable int id) {
        return false;
    }

    @PostMapping("/post/{id}/comment")
    @Authorize
    public Object /*Comment*/ commentPost(User user, @PathVariable int id, @RequestBody Object /*Comment*/ comment) {
        return null;
    }

    @GetMapping("/post/{id}/comment")
    @Authorize
    public Object /*Set<Comment>*/ postComments(User user, @PathVariable int id) {
        return null;
    }

    @PutMapping("/post/{id}")
    @Authorize
    public Post updatePost(User user, @PathVariable int id, @RequestBody PostDTO postDTO) {
        Post post = new Post();
        post.setPostId(postDTO.getPostId());
        post.setContent(postDTO.getContent());
        post.setUserId(user.getUserId());
        return postService.updatePost(post);
    }

    @DeleteMapping("/post/{id}")
    @Authorize
    public boolean deletePost(User user, @PathVariable int id) {
        return postService.deletePost(id, user);
    }
}
