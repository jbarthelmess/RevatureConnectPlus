package dev.group1.services;

import dev.group1.entities.Like;
import dev.group1.entities.LikeKey;
import dev.group1.repos.LikeRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

@Component
@Service
public class LikeServiceImpl implements LikeService{
    @Autowired
    LikeRepo likeRepo;

    @Override
    public boolean likePost(int userId, int postId) {
        LikeKey likeKey = new LikeKey();
        likeKey.setUserId(userId);
        likeKey.setPostId(postId);
        Like like = this.likeRepo.findById(likeKey).orElse(null);
        if(like == null) {
            like = new Like();
            like.setPostId(likeKey.getPostId());
            like.setUserId(likeKey.getUserId());
            this.likeRepo.save(like);
            return true;
        }
        else {
            this.likeRepo.delete(like);
            return false;
        }
    }
}
