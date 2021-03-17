package dev.group1.repos;


import dev.group1.entities.Post;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

@Component
@Repository
public interface PostRepo extends CrudRepository<Post, Integer> {
}
