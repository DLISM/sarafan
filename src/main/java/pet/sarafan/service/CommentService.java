package pet.sarafan.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pet.sarafan.domain.Comment;
import pet.sarafan.domain.User;
import pet.sarafan.domain.Views;
import pet.sarafan.dto.EventType;
import pet.sarafan.dto.ObjectType;
import pet.sarafan.repository.CommentRepo;
import pet.sarafan.util.WsSender;

import java.util.function.BiConsumer;

@Service
public class CommentService {
    private final CommentRepo commentRepo;
    private final BiConsumer<EventType, Comment> wsSender;

    @Autowired
    public CommentService(CommentRepo commentRepo, WsSender wsSender) {
        this.commentRepo = commentRepo;
        this.wsSender=wsSender.getSender(ObjectType.COMMENT, Views.FullComment.class);
    }

    public Comment create(Comment comment, User user){
        comment.setAuthor(user);
        Comment commentFromDB=commentRepo.save(comment);
        wsSender.accept(EventType.CREATE, commentFromDB);

        return commentFromDB;
    }
}
