package dev.group1.services;

import dev.group1.entities.Comment;
import dev.group1.repos.CommentRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

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
        Comment oldComment = this.commentRepo.findById(comment.getCommentId()).orElse(null);
        if(oldComment == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Cannot update non-existent comment");
        }
        if(oldComment.getUserId() != comment.getUserId()) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Cannot update comment that is not yours");
        }
        comment.setTimestamp(oldComment.getTimestamp());
        this.commentRepo.save(comment);
        return comment;
    }

    @Override
    public boolean deleteComment(int commentId, int userId) {
        Comment comment = this.commentRepo.findById(commentId).orElse(null);
        if(comment == null) {
            return true;
        }
        if(comment.getUserId() != userId) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "You cannot delete a comment that is not yours");
        }
        this.commentRepo.deleteById(commentId);
        return !this.commentRepo.existsById(commentId);
    }
}
