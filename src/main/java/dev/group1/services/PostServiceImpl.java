package dev.group1.services;

import dev.group1.dtos.UserDTO;
import dev.group1.entities.Post;
//import dev.group1.entities.User;
import dev.group1.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

@Component
@Service
public class PostServiceImpl implements PostService {

    @Autowired
    PostRepo postRepo;

    @Override
    public Post registerPost(Post post) {
        post.setTimestamp(System.currentTimeMillis()/1000L);
        this.postRepo.save(post);
        return post;
    }

    @Override
    public Post getPostByPostId(int postId) {
        return this.postRepo.findById(postId).orElse(null);
    }

    @Override
    public Set<Post> getAllPosts() {
        return new HashSet<>((Collection<? extends Post>) this.postRepo.findAll());
    }

    @Override
    public Post updatePost(Post updatedPost) {
        Post oldPost = this.postRepo.findById(updatedPost.getPostId()).orElse(null);
        if(oldPost == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempting to update non-existent post");
        }
        if(oldPost.getUserId() != updatedPost.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Attempting to update a post that is not yours");
        }
        this.postRepo.save(updatedPost);
        return updatedPost;
    }

    @Override
    public boolean deletePost(int postId, UserDTO user) {
        Post toBeDeleted = this.postRepo.findById(postId).orElse(null);
        if(toBeDeleted == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Attempting to delete non-existent post");
        }
        if(toBeDeleted.getUserId() != user.getUserId()) {
            throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Attempting to delete post that is not yours");
        }
        this.postRepo.deleteById(postId);
        return !this.postRepo.existsById(postId);
    }

    @Override
    public Set<Post> getFirst50Posts() {
        return this.postRepo.findFirst50ByOrderByTimestamp();
    }

    @Override
    /* for now use timestamp 1616075773L  for proof on concept*/
    public Set<Post> getNext50Posts(long timestamp) {
        return this.postRepo.findTop50ByTimestampAfter(timestamp);
    }
}
