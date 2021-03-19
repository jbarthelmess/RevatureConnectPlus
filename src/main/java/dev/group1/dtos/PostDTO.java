package dev.group1.dtos;

public class PostDTO {
    private int postId;
    private int userId;
    private long timestamp;
    private String content;

    public PostDTO() {}

    public PostDTO(int postId, int userId, long timestamp, String content) {
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
}
