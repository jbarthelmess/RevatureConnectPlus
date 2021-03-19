package dev.group1.revatureconnectplus;

import dev.group1.entities.Comment;
import dev.group1.entities.Post;
import dev.group1.entities.User;
import dev.group1.repos.CommentRepo;
import dev.group1.repos.PostRepo;
import dev.group1.repos.UserRepo;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;

import javax.transaction.Transactional;
import java.util.Set;

@SpringBootTest
@Transactional
public class CommentTest {

    @Autowired
    UserRepo userRepo;
    @Autowired
    PostRepo postRepo;
    @Autowired
    CommentRepo commentRepo;

    User user;
    Post post;

    @BeforeEach
    void setUp(){
        user = new User(0, "brooke", "Kiser", "Brooskiey");
        userRepo.save(user);
        post = new Post(0, user.getUserId(), System.currentTimeMillis() / 1000L,
                "The is a test content and should be rolledbacked!");
        postRepo.save(post);

    }

    @Test
    @Rollback
    void register_comment(){
        Comment comment = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment);
        System.out.println("\nregister_comment: assertNotEquals" +
                "\n\tUnexpected: 0" +
                "\n\tActual: " + comment.getCommentId());
        Assertions.assertNotEquals(0, comment.getCommentId());
    }

    @Test
    @Rollback
    void get_comment_by_comment_id(){
        Comment comment = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment);
        Comment newComment = commentRepo.findById(comment.getCommentId()).orElse(null);
        System.out.println(newComment);
        try {
            System.out.println("\nget_comment_by_comment_id: assertEquals" +
                    "\n\tExpected: " + comment +
                    "\n\tActual: " + newComment);
            Assertions.assertEquals(comment.getCommentId(), newComment.getCommentId());
        } catch (NullPointerException e){
            Assertions.fail();
        }
    }

    @Test
    @Rollback
    void get_all_comments_by_post_id(){
        Comment comment1 = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment1);
        Comment comment2 = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment2);
        Comment comment3 = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment3);

        Set<Comment> comments = this.commentRepo.findAllByPostId(post.getPostId());

        System.out.println("\nget_all_comments_by_post_id: assertEquals" +
                "\n\tExpected: " + 3 +
                "\n\tActual: " + comments.size());
        Assertions.assertEquals(3, comments.size());
    }

    @Test
    @Rollback
    void update_comment(){
        Comment comment = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment);
        Comment commentUpdate = new Comment(comment.getCommentId(), user.getUserId(), post.getPostId(),
                "This is an updated comment", System.currentTimeMillis()/1000L);
        commentRepo.save(commentUpdate);

        Comment databaseComment = commentRepo.findById(comment.getCommentId()).orElse(null);
        try {
            System.out.println("\nupdate_comment: assertEquals" +
                    "\n\tExpected: " + commentUpdate +
                    "\n\tActual: " + databaseComment);
            Assertions.assertEquals("This is an updated comment", databaseComment.getContentString());
        } catch (NullPointerException e){
            Assertions.fail();
        }
    }

    @Test
    @Rollback
    void delete_comment(){
        Comment comment = new Comment(0, user.getUserId(), post.getPostId(), "This is a test comment",
                System.currentTimeMillis()/1000L);
        commentRepo.save(comment);
        commentRepo.deleteById(comment.getCommentId());

        Comment deletedComment = commentRepo.findById(comment.getCommentId()).orElse(null);

        System.out.println("\ndelete_comment: assertNull" +
                "\n\tExpected: null" +
                "\n\tActual: " + deletedComment);
        Assertions.assertNull(deletedComment);
    }

}
