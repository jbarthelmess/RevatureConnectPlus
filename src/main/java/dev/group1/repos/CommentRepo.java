package dev.group1.repos;

import dev.group1.entities.Comment;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface CommentRepo extends CrudRepository<Comment, Integer> {
    Set<Comment> findAllByPostId(int postId);
}
