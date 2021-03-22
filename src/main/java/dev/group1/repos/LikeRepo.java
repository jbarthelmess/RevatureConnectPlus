package dev.group1.repos;

import dev.group1.entities.Like;
import dev.group1.entities.LikeKey;
import org.springframework.data.repository.CrudRepository;

import java.util.Set;

public interface LikeRepo extends CrudRepository<Like, LikeKey> {
    Set<Like> findAllByPostId(int postId);
}