package dev.group1.services;

import dev.group1.dtos.UserDTO;
import dev.group1.entities.Post;

import java.util.Set;

public interface PostService {
    Post registerPost(Post post);

    Post getPostByPostId(int postId);

    Set<Post> getAllPosts();

    Post updatePost(Post updatedPost);

    boolean deletePost(int postId, UserDTO user);

    Set<Post> getFirst50Posts();

    Set<Post> getNext50Posts(long timestamp);
}
