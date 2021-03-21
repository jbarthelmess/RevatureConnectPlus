package dev.group1.dtos;

import javax.persistence.Column;
import javax.persistence.JoinColumn;

public class CommentDTO {

    private int commentId;
    private int userId;
    private int postId;
    private String contentString;

    private long timestamp;

    public CommentDTO() {
    }

    public CommentDTO(int commentId, int userId, int postId, String contentString, long timestamp) {
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

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", userId=" + userId +
                ", postId=" + postId +
                ", contentString='" + contentString + '\'' +
                ", timestamp=" + timestamp +
                '}';
    }
}
