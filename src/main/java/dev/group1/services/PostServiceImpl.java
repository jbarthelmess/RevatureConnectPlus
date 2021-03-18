package dev.group1.services;

import dev.group1.entities.Post;
import dev.group1.repos.PostRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

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
        post.setTimestamp(System.currentTimeMillis());
        this.postRepo.save(post);
        return post;
    }

    @Override
    public Post getPostByPostId(int postId) {
        return this.postRepo.findById(postId).get();

    }

    @Override
    public Set<Post> getAllPosts() {
        return new HashSet<>((Collection<? extends Post>) this.postRepo.findAll());
    }

    @Override
    public Post updatePost(Post updatedPost) {
        this.postRepo.save(updatedPost);
        return updatedPost;
    }

    @Override
    public boolean deletePost(int postId) {
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
