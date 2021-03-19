package dev.group1.controllers;

import dev.group1.aspects.Authorize;
import dev.group1.dtos.CommentDTO;
import dev.group1.dtos.PostDTO;
import dev.group1.dtos.UserDTO;
import dev.group1.entities.Comment;
import dev.group1.entities.Post;
import dev.group1.entities.User;
import dev.group1.services.CommentService;
import dev.group1.services.PostService;
import dev.group1.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.Set;

@Component
@RestController
public class PostController {

    @Autowired
    PostService postService;
    @Autowired
    CommentService commentService;
    @Autowired
    UserService userService;

    @GetMapping("/post")
    @Authorize
    public Set<Post> retrieveVisiblePosts(UserDTO user) {
        return postService.getFirst50Posts();
    }

    @GetMapping("/post/next/{timestamp}")
    @Authorize
    public Set<Post> retrieveNextPosts(UserDTO user, @PathVariable long timestamp){ return postService.getNext50Posts(timestamp);}

    @PostMapping("/post")
    @Authorize
    public Post createPost(UserDTO user, @RequestBody PostDTO postDTO) {
        // Using DTO object to prevent bad data from being written to database
        Post post = new Post();
        post.setPostId(0);
        post.setContent(postDTO.getContent());
        post.setUserId(user.getUserId());

        return postService.registerPost(post);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/post/{id}/like")
    @Authorize
    public boolean likePost(UserDTO user, @PathVariable int id) {
        return false;
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/post/{id}/comment")
    @Authorize
    public Comment commentPost(UserDTO user, @PathVariable int id, @RequestBody CommentDTO commentDTO) {
        Post post = postService.getPostByPostId(id);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This post does not exist");
        }

        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setCommentId(0);
        comment.setPostId(id);
        comment.setContentString(commentDTO.getContentString());
        commentService.registerComment(comment);
        return comment;
    }

    @GetMapping("/post/{id}/comment")
    @Authorize
    public Set<Comment> postComments(UserDTO user, @PathVariable int id) {
        User userExist = userService.getUserByUserId(user.getUserId());
        if(userExist == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This user is not valid");
        }

        Post post = postService.getPostByPostId(id);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.CONFLICT, "This post doesn't exist");
        }
        return commentService.getAllCommentsByPostId(id);
    }

    @PutMapping("/post/{id}")
    @Authorize
    public Post updatePost(UserDTO user, @PathVariable int id, @RequestBody PostDTO postDTO) {
        Post post = new Post();
        post.setPostId(postDTO.getPostId());
        post.setContent(postDTO.getContent());
        post.setUserId(user.getUserId());
        return postService.updatePost(post);
    }

    @DeleteMapping("/post/{id}")
    @Authorize
    public boolean deletePost(UserDTO user, @PathVariable int id) {
        return postService.deletePost(id, user);
    }
}
