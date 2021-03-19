package dev.group1.services;

import dev.group1.entities.Comment;
import dev.group1.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Set;

@Component
@Service
public class CommentServiceImpl implements CommentService{

    @Autowired
    CommentRepo commentRepo;

    @Override
    public Comment registerComment(Comment comment) {
        comment.setTimestamp(System.currentTimeMillis()/1000L);
        this.commentRepo.save(comment);
        return comment;
    }

    @Override
    public Comment getCommentByCommentId(int commentId) {
        return this.commentRepo.findById(commentId).orElse(null);
    }

    @Override
    public Set<Comment> getAllCommentsByPostId(int postId) {
        return this.commentRepo.findAllByPostId(postId);
    }

    @Override
    public Comment updateComment(Comment comment) {
        this.commentRepo.save(comment);
        return comment;
    }

    @Override
    public boolean deleteComment(int commentId) {
        this.commentRepo.findById(commentId);
        return !this.commentRepo.existsById(commentId);
    }
}
