package pet.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.sarafan.domain.Comment;
import pet.sarafan.domain.User;
import pet.sarafan.repository.CommentRepo;

@Service
public class CommentService {
    private final CommentRepo commentRepo;

    @Autowired
    public CommentService(CommentRepo commentRepo) {
        this.commentRepo = commentRepo;
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        commentRepo.save(comment);

        return comment;
    }
}
