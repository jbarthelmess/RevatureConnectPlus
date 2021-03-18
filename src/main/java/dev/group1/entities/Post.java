package dev.group1.entities;

import javax.persistence.*;


@Entity
@Table(name = "post")
public class Post {

    // like counter

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "post_id")
    private int postId;

    @Column(name = "user_id")
    @JoinColumn(name = "user_id")
    private int userId;

    @Column(name = "post_timestamp")
    private long timestamp;

    @Column(name = "content_string")
    private String content;

    public Post() {
    }

    public Post(int postId, int userId, long timestamp, String content) {
        this.postId = postId;
        this.userId = userId;
        this.timestamp = timestamp;
        this.content = content;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", timestamp=" + timestamp +
                ", content='" + content + '\'' +
                '}';
    }
}
