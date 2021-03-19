package dev.group1.services;

import dev.group1.entities.Comment;

import java.util.Set;

public interface CommentService {
    Comment registerComment(Comment comment);

    Comment getCommentByCommentId(int commentId);

    Set<Comment> getAllCommentsByPostId(int postId);

    Comment updateComment(Comment comment);

    boolean deleteComment(int commentId);
}
