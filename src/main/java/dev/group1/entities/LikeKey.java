package dev.group1.entities;

import java.io.Serializable;
import java.util.Objects;

public class LikeKey implements Serializable {
    private int userId;
    private int postId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof LikeKey)) return false;
        LikeKey likeKey = (LikeKey) o;
        return userId == likeKey.userId && postId == likeKey.postId;
    }

    @Override
    public int hashCode() {
        return Objects.hash(userId, postId);
    }
}
