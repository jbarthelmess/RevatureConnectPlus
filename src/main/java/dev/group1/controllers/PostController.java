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
import dev.group1.services.LikeService;
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
    @Autowired
    LikeService likeService;

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
        if(postDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body given in request");
        }
        if(postDTO.getContent() == null || postDTO.getContent().equals("")) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No content given for Post");
        }
        Post post = new Post();
        post.setPostId(0);
        post.setContent(postDTO.getContent());
        post.setUserId(user.getUserId());
        return postService.registerPost(post);
    }

//////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/post/{id}/like")
    @Authorize
    public String likePost(UserDTO user, @PathVariable int id) {
        return "{\"liked\":\""+this.likeService.likePost(user.getUserId(), id)+"\"}";
    }

////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    @PostMapping("/post/{id}/comment")
    @Authorize
    public Comment commentPost(UserDTO user, @PathVariable int id, @RequestBody CommentDTO commentDTO) {
        if(commentDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body given in request");
        }
        if(commentDTO.getContentString() == null || commentDTO.getContentString().equals("")) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No content given for comment");
        }
        Post post = postService.getPostByPostId(id);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This post does not exist");
        }
        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setCommentId(0);
        comment.setPostId(id);
        comment.setContentString(commentDTO.getContentString());
        return commentService.registerComment(comment);
    }

    @GetMapping("/post/{id}/comment")
    @Authorize
    public Set<Comment> postComments(UserDTO user, @PathVariable int id) {
        User userExist = userService.getUserByUserId(user.getUserId());
        if(userExist == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This user is not valid");
        }

        Post post = postService.getPostByPostId(id);
        if(post == null){
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "This post doesn't exist");
        }
        return commentService.getAllCommentsByPostId(id);
    }

    @PutMapping("/post/{id}/comment/{commentId}")
    @Authorize
    public Comment updateComment(UserDTO user, @PathVariable int id, @PathVariable int commentId, @RequestBody CommentDTO update) {
        if(update == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body given in request");
        }
        if(update.getContentString() == null || update.getContentString().equals("")) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No new content given for comment");
        }
        Comment comment = new Comment();
        comment.setUserId(user.getUserId());
        comment.setPostId(id);
        comment.setCommentId(commentId);
        comment.setContentString(update.getContentString());
        return this.commentService.updateComment(comment);
    }

    @DeleteMapping("/post/{id}/comment/{commentId}")
    @Authorize
    public String deleteComment(UserDTO user, @PathVariable int id, @PathVariable int commentId) {
        return "{\"deleted\":"+this.commentService.deleteComment(commentId, user.getUserId())+"}";
    }

    @PutMapping("/post/{id}")
    @Authorize
    public Post updatePost(UserDTO user, @PathVariable int id, @RequestBody PostDTO postDTO) {
        if(postDTO == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "No body given in request");
        }
        if(postDTO.getContent() == null || postDTO.getContent().equals("")) {
            throw new ResponseStatusException(HttpStatus.UNPROCESSABLE_ENTITY, "No new content given for Post");
        }
        Post post = new Post();
        post.setPostId(id);
        post.setContent(postDTO.getContent());
        post.setUserId(user.getUserId());
        return postService.updatePost(post);
    }

    @DeleteMapping("/post/{id}")
    @Authorize
    public String deletePost(UserDTO user, @PathVariable int id) {
        return "{\"deleted\":"+postService.deletePost(id, user)+"}";
    }
}
