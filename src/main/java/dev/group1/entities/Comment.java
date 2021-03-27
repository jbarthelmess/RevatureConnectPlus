package dev.group1.entities;


import org.hibernate.annotations.Formula;

import javax.persistence.*;

@Entity
@Table(name = "comment_table")
public class Comment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int commentId;

    @Column(name = "user_id")
    @JoinColumn(name = "user_id")
    private int userId;

    @Column(name = "post_id")
    @JoinColumn(name = "post_id")
    private int postId;

    @Column(name = "comment_string")
    private String contentString;

    @Column(name = "comment_timestamp")
    private long timestamp;

    @Formula("(select plus_user.display_name from plus_user where plus_user.user_id = comment0_.user_id)")
    private String displayName;

    public Comment() {
    }

    public Comment(int commentId, int userId, int postId, String contentString, long timestamp) {
        this.commentId = commentId;
        this.userId = userId;
        this.postId = postId;
        this.contentString = contentString;
        this.timestamp = timestamp;
    }

    public int getCommentId() {
        return commentId;
    }

    public void setCommentId(int commentId) {
        this.commentId = commentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getPostId() {
        return postId;
    }

    public void setPostId(int postId) {
        this.postId = postId;
    }

    public String getContentString() {
        return contentString;
    }

    public void setContentString(String contentString) {
        this.contentString = contentString;
    }

    public long getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(long timestamp) {
        this.timestamp = timestamp;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", contentString='" + contentString + '\'' +
                ", timestamp=" + timestamp +
                ", displayName=" + displayName +
                '}';
    }
}
