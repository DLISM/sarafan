package pet.sarafan.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import pet.sarafan.domain.Comment;

public interface CommentRepo extends JpaRepository<Comment, Long> {
}
